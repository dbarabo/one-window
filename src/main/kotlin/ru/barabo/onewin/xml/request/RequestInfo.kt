package org.example.ru.barabo.onewin.xml.request

import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamConverter
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter
import org.example.ru.barabo.onewin.entity.ClientRequest
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@XStreamAlias("Запрос")
class RequestInfo() {

    constructor(clientRequest: ClientRequest) : this() {
        subject = Subject(clientRequest.fio(), clientRequest.passport(), clientRequest.birthDayLocalDate().toXmlString())

        agreement = Agreement(clientRequest.agreeLocalDate(), clientRequest.validity.toInt())
    }

    @XStreamAlias("Дата")
    var date: String = LocalDate.now().toXmlString()

    @XStreamAlias("Источник")
    var source: Source = Source()

    @XStreamAlias("Субъект")
    var subject: Subject = Subject()

    @XStreamAlias("Согласие")
    var agreement: Agreement = Agreement()

    @XStreamAlias("Цель")
    var target: Target = Target()

    @XStreamAlias("СуммаОбязательства")
    var amount: AmountCommitment = AmountCommitment()
}

@XStreamAlias("СуммаОбязательства")
@XStreamConverter(value = ToAttributedValueConverter::class, strings = ["value"])
class AmountCommitment(var value: String = "100000") {

    @XStreamAlias("Валюта")
    var currency: String = "RUB"
}


fun LocalDate.toXmlString(): String =  DateTimeFormatter.ofPattern("yyyy-MM-dd").format(this)