package dev.skillcode.disruptionnotifier.providers.sbahn

import dev.skillcode.disruptionnotifier.common.dataprocessing.DataProcessor
import dev.skillcode.disruptionnotifier.common.exception.ElementNotFoundExceptions
import dev.skillcode.disruptionnotifier.common.output.OutputData
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

private const val HEADER_CLASS_NAME = "o-constructions-group-header"
private const val CONSTRUCTIONS_CLASS_NAME = "c-constructions"
private const val ANNOUNCEMENT_CLASS_NAME = "c-construction-announcement"
private const val ANNOUNCEMENT_HEADING_CLASS_NAME = "o-construction-announcement-title__heading"
private const val DISRUPTION_HEADER_NAME = "störungen"

@Service
class SBahnDataProcessor(private val dataConverter: DataToFormatedStringConverter) : DataProcessor {

    private val logger = LoggerFactory.getLogger(SBahnDataProcessor::class.java)

    override fun processData(driver: WebDriver): OutputData {
        var data = emptyList<SBahnDisruptionData>()
        try {
            data = collectData(driver)
        } catch (exception: ElementNotFoundExceptions) {
            logger.warn(exception.message)
        } catch (exception: Exception) {
            logger.error("Failed to process data: ", exception)
        }

        return OutputData(data.isNotEmpty(), dataConverter.convert(data))
    }

    private fun collectData(driver: WebDriver): List<SBahnDisruptionData> {
        val headers = driver.findElements(By.className(HEADER_CLASS_NAME))

        if (headers.isEmpty()) {
            throw ElementNotFoundExceptions(HEADER_CLASS_NAME)
        }

        val disruptionHeader = findDisruptionHeader(headers)
            ?: throw ElementNotFoundExceptions("disruption header ($DISRUPTION_HEADER_NAME)")

        val constructionsElement =
            driver.findElements(By.className(CONSTRUCTIONS_CLASS_NAME))[0] ?: throw ElementNotFoundExceptions(
                CONSTRUCTIONS_CLASS_NAME
            )

        val announcementElements = findAnnouncementElements(constructionsElement, disruptionHeader)

        val dataList = mutableListOf<SBahnDisruptionData>()

        announcementElements.forEach {
            val title = it.findElements(By.className(ANNOUNCEMENT_HEADING_CLASS_NAME))[0]
                ?.text ?: "null"

            val lines = it.getAttribute("data-lines")?.split(",") ?: emptyList()

            dataList.add(SBahnDisruptionData(title, lines))
        }

        return dataList
    }


    private fun findAnnouncementElements(constructionsElement: WebElement, headerElement: WebElement) =
        sequence<WebElement> {
            val children = constructionsElement.findAll()

            children.forEach {
                if (it.isHeader() && !it.isDisruptionHeader()) {
                    return@forEach
                }

                if (it.isAnnouncementElement()) {
                    yield(it)
                }
            }
        }

    private fun findDisruptionHeader(headers: List<WebElement>): WebElement? = headers.find {
        val headingElement = it.findElements(By.tagName("h3")).firstOrNull()
            ?: throw ElementNotFoundExceptions("h3")

        val heading = headingElement.text

        if (heading.isNullOrBlank()) {
            throw ElementNotFoundExceptions("h3")
        }

        return@find heading.lowercase().contains(DISRUPTION_HEADER_NAME)
    }

    private fun WebElement.isHeader(): Boolean =
        this.getAttribute("class")
            ?.lowercase()
            ?.split(" ")
            ?.first()
            ?.equals(HEADER_CLASS_NAME) ?: false

    private fun WebElement.isDisruptionHeader(): Boolean =
        this.isHeader() && this.text.lowercase().contains(DISRUPTION_HEADER_NAME)

    private fun WebElement.isAnnouncementElement(): Boolean =
        this.getAttribute("class")
            ?.lowercase()
            ?.split(" ")
            ?.first()
            ?.equals(ANNOUNCEMENT_CLASS_NAME) ?: false

    private fun WebElement.findAll() = this.findElements(By.xpath("*"))
}
