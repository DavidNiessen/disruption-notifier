package dev.skillcode.disruptionnotifier.common.provider

import dev.skillcode.disruptionnotifier.common.dataprocessing.DataProcessor
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.springframework.test.context.junit.jupiter.SpringExtension
import kotlin.test.assertFalse

@ExtendWith(SpringExtension::class)
class ProviderCacheTest {

    private lateinit var cache: ProviderCache
    private lateinit var mockProvidier: TransportProvider

    @Mock
    private lateinit var mockDataProcessor: DataProcessor

    private val mockData = "abc12345"

    @BeforeEach
    fun beforeEach() {
        cache = ProviderCache()
        mockProvidier = TransportProvider(
            name = "mockProvider",
            url = "https://test.de",
            dataProcessor = mockDataProcessor,
        )
    }

    @Test
    fun returnsFalseIfNotInCache() {
        assertFalse(cache.isInCache(mockProvidier, mockData))
    }

    @Test
    fun returnsFalseIfIsInCacheButNotEqual() {
        // add data to cache
        cache.isInCache(mockProvidier, mockData)

        assertFalse(cache.isInCache(mockProvidier, "test"))
    }

    @Test
    fun returnsTrueIfIsInCacheAndEqual() {
        // add data to cache
        cache.isInCache(mockProvidier, mockData)

        assertTrue(cache.isInCache(mockProvidier, mockData))
    }

}
