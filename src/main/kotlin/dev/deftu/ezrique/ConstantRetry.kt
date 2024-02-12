package dev.deftu.ezrique

import dev.kord.gateway.retry.Retry
import kotlinx.coroutines.delay
import org.apache.logging.log4j.LogManager
import java.util.concurrent.atomic.AtomicLong

private val constantRetryLogger = LogManager.getLogger("Constant Retry")
private const val MAX_DELAY = 30_000L

class ConstantRetry : Retry {
    private val attempts = AtomicLong(0L)
    override val hasNext = true

    override fun reset() {
        attempts.set(0)
    }

    override suspend fun retry() {
        val attempt = attempts.getAndIncrement()
        val delay = (attempt * 1000).coerceAtMost(MAX_DELAY)
        constantRetryLogger.info(if (attempt == 0L) "Retrying in $delay ms" else "Retrying for the ${attempts.get().withOrdinalIndicator()} time in $delay ms")
        delay(delay)
    }

    private fun Long.withOrdinalIndicator(): String {
        val number = this
        val suffixes = listOf("th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th")
        val suffix = if (number % 100 in 11..13) "th" else suffixes[(number % 10).toInt()]
        return "$number$suffix"
    }
}
