package org.example.ru.barabo.onewin.xml.request

import com.thoughtworks.xstream.annotations.XStreamAlias

@XStreamAlias("Абонент")
class Subscriber {

    @XStreamAlias("ЮридическоеЛицо")
    var jurical: Jurical = ourBankInnOnly
}

