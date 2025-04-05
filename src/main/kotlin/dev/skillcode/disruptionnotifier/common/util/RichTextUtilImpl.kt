package dev.skillcode.disruptionnotifier.common.util

class RichTextUtilImpl : RichTextUtil {

    private val builder: StringBuilder = StringBuilder()

    override fun newLine(amount: Int) {
        repeat(amount) {
            builder.append("\\n")
        }
    }

    override fun paragraph(text: String) {
        builder.append(text)
    }

    override fun bold(text: String) {
        builder.append("**$text**")
    }

    override fun italic(text: String) {
        builder.append("*$text*")
    }

    override fun heading(text: String) {
        builder.append("# $text")
    }

    override fun subHeading(text: String) {
        builder.append("## $text")
    }

    override fun subText(text: String) {
        builder.append("-# $text")
    }

    override fun link(url: String, alias: String?) {
        if (!alias.isNullOrBlank()) {
            builder.append("[$alias]($url)")
            return
        }

        builder.append(url)
    }

    override fun indent(text: String) {
        builder.append("`$text`")
    }

    override fun toString(): String {
        return builder.toString()
    }
}
