package org.example.ru.barabo.cryptopro.ssl

import org.slf4j.LoggerFactory
import ru.CryptoPro.Crypto.CryptoProvider
import ru.CryptoPro.JCSP.JCSP
import ru.CryptoPro.reprov.RevCheck
import ru.CryptoPro.ssl.Provider
import java.io.FileInputStream
import java.net.URL
import java.security.KeyStore
import java.security.PrivateKey
import java.security.Security
import javax.net.ssl.*

object CryptoTls {

    private val logger = LoggerFactory.getLogger(CryptoTls::class.java)

    fun tlsConnection(): HttpsURLConnection {
        val context = sslContext()

        val factory: SSLSocketFactory = context!!.socketFactory

        val url = URL(URL_PATH)
        val connection: HttpsURLConnection = url.openConnection() as HttpsURLConnection

        // Задание для него требуемого SSLSocketFactory.
        connection.setSSLSocketFactory(factory)


        //connection.disconnect()

        return connection
    }

    private fun sslContext(): SSLContext? {
        var context: SSLContext? = null
        try {
            setPropertiesAndProviders()

            val keyStore = KeyStore.getInstance("HDIMAGE", "JCSP")
            keyStore.load(null, null)

            val trustStore = KeyStore.getInstance("JKS")
            val myKeys = FileInputStream("E:/kotlin/sert/truststore.jks")
            trustStore.load(myKeys, PASSWORD)

            var privateKey: PrivateKey? = keyStore.getKey(SIGN_KEY_ALIAS, null) as PrivateKey

            val kmf: KeyManagerFactory = KeyManagerFactory.getInstance("GostX509", "JTLS")
            kmf.init(keyStore, null)

            val tmf: TrustManagerFactory = TrustManagerFactory.getInstance("GostX509", "JTLS")
            tmf.init(trustStore)

            context = SSLContext.getInstance("GostTLSv1.2", "JTLS")
            context.init(kmf.keyManagers, tmf.trustManagers, null)

        } catch (e: Exception) {
            logger.error("Err", e)
        }
        return context
    }

    private fun setPropertiesAndProviders() {
        System.setProperty("ru.CryptoPro.reprov.enableCRLDP", "true")
        System.setProperty("com.sun.security.enableCRLDP", "true")
        System.setProperty("com.ibm.security.enableCRLDP", "true")

        System.setProperty("ssl.KeyManagerFactory.algorithm", "GostX509")
        System.setProperty("javax.net.ssl.keyStoreType", "HDIMAGE")
        System.setProperty("javax.net.ssl.keyStorePassword", "123456")

        System.setProperty("file.encoding", "UTF-8")

        //Security.addProvider(JCP())
        Security.addProvider(JCSP()) // провайдер JCSP
        Security.addProvider(RevCheck()) // провайдер проверки сертификатов JCPRevCheck //(revocation-провайдер)
        Security.addProvider(CryptoProvider()) // провайдер шифрования JCryptoP
        Security.addProvider(Provider()) //JTLS
    }
}
private const val URL_PATH = "https://reports.nbki.ru/qbch/"

private const val SIGN_KEY_ALIAS = "pfx-4b3c7fc4-1c87-2baa-5f38-60d054953ad1 - Copy - Copy"

private val PASSWORD: CharArray = "123456".toCharArray()