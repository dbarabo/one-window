package ru.barabo.onewin.xml.answer

import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamImplicit
import ru.barabo.onewin.xml.request.Subject

@XStreamAlias("СведенияОПлатежах")
class PayInfoXml() {

    @XStreamAlias("Версия")
    var version: String = ""

    @XStreamAlias("ИдентификаторЗапроса")
    var uuidRequest: String = ""

    @XStreamAlias("ИдентификаторОтвета")
    var uuidResponse: String = ""

    @XStreamAlias("ТитульнаяЧасть")
    var subject: Subject? = null

    @XStreamAlias("ОГРН")
    var ogrn: String = ""

    @XStreamAlias("ТипОтвета")
    var typeResponse: String = ""

    @XStreamImplicit(itemFieldName = "КБКИ")
    var bkiList: List<KBkiSource>? = null

    override fun toString(): String =
        " ИдентификаторЗапроса=$uuidRequest ОГРН=$ogrn ТипОтвета=$typeResponse subject=$subject\n" +
            bkiList?.joinToString("\n") { it.toString() }
}