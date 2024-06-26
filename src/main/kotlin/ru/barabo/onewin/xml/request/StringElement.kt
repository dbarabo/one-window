package org.example.ru.barabo.onewin.xml.request

import com.thoughtworks.xstream.annotations.XStreamConverter
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter

@XStreamConverter(value = ToAttributedValueConverter::class, strings = ["value"])
class StringElement(var value: String? = null)