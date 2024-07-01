package ru.barabo.onewin.xml.answer

import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamConverter
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter

@XStreamAlias("СреднемесячныйПлатеж")
@XStreamConverter(value = ToAttributedValueConverter::class, strings = ["value"])
class PayMonthly(var value: String? = null) {

    @XStreamAlias("ДатаРасчета")
    var dateCalculate: String = ""

    @XStreamAlias("Валюта")
    var currency: String = "RUB"

    override fun toString(): String {
        return "$value ДатаРасчета=$dateCalculate Валюта=$currency"
    }
}