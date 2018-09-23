package me.smu.bot.facebook.model.network.client.controller

import me.smu.bot.facebook.model.data.*
import me.smu.bot.facebook.model.network.client.api.ProfileApi
import me.smu.bot.facebook.model.network.client.api.data.GetStartedPayload
import me.smu.bot.facebook.model.network.client.api.data.ProfileInfo

/**
 * The Messenger Profile API allows you to set, update, retrieve, and delete properties from your bot's Messenger Profile.
 *
 * Calls to the Messenger Profile API are limited to 10 API calls per 10 minute interval. This rate limit is enforced per Page.
 */
interface ProfileController {

    /**
     * Messenger's Account Linking allows a secure and consistent way to link user accounts in your bot to the user's
     * Messenger account. When a user has linked their account, Log In and Log Out buttons will be shown in the profile screen.
     * Using this feature requires a valid [accountLinkingUrl] in your bot's Messenger Profile.
     *
     * @param accountLinkingUrl: URL opened by the Messenger Platform when a user triggers account linking.
     */
    suspend fun setAccountLinkingUrl(accountLinkingUrl: String)

    /**
     * A bot's welcome screen can display a Get Started button. When this button is tapped, the Messenger Platform will
     * send a `messaging_postbacks` event to your webhook.
     *
     * To set the get started button, you must have the Administrator role for the Facebook Page associated with the bot.
     *
     * @param payload: Payload sent back to your webhook in a messaging_postbacks event when the 'Get Started'
     * button is tapped. 1000 character limit.
     */
    suspend fun setGetStartedButton(payload: String)

    /**
     * The greeting property of your bot's Messenger profile allows you to specify the greeting message people will see
     * on the welcome screen of your bot. The welcome screen is displayed for people interacting with your bot for the
     * first time.
     * If greeting text is not set for the user's locale, the standard greeting text set with the thread settings API will
     * be shown in the welcome screen. If the standard greeting text is not set either, the page description will be shown.
     *
     * To set or update the greeting text you must have the 'Administrator' role for the Page associated with the bot.
     *
     * @see Greeting
     */
    suspend fun setGreetingScreen(greeting: List<Greeting>)

    /**
     * This [homeUrl] property of your bot's Messenger profile allows your bot to enable a Chat Extension in the composer
     * drawer in Messenger. It controls what is displayed when the Chat Extension is invoked via the composer drawer in
     * Messenger.
     *
     * The domain of the home URL for your Chat Extension must be added to the domain whitelist in your bot's Messenger profile.
     * To set or update the home URL you must have the 'Administrator' role for the Page associated with the bot.
     *
     * @see HomeUrl
     */
    suspend fun setHomeUrl(homeUrl: HomeUrl)

    /**
     * The [persistentMenu] can be set for your bot to help people discover and more easily access your functionality
     * throughout the conversation.
     * The [persistentMenu] is always available to the user. This menu should contain top-level actions that users can
     * enact at any point. Having a [persistentMenu] easily communicates the basic capabilities of your bot for first-time
     * and returning users. The menu will automatically appear in a thread if the person has been away for a certain
     * period of time and returns.
     *
     * To use the persistent menu, you must do the following:
     * - [setGetStartedButton]
     * - Have the 'Administrator' role for the page associated with the bot.
     *
     * @see PersistentMenu
     */
    suspend fun setPersistentMenu(persistentMenu: List<PersistentMenu>)

    /**
     * The [paymentSettings] property of your bot's Messenger Profile provides the Messenger Platform with several
     * settings needed to implement various aspects of payments in your bot:
     * - Privacy policy URL: Required for bots that implement buy button payments.
     * - Public key Required for bots that implement webview payments or tokenized payment with the buy button.
     * - Test users: Required to start testing while waiting to be accepted in the Payments beta program.
     *
     * To set or update payment settings you must have the 'Administrator' role for the Page associated with the bot.
     * @see PaymentSettings
     */
    suspend fun setPaymentSettings(paymentSettings: PaymentSettings)

    /**
     * Target Audience allows you to customize the audience that will see your bot in the Discover tab on Messenger.
     * Other users can still find and use your bot through other channels (e.g. search, m.me URL). There are three types
     * of Target Audience settings:
     * - Open to all users,
     * - Closed to all users, and
     * - Open or closed to custom set of users.
     *
     * To set or update the target audience you must have the 'Administrator' role for the Page associated with the bot.
     * @see TargetAudience
     */
    suspend fun setTargetAudience(targetAudience: TargetAudience)

    /**
     * The [domains] property of your bot's Messenger profile specifies a list of third-party domains that are accessible
     * in the Messenger webview for use with the Messenger Extensions SDK, and for the checkbox plugin.
     *
     * Domains must meet the following requirements to be whitelisted:
     * - Served over HTTPS
     * - Use a fully qualified domain name, such as https://www.messenger.com/. IP addresses and localhost are not
     * supported for whitelisting.
     *
     * @param domains: A list of domains being used. All domains must be valid. Up to 50 domains allowed.
     */
    suspend fun setWhitelistedDomains(domains: List<String>)
}

internal class ApiProfileController(private val profileApi: ProfileApi) : ProfileController {
    override suspend fun setAccountLinkingUrl(accountLinkingUrl: String) {
        profileApi.updateProfile(ProfileInfo(accountLinkingUrl = accountLinkingUrl))
    }

    override suspend fun setGetStartedButton(payload: String) {
        profileApi.updateProfile(ProfileInfo(getStarted = GetStartedPayload(payload)))
    }

    override suspend fun setGreetingScreen(greeting: List<Greeting>) {
        profileApi.updateProfile(ProfileInfo(greeting = greeting))
    }

    override suspend fun setHomeUrl(homeUrl: HomeUrl) {
        profileApi.updateProfile(ProfileInfo(homeUrl = homeUrl))
    }

    override suspend fun setPersistentMenu(persistentMenu: List<PersistentMenu>) {
        profileApi.updateProfile(ProfileInfo(persistentMenu = persistentMenu))
    }

    override suspend fun setPaymentSettings(paymentSettings: PaymentSettings) {
        profileApi.updateProfile(ProfileInfo(paymentSettings = paymentSettings))
    }

    override suspend fun setTargetAudience(targetAudience: TargetAudience) {
        profileApi.updateProfile(ProfileInfo(targetAudience = targetAudience))
    }

    override suspend fun setWhitelistedDomains(domains: List<String>) {
        profileApi.updateProfile(ProfileInfo(whitelistedDomains = domains))
    }

}

