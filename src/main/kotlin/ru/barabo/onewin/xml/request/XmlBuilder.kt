package ru.barabo.onewin.xml.request

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.DomDriver
import com.thoughtworks.xstream.security.AnyTypePermission
import org.slf4j.LoggerFactory
import ru.barabo.onewin.xml.answer.*
import ru.barabo.onewin.xml.result.ResponseId
import ru.barabo.onewin.xml.result.ResultXml
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

        processAnnotations(ResultXml::class.java)
        processAnnotations(ResponseId::class.java)

        processAnnotations(PayInfoXml::class.java)
        processAnnotations(BkiForLoan::class.java)
        processAnnotations(CommitmentAbsent::class.java)
        processAnnotations(Commitments::class.java)
        processAnnotations(ErrorRequest::class.java)
        processAnnotations(KBkiSource::class.java)
        processAnnotations(Loan::class.java)
        processAnnotations(PayMonthly::class.java)
        processAnnotations(SubjectNotFound::class.java)

        addPermission(AnyTypePermission.ANY)
    }

    fun xmlToString(xmlObject: Any): String = xstream.toXML(xmlObject)

    fun xmlToFile(xmlObject: Any, file: File): String {

        val xml = xmlToString(xmlObject)

        file.writeText(xml, Charset.forName(CHARSET))

        return xml
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> loadFromFile(file: File): T {
        return xstream.fromXML(file) as T
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