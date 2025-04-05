package dev.skillcode.disruptionnotifier.common.dataprocessing

import dev.skillcode.disruptionnotifier.common.output.OutputData
import org.openqa.selenium.WebDriver

interface DataProcessor {

    /**
     * Processes scraped data for transport provider
     *
     * @param driver the WebDriver with preloaded data that can be processed
     * @return the formatted output message
     */
    fun processData(driver: WebDriver): OutputData

}
