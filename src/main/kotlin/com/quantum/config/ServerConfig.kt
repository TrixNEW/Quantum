package com.quantum.config

import eu.okaeri.configs.OkaeriConfig
import eu.okaeri.configs.annotation.Comment

/**
 * Main configuration system for server
 * @author xRookieFight
 * @since 12/02/2026
 */
class ServerConfig : OkaeriConfig() {

	var serverSettings = ServerSettings()
	var networkSettings = NetworkSettings()
	var gameSettings = GameSettings()
	var whitelistSettings = WhitelistSettings()

	class ServerSettings : OkaeriConfig() {

		@Comment("Main server name displayed in the server list")
		var motd = "Quantum Server"

		@Comment("Secondary MOTD line displayed in the server list")
		var subMotd = "quantum.org"

		@Comment("Maximum number of players allowed on the server")
		var maxPlayers = 20

		@Comment("Allow players from multiple protocol versions to join")
		var multiVersion = true

		@Comment("Default server language (eng, tr, etc.)")
		var language = "eng"

		@Comment("Force translation for all players regardless of client language")
		var forceTranslate = false

		@Comment("Enable automatic world saving")
		var autoSave = true

		@Comment("Auto-save interval in ticks (20 ticks = 1 second)")
		var autoSaveDelay = 6000
	}

	class NetworkSettings : OkaeriConfig() {

		@Comment("IP address to bind the server to (0.0.0.0 = all interfaces)")
		var ip = "0.0.0.0"

		@Comment("UDP port used by the Bedrock server")
		var port = 19132

		@Comment("Compression level (0-9). Higher = better compression but more CPU usage")
		var compressionLevel = 4

		@Comment("Enable packet encryption (recommended for security)")
		var encryption = true

		@Comment("Maximum MTU size allowed for RakNet connections")
		var maxMtu = 1492

		@Comment("Enable query protocol for server list pinging")
		var enableQuery = true
	}

	class GameSettings : OkaeriConfig() {
		@Comment("Default world name")
		var levelName = "world"

		@Comment("World seed (-1 = random seed)")
		var seed = -1

		@Comment("Server view distance in chunks")
		var viewDistance = 8

		@Comment("Default gamemode (0=Survival, 1=Creative, 2=Adventure, 3=Spectator)")
		var gamemode = 0

		@Comment("World difficulty (0=Peaceful, 1=Easy, 2=Normal, 3=Hard)")
		var difficulty = 1

		@Comment("Enable player combat")
		var pvp = true

		@Comment("Enable vibrant visuals support (1.21.x)")
		var vibrantVisuals = true

		@Comment("Require Xbox Live authentication to join")
		var xboxAuth = true
	}

	class WhitelistSettings : OkaeriConfig() {

		@Comment("Enable or disable the whitelist system")
		var enabled = false

		@Comment("Message shown when a non-whitelisted player tries to join")
		var whitelistMessage = "This server is whitelisted"

		@Comment("List of allowed player usernames")
		var players = listOf<String>()
	}
}