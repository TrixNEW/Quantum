package com.quantum.network.handler

import com.quantum.player.Player
import com.quantum.player.model.GameMode
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
		val initialMode = GameMode.fromId(server.config.gameSettings.gamemode) //TODO: This should only be a temporarily initial variable, it should be overriden later by whatever is in the players data

		// TODO: XBOX authentication

        val player = Player(
            Utils.calculateUuidFromXuid(xuid),
            data.displayName,
            xuid,
            session,
			initialMode)

        server.logger.info("{} connected.", player.name)
        session.player = player
        server.playerManager.addPlayer(player)

        session.onLoginSuccess()

        return PacketSignal.HANDLED
    }
}
