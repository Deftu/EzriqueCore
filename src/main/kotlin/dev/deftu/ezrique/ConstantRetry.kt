package dev.deftu.ezrique

import dev.kord.gateway.retry.Retry
import kotlinx.coroutines.delay
import org.apache.logging.log4j.LogManager
import java.util.concurrent.atomic.AtomicLong

private val LOGGER = LogManager.getLogger("Constant Retry")
private const val MAX_DELAY = 30_000L

class ConstantRetry : Retry {

    private val attempts = AtomicLong(0L)
    override val hasNext = true

    override fun reset() {
        LOGGER.info("Resetting constant retry")
        attempts.set(0)
    }

    override suspend fun retry() {
        val attempt = attempts.getAndIncrement()
        val delay = (attempt * 1000).coerceAtMost(MAX_DELAY)
        LOGGER.info(if (attempt == 0L) "Retrying in $delay ms" else "Retrying for the ${attempts.get().withOrdinalIndicator()} time in $delay ms")
        delay(delay)
    }

    private fun Long.withOrdinalIndicator(): String {
        val number = this
        val suffixes = listOf("st", "nd", "rd") + List(7) { "th" }
        val suffix = suffixes.getOrElse((number % 100).coerceAtMost(20).toInt()) { suffixes[(number % 10).toInt()] }
        return "$number$suffix"
    }

}
