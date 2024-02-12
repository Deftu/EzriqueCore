package dev.deftu.ezrique

import dev.kord.core.builder.kord.KordBuilder
import dev.kord.core.event.Event
import dev.kord.gateway.DefaultGateway
import dev.kord.gateway.ratelimit.IdentifyRateLimiter
import kotlinx.coroutines.flow.MutableSharedFlow

val events = MutableSharedFlow<Event>()

fun KordBuilder.setup() {
    eventFlow = events
    gateways { resources, shards ->
        val rateLimiter = IdentifyRateLimiter(resources.maxConcurrency, defaultDispatcher)
        shards.map {
            DefaultGateway {
                client = resources.httpClient
                identifyRateLimiter = rateLimiter
                reconnectRetry = ConstantRetry()
            }
        }
    }
}
