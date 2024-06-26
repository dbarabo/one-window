import org.example.ru.barabo.afina.AfinaConnect
import org.example.ru.barabo.cryptopro.ssl.CryptoTls
import org.example.ru.barabo.onewin.service.ClientRequestService
import org.example.ru.barabo.onewin.xml.request.RequestXml
import org.example.ru.barabo.onewin.xml.request.XmlBuilder
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class CryptoTest {

    private val logger = LoggerFactory.getLogger(CryptoTest::class.java)

    //@Test
    fun simpleTestXml() {
        val request = RequestXml()

        logger.error(XmlBuilder.xmlToString(request) )
    }

    @Test
    fun clientTestXml() {

        AfinaConnect.init(isTest = false)

        val clientRequestService = ClientRequestService()

        logger.error(clientRequestService.requestByClientId(87574360L/*486569L*/) )
    }

    //@Test
    fun testTlsConnect() {
        val connection = CryptoTls.tlsConnection()

        try {
            printStream(connection.inputStream)
        } finally {
            connection.disconnect()
        }
    }

    private fun printStream(inputStream: InputStream?) {
        val br = BufferedReader(
            InputStreamReader(
                inputStream, "windows-1251"
            )
        )

        var input: String?
        while ((br.readLine().also { input = it }) != null) {
            println(input)
        }
        br.close()
    }
}