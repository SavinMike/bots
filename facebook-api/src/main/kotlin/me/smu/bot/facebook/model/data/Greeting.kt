package me.smu.bot.facebook.model.data

/**
 * @param locale: An array of objects that define the persistent menu for different locales. The menu with a locale
 * property that matches the person's locale will be displayed.
 * At least one object in the [PersistentMenu] array must specify "locale": "default". This is the menu we will fall back
 * to if no object has a locale property that matches the users locale. See the list of [supported locales](https://developers.facebook.com/docs/messenger-platform/messenger-profile/supported-locales).
 *
 * @param text: The greeting text for the specific locale. Must be in UTF-8. 160 character limit.
 * You can personalize the greeting text using the person's name. You can use the following template strings:
 * - {{user_first_name}}
 * - {{user_last_name}}
 * - {{user_full_name}}
 */
data class Greeting(val locale: String,
                    val text: String)