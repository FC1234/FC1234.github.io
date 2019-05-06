
package server

import agario.Backend.Game
import akka.actor.Actor
import javafx.scene.input.KeyCode

case object Update
case class AddPlayer(username: String)
case class RemovePlayer(username: String)
case class MovePlayerUp(username: String)
case class MovePlayerDown(username: String)
case class MovePlayerLeft(username: String)
case class MovePlayerRight(username: String)
case class GameState(gameState: String)
case object GS

class GameActor extends Actor {
  val game = new Game
  override def receive: Receive = {
    case GS => sender() ! GameState(game.gameState())
    case Update => game.update()
    case message: RemovePlayer => game.removePlayer(message.username)
    case message: AddPlayer => game.addPlayer(message.username)
    case message: MovePlayerUp => game.players(message.username).movePlayer(KeyCode.W)
    case message: MovePlayerDown => game.players(message.username).movePlayer(KeyCode.S)
    case message: MovePlayerLeft => game.players(message.username).movePlayer(KeyCode.A)
    case message: MovePlayerRight => game.players(message.username).movePlayer(KeyCode.D)
  }
}