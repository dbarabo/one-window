package ru.barabo.onewin.xml.request

import com.thoughtworks.xstream.annotations.XStreamAlias

@XStreamAlias("Источник")
class Source {

    @XStreamAlias("ЮридическоеЛицо")
    var jurical: Jurical = ourBank
}