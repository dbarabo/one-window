package org.example.ru.barabo.onewin.xml.request

import com.thoughtworks.xstream.annotations.XStreamAlias
import java.util.*

@XStreamAlias("ЮридическоеЛицо")
class Jurical {

    @XStreamAlias("ИНН")
    var inn: String = ""

    @XStreamAlias("ОГРН")
    var ogrn: String = ""

    @XStreamAlias("КодыВидаПользователя")
    var codeTypeUser: String? = null

    @XStreamAlias("ПризнакРегистрацииРФ")
    var isRegisterInRF: String? = null

    @XStreamAlias("ПолноеНаименование")
    var fullName: StringElement? = null

    @XStreamAlias("СокращенноеНаименование")
    var name: StringElement? = null
}

val ourBankInnOnly: Jurical = Jurical().apply {
    inn =  "2540015598"
    ogrn = "1022500001325"
    codeTypeUser = null
}

val ourBank: Jurical = Jurical().apply {
    inn = ourBankInnOnly.inn
    ogrn = ourBankInnOnly.ogrn
    codeTypeUser = "2" // bank
    isRegisterInRF = "1"
    fullName = StringElement("Общество с ограниченной ответственностью \"Примтеркомбанк\"".uppercase(Locale.getDefault()))
    name = StringElement("ООО \"Примтеркомбанк\"".uppercase(Locale.getDefault()) )
}

val ourBankFullNameOnly: Jurical = Jurical().apply {
    inn = ourBankInnOnly.inn
    ogrn = ourBankInnOnly.ogrn
    fullName = StringElement(ourBank.fullName!!.value)
}