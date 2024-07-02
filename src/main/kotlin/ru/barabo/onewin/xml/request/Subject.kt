package ru.barabo.onewin.xml.request

import com.thoughtworks.xstream.annotations.XStreamAlias
import java.time.LocalDate

@XStreamAlias("Субъект")
class Subject() {

    constructor(fio: Fio, passport: Passport, birthDay: String) : this() {

        this.fio = fio

        this.birthDay = StringElement(birthDay)

        this.passport = passport
    }

    @XStreamAlias("ФИО")
    var fio: Fio? = null

    @XStreamAlias("ДатаРождения")
    var birthDay: StringElement? = null

    @XStreamAlias("ДокументЛичности")
    var passport: Passport? = null

    override fun toString(): String {
        return "ФИО=$fio ДатаРождения=$birthDay ДокументЛичности=$passport"
    }

    val birthDayLocal: LocalDate? = birthDay?.value?.xmlDateToLocal()
}