package com.quantum.network

import com.quantum.Server
import com.quantum.network.handler.ResourcePackPacketHandler
import com.quantum.player.Player
import com.quantum.utils.Utils
import org.cloudburstmc.protocol.bedrock.BedrockPeer
import org.cloudburstmc.protocol.bedrock.BedrockServerSession
import org.cloudburstmc.protocol.bedrock.netty.BedrockPacketWrapper
import org.cloudburstmc.protocol.bedrock.packet.PlayStatusPacket
import org.cloudburstmc.protocol.bedrock.packet.ResourcePacksInfoPacket
import java.util.*

/**
 * The client network session
 * @author xRookieFight
 * @since 03/02/2026
 */
class Session(
    peer: BedrockPeer,
    subClientId: Int,
    val server: Server
) : BedrockServerSession(peer, subClientId) {

    var player: Player? = null

    override fun onPacket(wrapper: BedrockPacketWrapper) {
        Objects.requireNonNull(packetHandler, "Received a packet without handler")
        packetHandler.handlePacket(wrapper.packet)
    }

    fun onLoginSuccess() {
        val playStatusPacket = PlayStatusPacket().apply {
            status = PlayStatusPacket.Status.LOGIN_SUCCESS
        }

        sendPacket(playStatusPacket)

        val resourcePacksInfoPacket = ResourcePacksInfoPacket().apply {
            isForcedToAccept = false
            isHasAddonPacks = false
            isScriptingEnabled = false
            worldTemplateId = Utils.EMPTY_UUID
            worldTemplateVersion = ""
            isVibrantVisualsForceDisabled = false
        } // TODO: Resource pack system

        sendPacketImmediately(resourcePacksInfoPacket)

        packetHandler = ResourcePackPacketHandler().attach(this, server)
    }
}