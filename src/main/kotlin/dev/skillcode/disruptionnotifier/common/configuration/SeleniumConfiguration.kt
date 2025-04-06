package dev.skillcode.disruptionnotifier.common.configuration

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.RemoteWebDriver
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import java.net.URI

@Configuration
class SeleniumConfiguration(
    @Value("\${remote.driver.url}") private val seleniumUrl: String,
    @Value("\${run.locally}") private val runLocally: Boolean,
) {

    @Bean(destroyMethod = "quit")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    fun webDriver(): WebDriver {
        val options = ChromeOptions().apply {
            addArguments("--headless=new")
        }

        val driver = if (runLocally) {
            ChromeDriver(options)
        } else {
            RemoteWebDriver(URI.create(seleniumUrl).toURL(), options)
        }

        return driver
    }
}
