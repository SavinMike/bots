package me.smu.bot.facebook.model.data

/**
 * @param audienceType: Audience type.
 * @param countries: Needs to be specified only when audience_type is custom.
 */
data class TargetAudience(val audienceType: AudienceType,
                          val countries: CountryAudience)

/**
 * @param blacklist:  List of ISO 3166 Alpha-2 codes. Users in any of the blacklist countries won't see your bot on
 * discovery surfaces on Messenger Platform.
 *
 * @param whitelist: List of ISO 3166 Alpha-2 codes. Users in any of the whitelist countries will see your bot on
 * discovery surfaces on Messenger Platform.
 */
data class CountryAudience(val blacklist: List<String>? = null,
                           val whitelist: List<String>? = null)

enum class AudienceType {
    /**
     * Open to all users
     */
    all,

    /**
     * Closed to all users
     */
    custom,

    /**
     * Open or closed to custom set of users.
     */
    none
}