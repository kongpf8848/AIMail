package com.kongpf8848.dmail.exchange

import java.util.concurrent.ConcurrentHashMap

object ExchangeManager {
    private val exchangeSessionMap: MutableMap<String, ExchangeSession> = ConcurrentHashMap()

    fun addExchangeSession(username: String, password: String, domain: String, url: String) {
        val session = ExchangeSession(username, password, domain, url)
        exchangeSessionMap["$username@$password"] = session
    }

    fun getSession(address: String): ExchangeSession? {
        return exchangeSessionMap[address]
    }


}