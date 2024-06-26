package org.example.ru.barabo.onewin.xml.request

import com.thoughtworks.xstream.annotations.XStreamAlias

@XStreamAlias("Субъект")
class Subject() {

    constructor(fio: Fio, passport: Passport, birthDay: String) : this() {

        this.fio = fio

        this.passport = passport

        this.birthDay = birthDay
    }

    @XStreamAlias("ФИО")
    var fio: Fio? = null

    @XStreamAlias("ДатаРождения")
    var birthDay: String? = null

    @XStreamAlias("ДокументЛичности")
    var passport: Passport? = null
}