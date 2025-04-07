package dev.skillcode.disruptionnotifier.common.output

import dev.skillcode.disruptionnotifier.common.properties.WebHookProperties
import dev.skillcode.disruptionnotifier.common.util.WebHookWriter
import org.springframework.stereotype.Component

@Component
class OutputWebHookWriter(
    private val webHookWriter: WebHookWriter,
    private val webHookProperties: WebHookProperties,
) : DataWriter {

    override fun writeData(data: OutputData) {
        val color = if (data.success) webHookProperties.colors.success else webHookProperties.colors.failure

        webHookProperties.urls.forEach {
            webHookWriter.writeData(it, data.payload, color)
        }
    }
}
