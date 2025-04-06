package dev.skillcode.disruptionnotifier

import dev.skillcode.disruptionnotifier.common.provider.ProviderService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component("dnTaskScheduler")
class TaskScheduler(private val providerService: ProviderService) {

    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.MINUTES)
    private fun run() {
        providerService.scrapeAndProcessAll()
    }

}
