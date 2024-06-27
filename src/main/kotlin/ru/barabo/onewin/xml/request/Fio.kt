package ru.barabo.onewin.xml.request

import com.thoughtworks.xstream.annotations.XStreamAlias

@XStreamAlias("ФИО")
class Fio() {

    constructor(lastName: String, firstName: String, papaName: String) : this() {

        this.lastName =  StringElement(lastName)
        this.firstName =  StringElement(firstName)
        this.papaName =  StringElement(papaName)
    }

    @XStreamAlias("Фамилия")
    var lastName: StringElement? = null

    @XStreamAlias("Имя")
    var firstName: StringElement? = null

    @XStreamAlias("Отчество")
    var papaName: StringElement? = null
}