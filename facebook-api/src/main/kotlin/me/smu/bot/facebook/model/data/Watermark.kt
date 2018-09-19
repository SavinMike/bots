package me.smu.bot.facebook.model.data

/**
 * @property mids: Array containing message IDs of messages that were delivered. Field may not be present.
 * @property watermark: All messages that were sent before this timestamp were delivered
 * @property seq: Sequence number
 */
data class Watermark(val mids: List<String>?,
                     val watermark: Long,
                     val seq: Long)