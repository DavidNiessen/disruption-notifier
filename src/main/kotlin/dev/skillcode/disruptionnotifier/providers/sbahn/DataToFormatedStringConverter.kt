package dev.skillcode.disruptionnotifier.providers.sbahn

import dev.skillcode.disruptionnotifier.common.util.RichTextUtil
import dev.skillcode.disruptionnotifier.common.util.RichTextUtilImpl
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class DataToFormatedStringConverter(
    @Value("\${app.author.name}") private val authorName: String,
    @Value("\${app.author.url}") private val authorUrl: String,
    @Value("\${app.repo}") private val repository: String,
) :
    Converter<List<SBahnDisruptionData>, String> {

    override fun convert(source: List<SBahnDisruptionData>): String {
        val richTextUtil: RichTextUtil = RichTextUtilImpl()

        if (source.isEmpty()) {
            richTextUtil.apply {
                subHeading("Keine Störungen vorhanden")
                newLine()
            }
        } else {
            source.map { disruptionData ->
                val lines = if (disruptionData.lines.isEmpty()) {
                    "unbekannt"
                } else {
                    disruptionData.lines.joinToString(", ") { it.uppercase() }
                }

                val title = disruptionData.title.takeIf { it.isNotBlank() } ?: "Keine Überschrift"

                richTextUtil.apply {
                    bold(title)
                    newLine()
                    paragraph("betroffene Linien > ")
                    indent(lines)
                    newLine(2)
                }
            }
        }

        richTextUtil.apply {
            subText("built by ")
            link(authorUrl, authorName)
            paragraph(" | ")
            link(repository, "Source Code")
        }

        return richTextUtil.toString()
    }
}
