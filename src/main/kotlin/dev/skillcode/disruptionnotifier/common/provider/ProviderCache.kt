package dev.skillcode.disruptionnotifier.common.provider

import org.springframework.stereotype.Component

@Component
class ProviderCache {

    private val cache = mutableMapOf<String, String>()

    fun isInCache(provider: TransportProvider, data: String): Boolean {
        if (!cache.containsKey(provider.name)) {
            cache[provider.name] = data
            return false
        }

        val cachedData = cache[provider.name]
        cache[provider.name] = data

        return cachedData?.trim() == data.trim()
    }
}
