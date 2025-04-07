package dev.skillcode.disruptionnotifier.common.util

import dev.skillcode.disruptionnotifier.common.properties.WebHookProperties
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
    private val webHookProperties: WebHookProperties,
) {

    fun writeData(
        url: String,
        payload: String,
        color: String,
        username: String = webHookProperties.title,
        avatarUrl: String = webHookProperties.avatar,
        title: String = webHookProperties.title,
    ) {
        val httpClient = RestTemplate()
        val headers = HttpHeaders()

        headers.apply {
            set(HttpHeaders.CONTENT_TYPE, "application/json")
        }

        val json = buildJson(username, avatarUrl, color, title, payload)
        val entity = HttpEntity<Any>(json, headers)

        httpClient.exchange<String>(
            url,
            HttpMethod.POST,
            entity
        )
    }

    private fun buildJson(
        username: String,
        avatarUrl: String,
        color: String,
        title: String,
        description: String,
    ): String {
        return """
        {
            "username": "$username",
            "avatar_url": "$avatarUrl",
            "embeds": [{
                "color": "$color",
                "title": "$title",
                "description": "$description",
                "timestamp": "${
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneOffset.UTC).format(Instant.now())
        }"   
            }]
        }
        """.trimIndent()
    }
}
