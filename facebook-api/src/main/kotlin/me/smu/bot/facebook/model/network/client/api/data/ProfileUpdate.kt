package me.smu.bot.facebook.model.network.client.api.data

import me.smu.bot.facebook.model.data.*
import me.smu.bot.facebook.model.network.client.api.data.base.FacebookResponse

open class ProfileInfo(val accountLinkingUrl: String? = null,
                       val persistentMenu: PersistentMenu? = null,
                       val getStarted: GetStartedPayload? = null,
                       val greeting: List<Greeting>? = null,
                       val whitelistedDomains: List<String>? = null,
                       val homeUrl: HomeUrl? = null,
                       val paymentSettings: PaymentSettings? = null,
                       val targetAudience: TargetAudience? = null)

data class GetStartedPayload(val payload: String)

class ProfileUpdateRequest(val accountLinkingUrl: String? = null,
                           val persistentMenu: PersistentMenu? = null,
                           val getStarted: GetStartedPayload? = null,
                           val greeting: List<Greeting>? = null,
                           val whitelistedDomains: List<String>? = null,
                           val homeUrl: HomeUrl? = null,
                           val paymentSettings: PaymentSettings? = null,
                           val targetAudience: TargetAudience? = null)

class ProfileUpdateResponse(val data: List<ProfileInfo>) : FacebookResponse

