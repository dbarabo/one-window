package ru.barabo.onewin.xml.request

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.DomDriver
import org.slf4j.LoggerFactory
import java.io.File
import java.math.BigDecimal
import java.math.BigInteger
import java.nio.charset.Charset
import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory

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

    fun xmlToFile(xmlObject: Any, file: File): String {

        val xml = xmlToString(xmlObject)

        file.writeText(xml, Charset.forName(CHARSET))

        return xml
    }
}

private const val CHARSET = "UTF-8"

object XmlValidator {

    fun validate(xmlFile: File, xsdInJar: String) {

        val xmlStream = StreamSource(xmlFile)

        val schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)

        val urlXsd = this.javaClass.getResource(xsdInJar)

        val schema = schemaFactory.newSchema(urlXsd)

        val validator = schema!!.newValidator()

        validator.validate(xmlStream)
    }
}

private val logger = LoggerFactory.getLogger(XmlValidator::class.java)