@file:Suppress("EnumEntryName")

package me.smu.bot.facebook.model.data

enum class ButtonType {
    web_url
}

interface Button {
    val type: ButtonType
    val title: String
}

enum class WebviewHeightratio {
    compact, tall, full
}

/**
 * @property title: Button title. 20 character limit.
 * @property url: This URL is opened in a mobile browser when the button is tapped. Must use HTTPS protocol
 *                if messenger_extensions is true.
 * @property webviewHeightRatio: Optional. Height of the Webview. Valid values: compact, tall, full. Defaults to full.
 * @property messengerExtensions: Optional. Must be true if using Messenger Extensions.
 * @property fallbackUrl: The URL to use on clients that don't support Messenger Extensions. If this is not defined,
 *                        the url will be used as the fallback. It may only be specified if messenger_extensions is true.
 */
data class UrlButton(
        val url: String,
        override val title: String,
        val webviewHeightRatio: WebviewHeightratio = WebviewHeightratio.full,
        val messengerExtensions: Boolean = false,
        val fallbackUrl: String? = null
) : Button {
    override val type: ButtonType = ButtonType.web_url
}

