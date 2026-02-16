package com.quantum.player

import com.quantum.Server
import com.quantum.network.Session
import com.quantum.player.model.GameMode
import java.util.UUID

class Player(
	val uuid: UUID,
	val name: String,
	val xuid: String,
	val session: Session,
	gameMode: GameMode
) {

	var gameMode: GameMode = gameMode
		private set

	fun getServer(): Server = session.server

	fun setGameMode(mode: GameMode, sendUpdate: Boolean = true) {
		if (mode == gameMode) return
		gameMode = mode

		// TODO: apply internal flags

		if (sendUpdate) {
			session.sendGameMode(mode)
		}
	}

	fun remove() = getServer().playerManager.removePlayer(this)
}
