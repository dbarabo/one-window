package ru.barabo.onewin.xml.answer

import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamImplicit

@XStreamAlias("БКИ")
class BkiForLoan() {

    @XStreamAlias("ОГРН")
    var ogrn: String = ""

    @XStreamImplicit(itemFieldName = "Договор")
    var loanList: List<Loan>? = null

    override fun toString(): String {
        return "ОГРН=$ogrn\n" +
                loanList?.joinToString("\n") { it.toString() }
    }
}