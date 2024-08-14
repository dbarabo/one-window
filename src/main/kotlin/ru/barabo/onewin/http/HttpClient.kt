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
import ru.barabo.onewin.xml.request.RequestXml
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

        val (fileResult, requestXml)  = sendRequest(idClient, isOneWinRequest)

        val resultTicket: ResultXml? = getResultTicket(fileResult)

        if(resultTicket == null) {

            parseFullResponse(fileResult)
            return
        }
        val uuiTicket = resultTicket.responseId!!.value!!

        tryGetAnswerAndSave(uuiTicket, idClient, requestXml)
   }

    private fun tryGetAnswerAndSave(uuiTicket: String, idClient: Long, requestXml: RequestXml) {
        var count = 0
        var ticketError: ResultXml?

        do {
            val answerFile = getAnswer(uuiTicket)

            val (pay, error) = saveAnswer(idClient, answerFile)
            if(pay != null) {
                return
            }

            ticketError = error

            count++
            Thread.sleep(11000L)
        } while(count < 10)

        ticketError?.let { AnswerService.saveError(idClient, uuiTicket, requestXml, it) }
    }

    private fun saveAnswer(idClient: Long, answerFile: File): Pair<PayInfoXml?, ResultXml?> {

        var pay: PayInfoXml? = null
        var ticketError: ResultXml? = null

        try {
            pay = XmlBuilder.loadFromFile(answerFile)

        } catch (e: Exception) {
            logger.error("saveAnswer", e)

            try {
                ticketError = XmlBuilder.loadFromFile(answerFile)
            }catch (e: Exception) {
                logger.error("saveAnswer ResultXml FAIL", e)
                throw Exception(e)
            }
        }

        pay?.let { AnswerService.saveAnswer(idClient, it) }

        return Pair(pay, ticketError)
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

    private fun parseFullResponse(fileResult: File) {
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

    fun sendRequest(idClient: Long, isOneWinRequest: Boolean): Pair<File, RequestXml> {

        val (signFile, requestXml) = prepareRequest(idClient, isOneWinRequest)

        val signResponseFile = sendRequest(signFile)

        logger.error("signResponseFile=$signResponseFile")

        val responseFile =  File("${xNbkiTodayUncrypto()}/$RESPONSE_FILE_NAME-${Date().time}.xml")
        unsignXml(signResponseFile, responseFile)

        return Pair(responseFile, requestXml)
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

    private fun prepareRequest(idClient: Long, isOneWinRequest: Boolean): Pair<File, RequestXml> {

        val requestXml = clientRequest().requestByClientId(idClient, isOneWinRequest)

        return Pair(signXml(XmlBuilder.xmlToString(requestXml), XSD_SCHEMA_REQUEST), requestXml)
    }
}

const val RESPONSE_FILE_NAME = "response"

fun xNbki() = "X:/НБКИ/".ifTest("C:/НБКИ/")

fun todayFolder(): String = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDate.now())

fun xNbkiTodayUncrypto() = "${xNbki()}${todayFolder()}/UNCRYPTO".existsFolder()

fun String.byFolderExists(): File {
    val folder = File(this)

    if(!folder.exists()) {
        folder.mkdirs()
    }

    return folder
}

fun String.existsFolder(): String {
    val folder = File(this)

    if(!folder.exists()) {
        folder.mkdirs()
    }

    return this
}