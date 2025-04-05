package dev.skillcode.disruptionnotifier

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class PtDisruptionNotifierApplication

fun main(args: Array<String>) {
    runApplication<PtDisruptionNotifierApplication>(*args)
}
