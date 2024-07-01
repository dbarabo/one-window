package ru.barabo.onewin.xml.answer

import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamImplicit

@XStreamAlias("Обязательства")
class Commitments() {

    @XStreamImplicit(itemFieldName = "БКИ")
    var bkiList: List<BkiForLoan>? = null

    override fun toString(): String {
        return bkiList?.joinToString("\n") {it.toString()} ?: "@null"
    }
}