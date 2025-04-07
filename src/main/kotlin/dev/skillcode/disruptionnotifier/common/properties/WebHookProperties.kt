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
)

data class WebHookColors(
    val success: String,
    val failure: String,
)
