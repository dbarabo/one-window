package ru.barabo.onewin.http

import ru.barabo.crypto.XSD_SCHEMA_REQUEST
import ru.barabo.crypto.signXml
import ru.barabo.crypto.unsignXml
import org.slf4j.LoggerFactory
import ru.barabo.afina.AfinaConnect
import ru.barabo.afina.ifTest
import ru.barabo.cmd.Cmd
import ru.barabo.cryptopro.ssl.CryptoTls
import ru.barabo.onewin.service.AnswerService
import ru.barabo.onewin.service.ClientRequestService
import ru.barabo.onewin.xml.answer.PayInfoXml
import ru.barabo.onewin.xml.request.XmlBuilder
import ru.barabo.onewin.xml.result.ResultXml
import java.io.DataOutputStream
import java.io.File
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object HttpClient {

    private val logger = LoggerFactory.getLogger(HttpClient::class.java)

    private lateinit var clientRequestService: ClientRequestService

    private fun clientRequest(): ClientRequestService {
        if(!(::clientRequestService.isInitialized)) {
            AfinaConnect.init(isTest = false)
            clientRequestService = ClientRequestService()
        }

        return clientRequestService
    }

    fun requestAndAnswer(idClient: Long, isOneWinRequest: Boolean) {

        clientRequest().checkClient(idClient)

        val fileResult = sendRequest(idClient, isOneWinRequest)

        val resultTicket: ResultXml? = getResultTicket(fileResult)

        if(resultTicket == null) {

            parseFullResponse(fileResult)
            return
        }

        val uuiTicket = resultTicket.responseId!!.value!!

        val answerFile = getAnswer(uuiTicket)

        saveAnswer(idClient, answerFile)
    }

    private fun saveAnswer(idClient: Long, answerFile: File) {

        val pay: PayInfoXml = XmlBuilder.loadFromFile(answerFile)

        AnswerService.saveAnswer(idClient, pay)
    }


    private fun checkClient(idClient: Long) {
        clientRequest().checkClient(idClient)
    }


    fun getAnswer(uuid: String): File {

        val signAnswerFile = getAnswerSig(uuid)

        logger.error("signAnswerFile=$signAnswerFile")

        val responseFile =  File("${xNbkiTodayUncrypto()}/answer-$uuid.xml")
        unsignXml(signAnswerFile, responseFile)

        return responseFile
    }

    private fun parseFullResponse(fileResponse: File) {
        // TODO
    }

    private fun getResultTicket(fileResult: File): ResultXml? {

        return try {
            XmlBuilder.loadFromFile<ResultXml>(fileResult)
        }catch (e: Exception) {
            logger.error("getResultTicket", e)

            null
        }
    }

    private fun getAnswerSig(uuid: String): File {

        val connection = CryptoTls.tlsConnection("dlanswer?id=$uuid")

        val responseFile: File

        try {
            connection.setDoOutput(true)
            connection.instanceFollowRedirects = false
            connection.setRequestMethod("GET")
            connection.setUseCaches(false)

            responseFile = File("${Cmd.tempFolder("sign").absolutePath}/$uuid.sig")

            responseFile.writeBytes( connection.inputStream.readBytes() )

            logger.error("responseCode=${connection.responseCode}")

            if(!responseFile.exists()) {
                throw IOException ("файл ответа не найден! ${responseFile.absoluteFile}")
            }

        } finally {
            connection.disconnect()
        }

        return responseFile
    }

    fun sendRequest(idClient: Long, isOneWinRequest: Boolean): File {

        val signFile = prepareRequest(idClient, isOneWinRequest)

        val signResponseFile = sendRequest(signFile)

        logger.error("signResponseFile=$signResponseFile")

        val responseFile =  File("${xNbkiTodayUncrypto()}/$RESPONSE_FILE_NAME-${Date().time}.xml")
        unsignXml(signResponseFile, responseFile)

        return responseFile
    }

    private fun sendRequest(sendFileData: File): File {

        val bytes = sendFileData.readBytes()

        val connection = CryptoTls.tlsConnection("dlrequest")

        val responseFile: File

        try {

            connection.setDoOutput(true)
            connection.instanceFollowRedirects = false
            connection.setRequestMethod("POST")
            connection.setRequestProperty("Content-Type", "binary/octet-stream")
            connection.setRequestProperty("Content-Length", bytes.size.toString())
            connection.setUseCaches(false)
            DataOutputStream(connection.outputStream).use { wr ->
                wr.write(bytes)
            }

            responseFile = File("${sendFileData.parent}/$RESPONSE_FILE_NAME.sig")
            responseFile.writeBytes( connection.inputStream.readBytes() )

            logger.error("responseCode=${connection.responseCode}")

            if(!responseFile.exists()) {
                throw IOException ("файл ответа не найден! ${responseFile.absoluteFile}")
            }

        } finally {
            connection.disconnect()
        }

        return responseFile
    }

    private fun prepareRequest(idClient: Long, isOneWinRequest: Boolean): File {

        val requestXml = clientRequest().requestByClientId(idClient, isOneWinRequest)

        return signXml(requestXml, XSD_SCHEMA_REQUEST)
    }
}

const val RESPONSE_FILE_NAME = "response"

fun xNbki() = "X:/НБКИ/".ifTest("C:/НБКИ/")

fun todayFolder(): String = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDate.now())

fun xNbkiTodayUncrypto() = "${xNbki()}${todayFolder()}/UNCRYPTO"