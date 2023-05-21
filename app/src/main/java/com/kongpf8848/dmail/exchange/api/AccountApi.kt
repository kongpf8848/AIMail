package com.kongpf8848.dmail.exchange.api

import com.kongpf8848.dmail.exchange.DMExchangeService
import com.kongpf8848.dmail.exchange.ExchangeSession
import microsoft.exchange.webservices.data.core.PropertySet
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName
import microsoft.exchange.webservices.data.core.service.folder.Folder

object AccountApi {

    fun checkAccount(session: ExchangeSession, successCallBack:(String)->Unit, failCallBack:(Exception)->Unit) {
        try {
            Thread {
                val service = DMExchangeService.getInstance(session)
                val folder = Folder.bind(service, WellKnownFolderName.Inbox, PropertySet.IdOnly)
                successCallBack.invoke(folder.id.uniqueId)

            }.start()

        } catch (e: Exception) {
            failCallBack.invoke(e)
        }
    }
}