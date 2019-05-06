package server

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.io.{IO, Tcp}
import akka.util.ByteString
import play.api.libs.json.{JsValue, Json}

class AgarioServer(gameActor: ActorRef) extends Actor {

  import Tcp._
  import context.system

  IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", 6000))

  var web: Set[ActorRef] = Set()
  var buffer: String = ""
  val delimiter: String = "~"

  override def receive: Receive = {
    case b: Bound => println("Listening on port: " + b.localAddress.getPort)

    case c: Connected =>
      println("Client Connected: " + c.remoteAddress)
      this.web = this.web + sender()
      sender() ! Register(self)

    case PeerClosed =>
      println("Client Disconnected: " + sender())
      this.web = this.web - sender()

    case r: Received =>
      buffer += r.data.utf8String
      while (buffer.contains(delimiter)) {
        val curr = buffer.substring(0, buffer.indexOf(delimiter))
        buffer = buffer.substring(buffer.indexOf(delimiter) + 1)
        handleMessage(curr)
      }
    case GS =>
      gameActor ! GS
    case gs: GameState =>
      this.web.foreach((client: ActorRef) => client ! Write(ByteString(gs.gameState + delimiter)))
  }
  def handleMessage(messageString:String):Unit = {
    val message: JsValue = Json.parse(messageString)
    val username = (message \ "username").as[String]
    val messageType = (message \ "action").as[String]
    messageType match {
      case "connected" => gameActor ! AddPlayer(username)
      case "disconnected" => gameActor ! RemovePlayer(username)
      case "move" =>
        gameActor ! MovePlayerUp(username)
        gameActor ! MovePlayerDown(username)
        gameActor ! MovePlayerLeft(username)
        gameActor ! MovePlayerRight(username)
    }
  }
}
object AgarioServer {

  def main(args: Array[String]): Unit = {
    val actorSystem = ActorSystem()

    import actorSystem.dispatcher

    import scala.concurrent.duration._

    val gameActor = actorSystem.actorOf(Props(classOf[GameActor]))
    val server = actorSystem.actorOf(Props(classOf[AgarioServer], gameActor))

    actorSystem.scheduler.schedule(16.milliseconds, 32.milliseconds, gameActor, Update)
    actorSystem.scheduler.schedule(32.milliseconds, 32.milliseconds, server, GS)
  }

}
