package dev.skillcode.disruptionnotifier.common.configuration

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

@Configuration
class SeleniumConfiguration {

    @Bean(destroyMethod = "quit")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    fun webDriver(): WebDriver {
        val options = ChromeOptions().apply {
            // TODO allow disabling headless mode for debugging
            addArguments("--headless")
        }
        // TODO allow to set browser type in config
        val driver = ChromeDriver(options)
        return driver
    }
}
