package dev.deftu.ezrique

import io.sentry.Sentry

internal var isSentrySetup = false

fun setupSentry(url: String, projectName: String, projectVersion: String) {
    Sentry.init { options ->
        options.dsn = url
        options.release = "$projectName@$projectVersion"
    }

    isSentrySetup = true
}
