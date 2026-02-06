package com.quantum

import com.quantum.network.RakNetServer
import com.quantum.network.version.BedrockVersion
import com.quantum.player.PlayerManager
import org.apache.logging.log4j.Logger

/**
 * Base server class for Quantum
 * @author xRookieFight
 * @since 03/02/2026
 */
class Server {

    companion object {
        lateinit var instance: Server
            private set
    }

    @Volatile
    private var running = true

    var network: RakNetServer
        private set

    var playerManager: PlayerManager
        private set

    var logger: Logger
        private set

    constructor(mainLogger: Logger){
        instance = this
        logger = mainLogger
        logger.info("Starting ${Quantum.name} v${Quantum.version}")

        playerManager = PlayerManager()
        network = RakNetServer()
        logger.info("This server is latest supporting Minecraft: Bedrock Edition v${BedrockVersion.LATEST.name}")

        logger.info("Server started! Type /help to get information about commands.")

        loop()
    }

    fun shutdown() {
        network.shutdown()
        logger.info("Shutting down server...")

        running = false
        synchronized(this) {
            (this as Object).notifyAll()
        }
    }

    private fun loop() {
        synchronized(this) {
            while (running) {
                try {
                    (this as Object).wait()
                } catch (_: InterruptedException) {
                    Thread.currentThread().interrupt()
                    break
                }
            }
        }

        shutdown()
    }
}