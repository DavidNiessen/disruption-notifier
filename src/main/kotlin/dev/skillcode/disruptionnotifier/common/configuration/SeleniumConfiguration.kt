package dev.skillcode.disruptionnotifier.common.configuration

import dev.skillcode.disruptionnotifier.common.util.WebHookLogger
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.RemoteWebDriver
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import java.net.URI
import java.time.Duration

@Configuration
class SeleniumConfiguration(
    @Value("\${remote.driver.url}") private val seleniumUrl: String,
    @Value("\${driver.wait.timeout.seconds}") private val timeout: Long,
    @Value("\${app.run.locally}") private val runLocally: Boolean,
    private val webHookLogger: WebHookLogger,
) {

    private val logger = LoggerFactory.getLogger(SeleniumConfiguration::class.java)

    @Bean(destroyMethod = "quit")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    fun webDriver(): WebDriver {
        val options = ChromeOptions().apply {
            addArguments("--headless=new")
            setImplicitWaitTimeout(Duration.ofSeconds(timeout))
        }

        val driver = if (runLocally) {
            webHookLogger.info("Connecting to local web driver...", javaClass)
            ChromeDriver(options)
        } else {
            webHookLogger.info("Connecting to remote web driver: $seleniumUrl", javaClass)
            RemoteWebDriver(URI.create(seleniumUrl).toURL(), options)
        }

        return driver
    }
}
