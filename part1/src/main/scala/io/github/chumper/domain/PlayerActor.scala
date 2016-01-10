package io.github.chumper.domain

import akka.cluster.sharding.ShardRegion
import akka.event.Logging
import akka.persistence.PersistentActor
import io.github.chumper.domain.PlayerActor.{PlayerInformation, GetPlayerInformation, InitializePlayer, PlayerInitialized}

object PlayerActor {
  // define compatible commands
  case class InitializePlayer(playerId: PlayerId, xp: Int, credits: Int)
  case class GetPlayerInformation(playerId: PlayerId)

  // define compatible events
  case class PlayerInitialized(playerId: PlayerId, xp: Int, credits: Int)

  // custom responses
  case class PlayerInformation(playerId: PlayerId, xp: Int, credits: Int)

  def extractEntityId(): ShardRegion.ExtractEntityId = {
    case msg@InitializePlayer(id, _, _) => (id.value.toString, msg)
    case msg@GetPlayerInformation(id) => (id.value.toString, msg)
  }

  def extractShardId(numberOfShards: Int): ShardRegion.ExtractShardId = {
    case InitializePlayer(id, _, _) => Math.abs(id.hashCode() % numberOfShards).toString
    case GetPlayerInformation(id) =>  Math.abs(id.hashCode() % numberOfShards).toString
  }
}

/**
  * Player Aggregate Root
  */
class PlayerActor extends PersistentActor {

  var id: PlayerId = PlayerId(self.path.name)
  var xp: Int = _
  var credits: Int = _

  // self.path.name is the entity identifier (utf-8 URL-encoded)
  override def persistenceId: String = "Player-" + self.path.name

  val log = Logging(context.system, this)

  override def receiveCommand = {
    case init: InitializePlayer =>
      persist(PlayerInitialized(init.playerId, init.xp, init.credits)) { ev =>
        initialize(ev)
        sender() ! ev
      }
    case GetPlayerInformation => sender() ! PlayerInformation(id, xp, credits)
    case _ => log.info("received unknown message")
  }

  override def receiveRecover = {
    case init: PlayerInitialized => initialize(init)
    case _ => log.info("received unknown message")
  }

  def initialize(init: PlayerInitialized) = {
    this.xp = init.xp
    this.credits = init.credits
    this.id = init.playerId
  }
}
