package com.quantum.network.version

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec
import org.cloudburstmc.protocol.bedrock.codec.v685.Bedrock_v685
import org.cloudburstmc.protocol.bedrock.codec.v686.Bedrock_v686
import org.cloudburstmc.protocol.bedrock.codec.v712.Bedrock_v712
import org.cloudburstmc.protocol.bedrock.codec.v729.Bedrock_v729
import org.cloudburstmc.protocol.bedrock.codec.v748.Bedrock_v748
import org.cloudburstmc.protocol.bedrock.codec.v766.Bedrock_v766
import org.cloudburstmc.protocol.bedrock.codec.v776.Bedrock_v776
import org.cloudburstmc.protocol.bedrock.codec.v786.Bedrock_v786
import org.cloudburstmc.protocol.bedrock.codec.v800.Bedrock_v800
import org.cloudburstmc.protocol.bedrock.codec.v818.Bedrock_v818
import org.cloudburstmc.protocol.bedrock.codec.v819.Bedrock_v819
import org.cloudburstmc.protocol.bedrock.codec.v827.Bedrock_v827
import org.cloudburstmc.protocol.bedrock.codec.v844.Bedrock_v844
import org.cloudburstmc.protocol.bedrock.codec.v859.Bedrock_v859
import org.cloudburstmc.protocol.bedrock.codec.v860.Bedrock_v860
import org.cloudburstmc.protocol.bedrock.codec.v898.Bedrock_v898
import org.cloudburstmc.protocol.bedrock.codec.v924.Bedrock_v924

/**
 * Represents supported Minecraft Bedrock Edition versions
 * and their corresponding protocol numbers.
 *
 * @property name Version network name
 * @property protocol Protocol version
 * @property codec Bedrock codec
 *
 * @author xRookieFight
 * @since 03/02/2026
 */
data class BedrockVersion(
    val name: String,
    val protocol: Int,
	val codec: BedrockCodec
) {

    companion object {
        val V1_21_0   = BedrockVersion("1.21.0", 685, Bedrock_v685.CODEC)
        val V1_21_2   = BedrockVersion("1.21.2", 686, Bedrock_v686.CODEC)
        val V1_21_20  = BedrockVersion("1.21.20", 712, Bedrock_v712.CODEC)
        val V1_21_30  = BedrockVersion("1.21.30", 729, Bedrock_v729.CODEC)
        val V1_21_40  = BedrockVersion("1.21.40", 748, Bedrock_v748.CODEC)
        val V1_21_50  = BedrockVersion("1.21.50", 766, Bedrock_v766.CODEC)
        val V1_21_60  = BedrockVersion("1.21.60", 776, Bedrock_v776.CODEC)
        val V1_21_70  = BedrockVersion("1.21.70", 786, Bedrock_v786.CODEC)
        val V1_21_80  = BedrockVersion("1.21.80", 800, Bedrock_v800.CODEC)
        val V1_21_90  = BedrockVersion("1.21.90", 818, Bedrock_v818.CODEC)
        val V1_21_93  = BedrockVersion("1.21.93", 819, Bedrock_v819.CODEC)
        val V1_21_100 = BedrockVersion("1.21.100", 827, Bedrock_v827.CODEC)
        val V1_21_110 = BedrockVersion("1.21.110", 843, Bedrock_v844.CODEC)
        val V1_21_111 = BedrockVersion("1.21.111", 844, Bedrock_v844.CODEC)
        val V1_21_120 = BedrockVersion("1.21.120", 859, Bedrock_v859.CODEC)
        val V1_21_124 = BedrockVersion("1.21.124", 860, Bedrock_v860.CODEC)
        val V1_21_130 = BedrockVersion("1.21.130", 898, Bedrock_v898.CODEC)
        val V1_26_0 = BedrockVersion("1.26.0", 924, Bedrock_v924.CODEC)

        val ALL = listOf(
            V1_21_0, V1_21_2, V1_21_20, V1_21_30, V1_21_40,
            V1_21_50, V1_21_60, V1_21_70, V1_21_80, V1_21_90,
            V1_21_93, V1_21_100, V1_21_110, V1_21_111,
            V1_21_120, V1_21_124, V1_21_130, V1_26_0
        )

        val LATEST = V1_26_0 // Latest supported version
        val MINIMUM = V1_21_0 // Minimum supported version

        fun fromProtocol(protocol: Int): BedrockVersion?
        = ALL.firstOrNull { it.protocol == protocol }

        fun fromName(name: String): BedrockVersion?
        = ALL.firstOrNull { it.name == name }
    }

    fun isBefore(version: BedrockVersion): Boolean
    = protocol < version.protocol

    fun isBeforeOrEqual(version: BedrockVersion): Boolean
    = protocol <= version.protocol

    fun isAfter(version: BedrockVersion): Boolean
    = protocol > version.protocol

    fun isAfterOrEqual(version: BedrockVersion): Boolean
    = protocol >= version.protocol

	fun isEqual(version: BedrockVersion): Boolean
	= protocol == version.protocol
}