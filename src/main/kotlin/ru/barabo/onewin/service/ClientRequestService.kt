package ru.barabo.onewin.service

import ru.barabo.afina.AfinaOrm
import ru.barabo.db.annotation.ParamsSelect
import ru.barabo.db.service.StoreFilterService
import ru.barabo.onewin.entity.ClientRequest
import ru.barabo.onewin.xml.request.RequestXml
import ru.barabo.onewin.xml.request.XmlBuilder

class ClientRequestService : StoreFilterService<ClientRequest>(AfinaOrm, ClientRequest::class.java), ParamsSelect {

    private var clientId: Long? = null

    override fun selectParams(): Array<Any?> = arrayOf(clientId)

    fun requestByClientId(clientId: Number, isOneWinRequest: Boolean): RequestXml {

        this.clientId = clientId.toLong()
        initData()

        val client = this.selectedEntity()!!

        checkClient(client)

        return RequestXml(client, isOneWinRequest)
    }

    fun checkClient(clientId: Number) {
        checkClient(getClientById(clientId))
    }

    private fun getClientById(clientId: Number): ClientRequest {
        this.clientId = clientId.toLong()
        initData()

        return selectedEntity()!!
    }


    private fun checkClient(client: ClientRequest) {

        if(client.lastName.isEmpty() ||
           client.firstName.isEmpty() ) {
            throw Exception("Не заданы ФИО у клиента id=${client.id}")
        }

        if(client.lineDoc.isEmpty() ||
            client.numberDoc.isEmpty() ||
            client.dateOutDoc == null ) {
            throw Exception("Не задан паспорт у клиента id=${client.id}")
        }

        if(client.agreeDateOut == null ) {
            throw Exception("Дата согласия не задана или просрочена у клиента id=${client.id}")
        }
    }
}