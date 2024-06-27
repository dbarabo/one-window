package ru.barabo.onewin.xml.request

import com.thoughtworks.xstream.annotations.XStreamAlias
import java.util.*

@XStreamAlias("ЮридическоеЛицо")
class Jurical {

    @XStreamAlias("ИНН")
    var inn: StringElement? = null

    @XStreamAlias("ОГРН")
    var ogrn: StringElement? = null

    @XStreamAlias("КодВидаПользователя")
    var codeTypeUser: String? = null

    @XStreamAlias("ПризнакРегистрацииРФ")
    var isRegisterInRF: String? = null

    @XStreamAlias("ПолноеНаименование")
    var fullName: StringElement? = null

    @XStreamAlias("СокращенноеНаименование")
    var name: StringElement? = null
}

val ourBankInnOnly: Jurical = Jurical().apply {
    inn =  StringElement("2540015598")
    ogrn = StringElement("1022500001325")
    codeTypeUser = null
}

val ourBank: Jurical = Jurical().apply {
    inn = StringElement(ourBankInnOnly.inn!!.value)
    ogrn = StringElement(ourBankInnOnly.ogrn!!.value)
    codeTypeUser = "2" // bank
    isRegisterInRF = "1"
    fullName = StringElement("Общество с ограниченной ответственностью \"Примтеркомбанк\"".uppercase(Locale.getDefault()))
    name = StringElement("ООО \"Примтеркомбанк\"".uppercase(Locale.getDefault()) )
}

val ourBankFullNameOnly: Jurical = Jurical().apply {
    inn = StringElement(ourBankInnOnly.inn!!.value)
    ogrn = StringElement(ourBankInnOnly.ogrn!!.value)
    fullName = StringElement(ourBank.fullName!!.value)
}