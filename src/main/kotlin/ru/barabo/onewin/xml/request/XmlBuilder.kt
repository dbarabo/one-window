package org.example.ru.barabo.onewin.xml.request

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.DomDriver
import java.math.BigDecimal
import java.math.BigInteger

object XmlBuilder {

    private val xstream: XStream =  XStream(DomDriver(CHARSET) ).apply {
        autodetectAnnotations(true)

        useAttributeFor(String::class.java)
        useAttributeFor(Int::class.java)
        useAttributeFor(Long::class.java)
        useAttributeFor(Double::class.java)
        useAttributeFor(BigDecimal::class.java)
        useAttributeFor(BigInteger::class.java)
        useAttributeFor(Boolean::class.java)
        useAttributeFor(Integer::class.java)
    }

    fun xmlToString(xmlObject: Any): String = xstream.toXML(xmlObject)

}

private const val CHARSET = "UTF-8"