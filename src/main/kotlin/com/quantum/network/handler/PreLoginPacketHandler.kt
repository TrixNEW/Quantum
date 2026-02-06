package com.quantum.network.handler

import com.quantum.network.version.BedrockVersion
import org.cloudburstmc.protocol.bedrock.data.PacketCompressionAlgorithm
import org.cloudburstmc.protocol.bedrock.packet.NetworkSettingsPacket
import org.cloudburstmc.protocol.bedrock.packet.PlayStatusPacket
import org.cloudburstmc.protocol.bedrock.packet.RequestNetworkSettingsPacket
import org.cloudburstmc.protocol.common.PacketSignal

class PreLoginPacketHandler : PacketHandler() {

	override fun handle(packet: RequestNetworkSettingsPacket): PacketSignal {
		val clientVersion = BedrockVersion.fromProtocol(packet.protocolVersion)

		if (clientVersion == null || clientVersion.isBefore(BedrockVersion.MINIMUM)) {
			val playStatus = PlayStatusPacket().apply {
				status = PlayStatusPacket.Status.LOGIN_FAILED_CLIENT_OLD
			}
			session.sendPacketImmediately(playStatus)
			session.disconnect()
			return PacketSignal.HANDLED
		}

		if (clientVersion.isAfter(BedrockVersion.LATEST)) {
			val playStatus = PlayStatusPacket().apply {
				status = PlayStatusPacket.Status.LOGIN_FAILED_SERVER_OLD
			}
			session.sendPacketImmediately(playStatus)
			session.disconnect()
			return PacketSignal.HANDLED
		}

		val networkSettings = NetworkSettingsPacket().apply {
			compressionThreshold = 0
			compressionAlgorithm = PacketCompressionAlgorithm.ZLIB
		}

		session.codec = clientVersion.codec
		session.sendPacketImmediately(networkSettings)
		session.setCompression(PacketCompressionAlgorithm.ZLIB)

		session.packetHandler = LoginPacketHandler().attach(session, session.server)
		return PacketSignal.HANDLED
	}
}