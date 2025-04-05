package dev.skillcode.disruptionnotifier.common.configuration

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.RemoteWebDriver
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import java.net.URL

@Configuration
class SeleniumConfiguration(
    @Value("\${remote.driver.url}") private val seleniumUrl: String,
) {

    @Bean(destroyMethod = "quit")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    fun webDriver(): WebDriver {
        val options = ChromeOptions().apply {
            // TODO allow disabling headless mode for debugging
            addArguments("--headless=new")
        }
        // TODO allow to set browser type in config
        val driver = RemoteWebDriver(URL(seleniumUrl), options)
        return driver
    }
}
