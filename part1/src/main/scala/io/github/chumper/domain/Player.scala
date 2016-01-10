package io.github.chumper.domain

import java.util.UUID

/**
  * Player Aggregate Root
  */
class Player {

  var id: PlayerId = PlayerId(UUID.randomUUID.toString)
  var xp: Int = 0
  var credits: Int = 0

}
