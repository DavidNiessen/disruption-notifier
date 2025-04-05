package dev.skillcode.disruptionnotifier.common.util

interface RichTextUtil {

    fun newLine(amount: Int = 1)

    fun paragraph(text: String)

    fun bold(text: String)

    fun italic(text: String)

    fun heading(text: String)

    fun subHeading(text: String)

    fun subText(text: String)

    fun link(url: String, alias: String?)

    fun indent(text: String)

}
