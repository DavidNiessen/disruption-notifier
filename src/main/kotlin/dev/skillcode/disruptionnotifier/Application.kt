package dev.skillcode.disruptionnotifier

import dev.skillcode.disruptionnotifier.common.properties.WebHookProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableConfigurationProperties(WebHookProperties::class)
@EnableScheduling
class PtDisruptionNotifierApplication

fun main(args: Array<String>) {
    runApplication<PtDisruptionNotifierApplication>(*args)
}
