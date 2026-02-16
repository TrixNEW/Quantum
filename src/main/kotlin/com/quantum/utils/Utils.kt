package com.quantum.utils

import java.nio.ByteBuffer
import java.security.MessageDigest
import java.util.*

object Utils {

    val EMPTY_UUID = UUID(0L, 0L)

    fun calculateUuidFromXuid(xuid: String): UUID {
        val md5 = MessageDigest.getInstance("MD5")
        val hash = md5.digest("pocket-auth-1-xuid:$xuid".toByteArray(Charsets.UTF_8))

        hash[6] = ((hash[6].toInt() and 0x0F) or 0x30).toByte() // Version 3
        hash[8] = ((hash[8].toInt() and 0x3F) or 0x80).toByte() // Variant

        val bb = ByteBuffer.wrap(hash)
        val mostSigBits = bb.long
        val leastSigBits = bb.long
        return UUID(mostSigBits, leastSigBits)
    }
}