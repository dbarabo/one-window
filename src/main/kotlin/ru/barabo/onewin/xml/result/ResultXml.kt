package ru.barabo.onewin.xml.result

import com.thoughtworks.xstream.annotations.XStreamAlias
import ru.barabo.onewin.xml.answer.ErrorRequest

@XStreamAlias("Результат")
class ResultXml() {

    @XStreamAlias("Версия")
    var version: String? = null

    @XStreamAlias("ОГРН")
    var ogrn: String? = null

    @XStreamAlias("ИдентификаторОтвета")
    var responseId: ResponseId? = null

    @XStreamAlias("Ошибка")
    var errorRequest: ErrorRequest? = null
}