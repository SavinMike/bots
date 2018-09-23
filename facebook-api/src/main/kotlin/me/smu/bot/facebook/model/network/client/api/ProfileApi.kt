package me.smu.bot.facebook.model.network.client.api

import me.smu.bot.facebook.model.network.client.FacebookHttpClient
import me.smu.bot.facebook.model.network.client.api.data.ProfileUpdateRequest
import me.smu.bot.facebook.model.network.client.api.data.ProfileUpdateResponse

private const val PROFILE_METHOD = "messenger_profile"

internal class ProfileApi(private val client: FacebookHttpClient) {
    suspend fun updateProfile(profileUpdateRequest: ProfileUpdateRequest): ProfileUpdateResponse {
        return client.post(PROFILE_METHOD, profileUpdateRequest)
    }
}