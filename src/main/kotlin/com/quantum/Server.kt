package com.quantum

import com.quantum.config.ServerConfig
import com.quantum.network.RakNetServer
import com.quantum.network.version.BedrockVersion
import com.quantum.player.PlayerManager
import eu.okaeri.configs.ConfigManager
import eu.okaeri.configs.yaml.snakeyaml.YamlSnakeYamlConfigurer
import org.apache.logging.log4j.Logger
import java.io.File

/**
 * Base server class for Quantum
 * @author xRookieFight
 * @since 03/02/2026
 */
class Server {

    companion object {
        lateinit var instance: Server
            private set

		val DATA_PATH = System.getProperty("user.dir") + "/"
    }

    @Volatile
    private var running = true

    val network: RakNetServer
    val playerManager: PlayerManager
    val logger: Logger
	val config: ServerConfig

    constructor(mainLogger: Logger){
        instance = this
        logger = mainLogger

        playerManager = PlayerManager()

		config = ConfigManager.create(ServerConfig::class.java) { it ->
			logger.info("Loading configuration")
			it.withConfigurer(YamlSnakeYamlConfigurer())
				.withBindFile(File(DATA_PATH + "quantum.yml"))
				.withRemoveOrphans(true)
				.saveDefaults()
				.load()
		}

		logger.info("Starting ${Quantum.NAME} v${Quantum.VERSION}")

        network = RakNetServer()
        logger.info("This server is running Minecraft: Bedrock Edition v${BedrockVersion.LATEST.name}")

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

	fun gameModeFrom(value: Int) : String {
		return when (value) {
			0 -> "Survival"
			1 -> "Creative"
			2 -> "Adventure"
			3 -> "Spectator"
			else -> "Unknown"
		}
	}
}