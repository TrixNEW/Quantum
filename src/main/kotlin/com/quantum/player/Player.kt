package com.quantum.player

import com.quantum.Server
import com.quantum.network.Session
import java.util.*

class Player(
    val uuid: UUID,
    val name: String,
    val xuid: String,
    val session: Session
) {

    fun getServer() : Server
    = session.server

    fun remove()
    = getServer().playerManager.removePlayer(this)
}