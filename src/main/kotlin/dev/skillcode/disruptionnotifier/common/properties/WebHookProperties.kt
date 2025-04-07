package dev.skillcode.disruptionnotifier.common.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.NestedConfigurationProperty

@ConfigurationProperties(prefix = "webhook")
data class WebHookProperties(
    val title: String,
    val avatar: String,
    val urls: List<String>,

    @NestedConfigurationProperty
    val colors: WebHookColors,

    @NestedConfigurationProperty
    val logging: LoggingUrls,
)

data class WebHookColors(
    val success: String,
    val failure: String,
    val log: String,
    val warn: String,
)

data class LoggingUrls(
    val urls: List<String>,
)
