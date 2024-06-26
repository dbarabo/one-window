package org.example.ru.barabo.onewin.xml.request

import com.thoughtworks.xstream.annotations.XStreamAlias
import java.time.LocalDate

@XStreamAlias("Согласие")
class Agreement() {

    constructor (dateOut: LocalDate, validity: Int) : this() {
        this.dateOut = dateOut.toXmlString()

        this.validity = validity.toString()

        if(validity == 3) {
            pact = Pact(dateOut)
        }
    }

    @XStreamAlias("ДатаВыдачи")
    var dateOut: String = ""

    @XStreamAlias("СрокДействия")
    var validity: String = "3" // 3 - В течение срока действия согласия с субъектом кредитной истории были заключены договор займа (кредита), 1- на год 2- на 6мес

    // TODO @XStreamAlias("ОснованиеПередачи")

    @XStreamAlias("ОбОтветственностиПредупрежден")
    var isResponsibility: String = "1"

    @XStreamAlias("Выдано")
    var issued: Issued = Issued()

    @XStreamAlias("Цель")
    var target: Target = Target()

    @XStreamAlias("Договор")
    var pact: Pact? = null
}

@XStreamAlias("Договор")
class Pact() {

    constructor (datePact: LocalDate) : this() {
        this.datePact = datePact.toXmlString()
    }

    @XStreamAlias("Дата")
    var datePact: String = ""
}

@XStreamAlias("Выдано")
class Issued {

    @XStreamAlias("ЮридическоеЛицо")
    var  jurical: Jurical = ourBankFullNameOnly
}

@XStreamAlias("Цель")
class Target {

    @XStreamAlias("КодЦели")
    var  code: String = "5"
}

