package ru.barabo.onewin.xml.answer

import com.thoughtworks.xstream.annotations.XStreamAlias

@XStreamAlias("КБКИ")
class KBkiSource() {

    @XStreamAlias("ОГРН")
    var ogrn: String = ""

    @XStreamAlias("ПоСостояниюНа")
    var onDateTimeState: String = ""

    @XStreamAlias("ИдентификаторОтвета")
    var uuidResponse: String = ""

    @XStreamAlias("Ошибка")
    var error: ErrorRequest? = null

    @XStreamAlias("СубъектНеНайден")
    var subjectNotFound: SubjectNotFound? = null

    @XStreamAlias("ОбязательствНет")
    var commitmentAbsent: CommitmentAbsent? = null

    @XStreamAlias("Обязательства")
    var commitments: Commitments? = null

    override fun toString(): String {
        return "ОГРН=$ogrn onDateTimeState=$onDateTimeState uuidResponse=$uuidResponse СубъектНеНайден=$subjectNotFound Обязательства=$commitments"
    }
}