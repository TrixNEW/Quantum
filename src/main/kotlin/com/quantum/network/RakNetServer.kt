package com.quantum.network

import com.quantum.Server
import com.quantum.network.handler.PreLoginPacketHandler
import com.quantum.network.version.BedrockVersion
import com.quantum.player.model.GameMode
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.Channel
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioDatagramChannel
import org.cloudburstmc.netty.channel.raknet.RakChannelFactory
import org.cloudburstmc.netty.channel.raknet.config.RakChannelOption
import org.cloudburstmc.protocol.bedrock.BedrockPeer
import org.cloudburstmc.protocol.bedrock.BedrockPong
import org.cloudburstmc.protocol.bedrock.netty.initializer.BedrockChannelInitializer
import java.net.InetSocketAddress
import java.util.*

/**
 * Head RakNet network class
 * @author xRookieFight
 * @since 03/02/2026
 */
class RakNetServer {

	private val config = Server.instance.config

    private val advertisement = BedrockPong()
        .edition("MCPE")
		.gameType(GameMode.fromId(config.gameSettings.gamemode).displayName)
        .version(BedrockVersion.LATEST.name)
        .protocolVersion(BedrockVersion.LATEST.protocol)
        .motd(config.serverSettings.motd)
        .playerCount(Server.instance.playerManager.players.size)
        .maximumPlayerCount(config.serverSettings.maxPlayers)
        .serverId(UUID.randomUUID().mostSignificantBits)
        .subMotd(config.serverSettings.subMotd)
        .nintendoLimited(false)
        .ipv4Port(config.networkSettings.port)
        .ipv6Port(config.networkSettings.port)

    var server: Channel = ServerBootstrap()
        .group(NioEventLoopGroup())
        .channelFactory(RakChannelFactory.server(NioDatagramChannel::class.java))
        .option(RakChannelOption.RAK_ADVERTISEMENT, advertisement.toByteBuf())
        .childHandler(object : BedrockChannelInitializer<Session>() {
            override fun createSession0(peer: BedrockPeer, subClientId: Int): Session
            = Session(peer, subClientId, Server.instance)

            override fun initSession(session: Session) {
                session.packetHandler = PreLoginPacketHandler().attach(session, Server.instance)
            }
        })
        .bind(InetSocketAddress(config.networkSettings.ip, config.networkSettings.port))
        .awaitUninterruptibly()
        .channel()

    fun shutdown() {
        server.disconnect()
    }
}