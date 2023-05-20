package com.kongpf8848.dmail.exchange

import android.util.Log
import microsoft.exchange.webservices.data.core.ExchangeService
import microsoft.exchange.webservices.data.core.enumeration.misc.DateTimePrecision
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion
import microsoft.exchange.webservices.data.core.enumeration.misc.TraceFlags
import microsoft.exchange.webservices.data.credential.WebCredentials
import microsoft.exchange.webservices.data.misc.EwsTraceListener
import java.net.URI
import java.net.URISyntaxException
import java.util.*

class DMExchangeService(requestedServerVersion: ExchangeVersion) : ExchangeService(requestedServerVersion) {
    companion object {
        private const val traced = true
        fun getInstance(session: ExchangeSession): DMExchangeService {
            val service = DMExchangeService(ExchangeVersion.Exchange2010_SP2).apply {
                dateTimePrecision = DateTimePrecision.Milliseconds
                credentials = WebCredentials(session.username, session.password, session.domain)
            }
            try {
                service.url = URI("https://${session.url}/EWS/Exchange.asmx")
            } catch (e: URISyntaxException) {
                e.printStackTrace()
            }
            if (traced) {
                service.isTraceEnabled = traced
                service.traceFlags = EnumSet.of(
                    TraceFlags.DebugMessage,
                    TraceFlags.EwsRequest,
                    TraceFlags.EwsResponse
                )
                service.traceListener = object : EwsTraceListener() {
                    override fun trace(traceType: String, traceMessage: String) {
                        super.trace(traceType, traceMessage)
                        Log.d("JACK8",
                            "trace() called with: traceType = $traceType, traceMessage = $traceMessage"
                        )
                    }
                }
            }
            return service
        }
    }
}