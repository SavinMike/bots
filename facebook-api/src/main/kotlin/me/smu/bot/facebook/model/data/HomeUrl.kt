package me.smu.bot.facebook.model.data

/**
 * @param url: The URL to be invoked from drawer.
 * - Must be whitelisted.
 * - Must use https.
 *
 * @param inTest: Controls whether users not assigned a role for your bot or its Facebook page can see the Chat Extension.
 * This should be set to true until the Chat Extension is ready to be used by others.
 *
 * @param webviewShareButton: Controls whether the share button in the webview is enabled. Either show or hide, defaults to "hide".
 */
data class HomeUrl(val url: String,
                   val inTest: Boolean,
                   val webviewShareButton: WebViewShareButton = WebViewShareButton.hide) {
    val webviewHeightRatio: WebviewHeightRatio = WebviewHeightRatio.tall
}

enum class WebViewShareButton {
    show, hide
}