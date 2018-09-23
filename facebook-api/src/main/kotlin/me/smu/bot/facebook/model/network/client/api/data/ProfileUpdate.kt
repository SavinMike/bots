package me.smu.bot.facebook.model.network.client.api.data

import me.smu.bot.facebook.model.data.*
import me.smu.bot.facebook.model.network.client.api.data.base.FacebookResponse

data class GetStartedPayload(val payload: String)

class ProfileInfo(val accountLinkingUrl: String? = null,
                  val persistentMenu: List<PersistentMenu>? = null,
                  val getStarted: GetStartedPayload? = null,
                  val greeting: List<Greeting>? = null,
                  val whitelistedDomains: List<String>? = null,
                  val homeUrl: HomeUrl? = null,
                  val paymentSettings: PaymentSettings? = null,
                  val targetAudience: TargetAudience? = null)

class ProfileUpdateResponse(val data: List<ProfileInfo>) : FacebookResponse

