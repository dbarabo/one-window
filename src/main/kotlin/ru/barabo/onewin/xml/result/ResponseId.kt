package ru.barabo.onewin.xml.result

import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamConverter
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter

@XStreamAlias("ИдентификаторОтвета")
@XStreamConverter(value = ToAttributedValueConverter::class, strings = ["value"])
class ResponseId(var value: String? = null) {

    @XStreamAlias("ИдентификаторЗапроса")
    var requestId: String? = null
}