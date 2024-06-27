package ru.barabo.onewin.xml.request

import com.thoughtworks.xstream.annotations.XStreamAlias
import java.time.LocalDate

@XStreamAlias("ДокументЛичности")
class Passport() {

    constructor(line: String, number: String, dateOut: LocalDate) : this() {

        this.line = StringElement(line)
        this.number = StringElement(number)

        this.dateOut = StringElement(dateOut.toXmlString() )
    }

    @XStreamAlias("КодДУЛ")
    var code: String = "21"

    @XStreamAlias("Серия")
    var line: StringElement? = null

    @XStreamAlias("Номер")
    var number: StringElement? = null

    @XStreamAlias("ДатаВыдачи")
    var dateOut: StringElement? = null

    @XStreamAlias("Гражданство")
    var citizenship: StringElement = StringElement("643")
}