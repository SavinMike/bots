@file:Suppress("EnumEntryName")

package me.smu.bot.facebook.model.data

import me.smu.bot.facebook.model.network.client.api.data.SendingMessage

enum class ButtonType {
    web_url, payment, phone_number, game_play, account_link, account_unlink, postback, element_share
}

/**
 * @property title: Button title, 20 character limit.
 */
interface Button {
    val type: ButtonType
    val title: String?
}

enum class WebviewHeightRatio {
    compact, tall, full
}

/**
 * The [UrlButton] opens a webpage in the Messenger webview. This button can be used with the Button and Generic Templates.
 *
 * To display a webpage with the Messenger Extensions SDK enabled in the Messenger webview you must whitelist the domain,
 * including sub-domain, in the whitelisted_domains property of your bot's Messenger Profile.
 * This ensures that only trusted domains have access to user information available via SDK functions.
 *
 * @property url: This URL is opened in a mobile browser when the button is tapped. Must use HTTPS protocol if [messengerExtensions] is true.
 * @property webviewHeightRatio: Optional. Height of the Webview.
 * @property messengerExtensions: Optional. Must be true if using Messenger Extensions.
 * @property fallbackUrl: The URL to use on clients that don't support Messenger Extensions. If this is not defined,
 *  the url will be used as the fallback. It may only be specified if [messengerExtensions] is true.
 * @see Button
 */
data class UrlButton(
        val url: String,
        override val title: String? = null,
        val webviewHeightRatio: WebviewHeightRatio = WebviewHeightRatio.full,
        val messengerExtensions: Boolean = false,
        val fallbackUrl: String? = null
) : Button {
    override val type: ButtonType = ButtonType.web_url
}

/**
 * The [PaymentButton] enables you to build a checkout experience in Messenger. This button opens a native checkout dialog
 * that enables people to make payments using their information stored in Messenger.
 *
 * @property title: Title of Buy Button. Must be "buy".
 * @property payload: Developer defined metadata about the purchase.
 * @property paymentSummary: Fields used in the checkout dialog.
 *
 * @see Button
 */
data class PaymentButton(
        override val title: String = "buy",
        val payload: String? = null,
        val paymentSummary: PaymentSummary
) : Button {
    override val type: ButtonType = ButtonType.payment
}

/**
 * The [CallButton] can be used to initiate a phone call. This button can be used with the Button and Generic Templates.
 *
 * @property payload: Format must have "+" prefix followed by the country code, area code and local number. For example, +16505551234.
 * @see Button
 */
data class CallButton(
        override val title: String,
        val payload: String
) : Button {
    override val type: ButtonType = ButtonType.phone_number
}

/**
 * The [GamePlayButton] launches an Instant Game that is associated with the bot page.
 *
 * Refer to [me.smu.bot.facebook.model.network.webhook.event.MessagingGamePlaysEvent] for the event that will be sent to the bot
 * when a user finishes a game round.
 *
 * @property payload: This data will be sent to the game.
 * @property gameMetadata: Parameters specific to Instant Games.
 * @see Button
 */
data class GamePlayButton(
        override val title: String,
        val payload: String? = null,
        val gameMetadata: String? = null
) : Button {
    override val type: ButtonType = ButtonType.game_play
}

/**
 * You can trigger the game to be started against a specific [playerId] or in a specific [contextId].
 *
 * @property playerId: Player ID (Instant Game name-space) to play against.
 * @property contextId: Context ID (Instant Game name-space) of the THREAD to play in
 */
data class GameMetadata(val playerId: String? = null,
                        val contextId: String? = null)

/**
 * The [LoginButton] triggers the account linking authentication flow.
 *
 * @property url: Authentication callback URL. Must use HTTPS protocol.
 */
data class LoginButton(val url: String) : Button {
    override val title: String? = null
    override val type: ButtonType = ButtonType.account_link
}

/**
 * The [LogoutButton] triggers the account unlinking flow.
 */
object LogoutButton : Button {
    override val title: String? = null
    override val type: ButtonType = ButtonType.account_unlink
}

/**
 * When the [PostbackButton] is tapped, the Messenger Platform sends an event to your [me.smu.bot.facebook.model.network.webhook.event.MessagingPostbacksEvent].
 * This is useful when you want to invoke an action in your bot. This button can be used with the Button Template and Generic Template.
 *
 * @property payload: This data will be sent back to your webhook. 1000 character limit.
 * @see Button
 */
data class PostbackButton(
        override val title: String?,
        val payload: String
) : Button {
    override val type: ButtonType = ButtonType.postback
}

/**
 * The [ShareButton] enables people to share your content in Messenger. Messages shared this way show an attribution
 * to your bot that recipients can tap to learn more about your bot.
 *
 * [shareContents] only supports the following:
 * + Template used must be generic template.
 * + Maximum of one URL button on the template. If no buttons are specified, the buttons property on the generic template
 * must be set to an empty array.
 *
 * @property shareContents: The message that you wish the recipient of the share to see, if it is different from the one
 * this button is attached to. The format follows that used in Send API.
 */
data class ShareButton(val shareContents: SendingMessage? = null) : Button {
    override val type: ButtonType = ButtonType.element_share
    override val title: String? = null
}