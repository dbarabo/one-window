import org.example.ru.barabo.cyptopro.ssl.CryptoTls
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class CryptoTest {

    private val logger = LoggerFactory.getLogger(CryptoTest::class.java)

    @Test
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