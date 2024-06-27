package ru.barabo.test

import ru.barabo.afina.AfinaConnect
import ru.barabo.onewin.http.HttpClient
import ru.barabo.onewin.service.ClientRequestService
import ru.barabo.onewin.xml.request.RequestXml
import ru.barabo.onewin.xml.request.XmlBuilder
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import ru.barabo.onewin.http.RESPONSE_FILE_NAME
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader

class CryptoTest {

    private val logger = LoggerFactory.getLogger(CryptoTest::class.java)

    //@Test
    fun simpleTestXml() {
        val request = RequestXml()

        logger.error(XmlBuilder.xmlToString(request) )
    }

    //@Test
    fun anyTest() {

        val sendFileData = File("E:\\kotlin\\projects\\one-window\\build\\classes\\kotlin\\temp\\sign1719455545523\\1719455545527.xml.sig")

        val responseFile = File("${sendFileData.parent}/$RESPONSE_FILE_NAME.sig")

        logger.error("$responseFile")
    }



    @Test
    fun sendRequestTestXml() {

        AfinaConnect.init(isTest = false)

        HttpClient.sendRequest(486569L)
    }


    //@Test
    fun clientTestXml() {

        AfinaConnect.init(isTest = false)

        val clientRequestService = ClientRequestService()

        logger.error(clientRequestService.requestByClientId(87574360L/*486569L*/) )

        //file.writeText(dataFile, Charset.forName("CP1251"))
    }

    //@Test
    fun testTlsConnect() {
        /*val connection = CryptoTls.tlsConnection()

        try {
            printStream(connection.inputStream)
        } finally {
            connection.disconnect()
        }*/
    }

    private fun printStream(inputStream: InputStream?) {
        val br = BufferedReader(
            InputStreamReader(
                inputStream, "UTF-8"
            )
        )

        var input: String?
        while ((br.readLine().also { input = it }) != null) {
            println(input)
        }
        br.close()
    }
}