package me.smu.bot.facebook.model.data

/**
 * @param locale: An array of objects that define the persistent menu for different locales. The menu with a locale
 * property that matches the person's locale will be displayed.
 * At least one object in the [PersistentMenu] array must specify "locale": "default". This is the menu we will fall back
 * to if no object has a locale property that matches the users locale. See the list of [supported locales](https://developers.facebook.com/docs/messenger-platform/messenger-profile/supported-locales).
 * @param composerInputDisabled: Disables the Messenger composer field if set to true.
 * This means your bot can only be interacted with via the [PersistentMenu], [PostbackButton], [Button], and webviews.
 * @param disabledSurfaces: Array of interface names to disable the persistent menu in.
 * @param callToActions: An array of top-level menu items for the persistent menu. A maximum of 3 items is allowed.
 * A maximum of two nested menus are supported. Required if "[composerInputDisabled]": true.
 */
data class PersistentMenu(val locale: String,
                          val composerInputDisabled: Boolean = false,
                          val disabledSurfaces: List<Surface>? = null,
                          val callToActions: List<MenuItem>? = null)

enum class Surface {
    customer_chat_plugin
}

/**
 * @param title: Title to display on the menu item. 30 character limit.
 * @param url: URL to open when the button is tapped. Required if [type] is [MenuType.web_url].
 * @param payload: Data that will be sent back to your webhook as a [me.smu.bot.facebook.model.network.webhook.event.MessagingPostbacksEvent].
 * Required if [type] is [MenuType.postback]. 1000 character limit.
 * @param callToActions: Nested [MenuItem] that will be expanded in next level. A maximum of 5 items is allowed.
 * Required if [type] is [MenuType.nested]. A persistent menu may have a maximum of two nested menus.
 * @param webviewHeightRatio: Height of the webview
 * @param messengerExtensions: Must be true if the item type is [MenuType.web_url] and the Messenger Extensions SDK will be used
 * in the webview.
 * @param fallbackUrl: URL to open in the webview for clients that do not support the Messenger Extensions SDK.
 * If this is not defined, the [url] will be used as the fallback. It may only be specified if "[messengerExtensions]": true.
 * @param webviewShareButton: Set to `hide` to disable sharing in the webview (for sensitive info).
 */
data class MenuItem(val type: MenuType,
                    val title: String,
                    val url: String? = null,
                    val payload: String? = null,
                    val callToActions: List<MenuItem>? = null,
                    val webviewHeightRatio: WebviewHeightRatio? = null,
                    val messengerExtensions: Boolean? = null,
                    val fallbackUrl: String? = null,
                    val webviewShareButton: String? = null)

enum class MenuType {
    /**
     * Specifies the item is a [UrlButton].
     */
    web_url,
    /**
     * Specifies the item is a [PostbackButton].
     */
    postback,
    /**
     * Specifies the item opens a nested menu.
     */
    nested
}
