package dev.skillcode.disruptionnotifier.common.scraper

import org.openqa.selenium.Dimension
import org.openqa.selenium.WebDriver
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class WebScraper(private val webDriver: WebDriver) {

    private val windowSize = Dimension(1920, 1080)

    fun scrapePage(url: String): WebDriver {
        webDriver.manage().window().size = windowSize
        webDriver.get(url)
        return webDriver
    }

}
