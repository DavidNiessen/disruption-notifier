package dev.skillcode.disruptionnotifier.common.output

import dev.skillcode.disruptionnotifier.common.properties.WebHookProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Component
@EnableConfigurationProperties(WebHookProperties::class)
class WebHookWriter(
    private val webHookProperties: WebHookProperties,
) : DataWriter {

    override fun writeData(data: OutputData) {
        val httpClient = RestTemplate()
        val headers = HttpHeaders()

        headers.apply {
            set(HttpHeaders.CONTENT_TYPE, "application/json")
        }

        webHookProperties.urls.forEach { url ->
            val entity = HttpEntity<Any>(buildJson(data), headers)

            httpClient.exchange<String>(
                url,
                HttpMethod.POST,
                entity
            )
        }
    }

    private fun buildJson(data: OutputData): String {
        return """
        {
            "username": "${webHookProperties.title}",
            "avatar_url": "${webHookProperties.avatar}",
            "embeds": [{
                "color": "${if (data.success) webHookProperties.colors.success else webHookProperties.colors.failure}",
                "title": "${webHookProperties.title}",
                "description": "${data.payload}",
                "timestamp": "${
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneOffset.UTC).format(Instant.now())
        }"   
            }]
        }
        """.trimIndent()
    }
}
