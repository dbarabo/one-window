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

    override fun toString(): String {
        return "Фамилия=$lastName Имя=$firstName Отчество=$papaName"
    }

    val fullName: String
        get() = "${lastName?.value?:""} ${firstName?.value?:""} ${papaName?.value?:""}".trim()
}