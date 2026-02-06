package com.quantum.network.handler

import com.quantum.player.Player
import com.quantum.utils.Utils
import org.cloudburstmc.protocol.bedrock.packet.LoginPacket
import org.cloudburstmc.protocol.bedrock.util.EncryptionUtils
import org.cloudburstmc.protocol.common.PacketSignal

class LoginPacketHandler : PacketHandler() {

    override fun handle(packet: LoginPacket): PacketSignal {
        val chain = EncryptionUtils.validatePayload(packet.authPayload)

        val claims = chain.identityClaims()
        val data = claims.extraData
        val xuid = data.xuid

		// TODO: XBOX authentication

        val player = Player(
            Utils.calculateUuidFromXuid(xuid),
            data.displayName,
            xuid,
            session)

        server.logger.info("{} connected.", player.name)
        session.player = player
        server.playerManager.addPlayer(player)

        session.onLoginSuccess()

        return PacketSignal.HANDLED
    }
}
