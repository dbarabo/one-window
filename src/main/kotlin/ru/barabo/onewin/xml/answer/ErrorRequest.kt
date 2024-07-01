package ru.barabo.onewin.xml.answer

import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamConverter
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter

@XStreamAlias("Ошибка")
@XStreamConverter(value = ToAttributedValueConverter::class, strings = ["value"])
class ErrorRequest(var value: String? = null) {

    @XStreamAlias("Код")
    var code: Int = 0
}