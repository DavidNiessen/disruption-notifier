package dev.skillcode.disruptionnotifier.providers.sbahn

import dev.skillcode.disruptionnotifier.common.util.RichTextUtil
import dev.skillcode.disruptionnotifier.common.util.RichTextUtilImpl
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class DataToFormatedStringConverter :
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

                richTextUtil.apply {
                    bold(disruptionData.title)
                    newLine()
                    paragraph("betroffene Linien > ")
                    indent(lines)
                    newLine(2)
                }
            }
        }

        richTextUtil.apply {
            newLine()
            subText("built by ")
            link("https://skillcode.dev", "David Nießen")
        }

        return richTextUtil.toString()
    }
}
