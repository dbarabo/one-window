package org.example.ru.barabo.onewin.xml.request

import com.thoughtworks.xstream.annotations.XStreamAlias
import org.example.ru.barabo.onewin.entity.ClientRequest
import java.util.*

@XStreamAlias("ЗапросСведенийОПлатежах")
class RequestXml() {

    constructor (clientRequest: ClientRequest) : this() {

        this.requestInfo = RequestInfo(clientRequest)
    }

    @XStreamAlias("Версия")
    var version: String = "1.2"

    @XStreamAlias("ИдентификаторЗапроса")
    var uuid: String = UUID.randomUUID().toString()

    @XStreamAlias("ТипЗапроса")
    var typeRequest: String = "1" // 2 - режим одного окна

    @XStreamAlias("Абонент")
    var ourSubscriber: Subscriber = Subscriber()

    @XStreamAlias("Запрос")
    var requestInfo: RequestInfo = RequestInfo()
}