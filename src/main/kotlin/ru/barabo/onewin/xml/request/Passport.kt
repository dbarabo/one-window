package org.example.ru.barabo.onewin.xml.request

import com.thoughtworks.xstream.annotations.XStreamAlias
import java.time.LocalDate

@XStreamAlias("ДокументЛичности")
class Passport() {

    constructor(line: String, number: String, dateOut: LocalDate) : this() {

        this.line = line
        this.number = number

        this.dateOut = dateOut.toXmlString()
    }

    @XStreamAlias("КодДУЛ")
    var code: String = "21"

    @XStreamAlias("Серия")
    var line: String = ""

    @XStreamAlias("Номер")
    var number: String = ""

    @XStreamAlias("ДатаВыдачи")
    var dateOut: String = ""

    @XStreamAlias("Гражданство")
    var citizenship: String = "643"
}