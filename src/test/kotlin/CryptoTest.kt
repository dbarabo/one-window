import org.cryptacular.util.CertUtil
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import ru.CryptoPro.Crypto.CryptoProvider
import ru.CryptoPro.JCP.JCP
import ru.CryptoPro.JCSP.JCSP
import ru.CryptoPro.reprov.RevCheck
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import java.security.*
import java.security.cert.X509Certificate
import javax.net.ssl.*

import ru.CryptoPro.ssl.Provider

class CryptoTest {

    private val logger = LoggerFactory.getLogger(CryptoTest::class.java)

    //@Test
    fun firstTest() {

        System.setProperty("file.encoding", "UTF-8")
        Security.addProvider(JCSP()) // провайдер JCSP
        Security.addProvider(RevCheck()) // провайдер проверки сертификатов JCPRevCheck
//(revocation-провайдер)
        Security.addProvider(CryptoProvider()) // провайдер шифрования JCryptoP


        val ks = KeyStore.getInstance("HDIMAGE", "JCSP") //KeyStore.getInstance("REGISTRY", "JCSP")
        ks.load(null, null)

        for (alias in ks.aliases() ) {

            logger.error( "alias:$alias" )

            val cert = ks.getCertificate(alias) ?: continue
            if (cert !is X509Certificate) {
                continue
            }
            logger.error( CertUtil.subjectCN(cert) )
        }
    }

    var urlPath: String = "https://reports.nbki.ru/qbch/"

    @Test
    fun jtlsTest() {

        val context = getContext()
        logger.error("context=$context")

        val factory: SSLSocketFactory = context!!.socketFactory

        val url = URL(urlPath)
        val connection: HttpsURLConnection = url.openConnection() as HttpsURLConnection

        // Задание для него требуемого SSLSocketFactory.
        connection.setSSLSocketFactory(factory)

        // Вывод на экран содержимого запрошенной страницы.

        printStream(connection.inputStream);

        // Разрыв соединения.
        connection.disconnect()
    }


    fun printStream(inputStream: InputStream?) {
        val br = BufferedReader(
            InputStreamReader(
                inputStream, "windows-1251"
            )
        )

        var input: String?
        while ((br.readLine().also { input = it }) != null) {
            println(input)
        } // while
        br.close()
    }

    private val PRIVATE_KEY_NOT_FOUND: String = "privateKey not found! "

    private val PASSWORD: CharArray = "123456".toCharArray()
    private val SIGNER_KEY_ALIAS: String = "pfx-4b3c7fc4-1c87-2baa-5f38-60d054953ad1 - Copy - Copy"

    fun getContext(): SSLContext? {
        var context: SSLContext? = null
        try {
            System.setProperty("ru.CryptoPro.reprov.enableCRLDP", "true")
            System.setProperty("com.sun.security.enableCRLDP", "true")
            System.setProperty("com.ibm.security.enableCRLDP", "true")

            //System.setProperty("tls_prohibit_disabled_validation", "false")

            System.setProperty("ssl.KeyManagerFactory.algorithm", "GostX509")
            System.setProperty("javax.net.ssl.keyStoreType", "HDIMAGE")
            System.setProperty("javax.net.ssl.keyStorePassword", "123456")


            System.setProperty("file.encoding", "UTF-8")


            //Security.addProvider(JCP())
            Security.addProvider(JCSP()) // провайдер JCSP
            Security.addProvider(RevCheck()) // провайдер проверки сертификатов JCPRevCheck
//(revocation-провайдер)
            Security.addProvider(CryptoProvider()) // провайдер шифрования JCryptoP

            Security.addProvider(Provider())

            val keyStore = KeyStore.getInstance("HDIMAGE", "JCSP")
            keyStore.load(null, null)


            val trustStore = KeyStore.getInstance("JKS")
            val myKeys = FileInputStream("E:/kotlin/sert/truststore.jks")
            trustStore.load(myKeys, PASSWORD)


            val cert = keyStore.getCertificate(SIGNER_KEY_ALIAS) as X509Certificate
            logger.error("Signer found: " + cert.subjectX500Principal.name)

            var privateKey: PrivateKey? = null
            val key: Key? = keyStore.getKey(SIGNER_KEY_ALIAS, null)

            if (key != null) {
                privateKey = key as PrivateKey
            } else {
                logger.error(PRIVATE_KEY_NOT_FOUND + SIGNER_KEY_ALIAS)
            }

            val kmf: KeyManagerFactory = KeyManagerFactory.getInstance("GostX509", "JTLS")
            kmf.init(keyStore, null)

            val tmf: TrustManagerFactory = TrustManagerFactory.getInstance("GostX509", "JTLS")
            tmf.init(trustStore)

            context = SSLContext.getInstance("GostTLSv1.2", "JTLS")  //SSLContext.getInstance("GostTLS", "JTLS")
            context.init(kmf.keyManagers, tmf.trustManagers, null)

        } catch (e: Exception) {
            logger.error("Err", e)
        }
        return context
    }
}