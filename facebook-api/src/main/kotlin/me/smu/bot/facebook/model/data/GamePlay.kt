package me.smu.bot.facebook.model.data

/**
 * @property gameId: App ID of the game
 * @property playerId: ID of the user in the Instant Game name-space. By linking this ID to the PSID received
 *                     in the sender field, the bot can send messages to a user after a game play.
 * @property contextType: Type of the social context a game is played in.
 * @property contextId: ID of the context if not a SOLO type. This ID is in the Instant Game name-space.
 * @property score: Best score achieved by this user in this game round. Only available to Classic score based games.
 * @property payload: JSON encoded payload data, set using FBInstant.setSessionData(). Only available to game with Rich Games Feature enabled.
 */
data class GamePlay(val gameId: String,
                    val playerId: String,
                    val contextType: String,
                    val contextId: String?,
                    val score: Int?,
                    val payload: String)