package ru.barabo.onewin.xml.answer

import com.thoughtworks.xstream.annotations.XStreamAlias

@XStreamAlias("Договор")
class Loan() {

    @XStreamAlias("УИД")
    var uuid: String = ""

    @XStreamAlias("СреднемесячныйПлатеж")
    var payMonthly: PayMonthly? = null

    override fun toString(): String {
        return "УИД=$uuid СМП=$payMonthly"
    }
}