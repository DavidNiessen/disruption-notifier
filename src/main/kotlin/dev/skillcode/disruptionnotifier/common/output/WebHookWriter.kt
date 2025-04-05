package dev.skillcode.disruptionnotifier.common.output

import org.springframework.beans.factory.annotation.Value
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
class WebHookWriter(
    @Value("\${webhook.title}") private val title: String,
    @Value("\${webhook.avatar}") private val avatar: String,
    @Value("\${webhook.colors.success}") private val successColor: String,
    @Value("\${webhook.colors.failure}") private val failureColor: String,
    @Value("\${webhook.url}") private val url: String,
) : DataWriter {

    override fun writeData(data: OutputData) {
        val httpClient = RestTemplate()
        val headers = HttpHeaders()

        headers.apply {
            set(HttpHeaders.CONTENT_TYPE, "application/json")
        }

        val entity = HttpEntity<Any>(buildJson(data), headers)

        httpClient.exchange<String>(
            url,
            HttpMethod.POST,
            entity
        )
    }

    private fun buildJson(data: OutputData): String {
        return """
        {
            "username": "$title",
            "avatar_url": "$avatar",
            "embeds": [{
                "color": "${if (data.success) successColor else failureColor}",
                "title": "$title",
                "description": "${data.payload}",
                "timestamp": "${
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneOffset.UTC).format(Instant.now())
        }"   
            }]
        }
        """.trimIndent()
    }
}
