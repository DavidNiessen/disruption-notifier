package dev.skillcode.disruptionnotifier.common.util

import dev.skillcode.disruptionnotifier.common.properties.WebHookColors
import dev.skillcode.disruptionnotifier.common.properties.WebHookProperties
import jakarta.annotation.PostConstruct
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class WebHookLogger(
    private val webHookWriter: WebHookWriter,
    private val webHookProperties: WebHookProperties,
    @Value("\${app.log-to-webhook}") val logToWebHook: Boolean,
) {

    private val loggers = mutableMapOf<String, Logger>()

    private lateinit var colors: WebHookColors

    @PostConstruct
    fun init() {
        colors = webHookProperties.colors
    }

    fun info(message: String, clazz: Class<*>) {
        getLogger(clazz).info(message)
        if (logToWebHook) log(message, colors.log)
    }

    fun warn(message: String, clazz: Class<*>) {
        getLogger(clazz).warn(message)
        if (logToWebHook) log(message, colors.warn)
    }

    fun error(message: String, clazz: Class<*>) {
        getLogger(clazz).error(message)
        if (logToWebHook) log(message, colors.failure)
    }

    private fun log(message: String, color: String) {
        webHookProperties.logging.urls.forEach {
            webHookWriter.writeData(it, message, color)
        }
    }

    private fun getLogger(clazz: Class<*>): Logger =
        loggers.getOrPut(clazz.name) { LoggerFactory.getLogger(clazz) }


}
