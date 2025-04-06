package dev.skillcode.disruptionnotifier.common.provider

import dev.skillcode.disruptionnotifier.common.output.WebHookWriter
import dev.skillcode.disruptionnotifier.common.scraper.WebScraper
import dev.skillcode.disruptionnotifier.providers.sbahn.SBahnDataProcessor
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.ObjectProvider
import org.springframework.stereotype.Service

@Service
class ProviderService(
    private val webScraperProvider: ObjectProvider<WebScraper>,
    private val sBahnProcessor: SBahnDataProcessor,
    private val webHookWriter: WebHookWriter,
    private val providerCache: ProviderCache,
) {

    private val logger = LoggerFactory.getLogger(ProviderService::class.java)
    private val providers = mutableListOf<TransportProvider>()

    fun registerProvider(provider: TransportProvider) {
        logger.info("provider registered: ${provider.name} (${provider.url}")
        providers.add(provider)
    }

    fun scrapeAndProcessAll() {
        providers.forEach {
            try {
                val driver = webScraperProvider.getObject().scrapePage(it.url)
                val processedData = it.dataProcessor.processData(driver)

                val isInCache = providerCache.isInCache(it, processedData.payload)

                if (!isInCache) {
                    logger.info("writing data: ${processedData.payload}")
                    webHookWriter.writeData(processedData)
                }
            } catch (exception: Exception) {
                logger.error("An error occurred:", exception)
            }
        }
    }

    // registers all providers
    @PostConstruct
    private fun registerProviders() {
        registerProvider(
            TransportProvider(
                "sbahn-berlin",
                "https://sbahn.berlin/fahren/bauen-stoerung/",
                sBahnProcessor
            )
        )
    }


}
