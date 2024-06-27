package ru.barabo.onewin.http

import ru.barabo.crypto.XSD_SCHEMA_REQUEST
import ru.barabo.crypto.signXml
import ru.barabo.crypto.unsignXml
import org.slf4j.LoggerFactory
import ru.barabo.afina.ifTest
import ru.barabo.cryptopro.ssl.CryptoTls
import ru.barabo.onewin.service.ClientRequestService
import java.io.DataOutputStream
import java.io.File
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object HttpClient {

    private val logger = LoggerFactory.getLogger(HttpClient::class.java)

    fun sendRequest(idClient: Long): File {

        val signFile = prepareRequest(idClient)

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

    private fun prepareRequest(idClient: Long): File {
        val clientRequestService = ClientRequestService()

        val requestXml = clientRequestService.requestByClientId(idClient)

        return signXml(requestXml, XSD_SCHEMA_REQUEST)
    }
}

const val RESPONSE_FILE_NAME = "response"

fun xNbki() = "X:/НБКИ/".ifTest("C:/НБКИ/")

fun todayFolder(): String = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDate.now())

fun xNbkiTodayUncrypto() = "${xNbki()}${todayFolder()}/UNCRYPTO"