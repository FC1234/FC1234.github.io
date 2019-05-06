package gui
import agario.Backend.{Food, Game, Player}
import io.socket.client.{IO, Socket}
import io.socket.emitter.Emitter
import javafx.scene.input.KeyEvent
import scalafx.application
import scalafx.application.JFXApp
import scalafx.scene.shape.Circle
import scalafx.scene.{Group, Scene}

import scala.collection.mutable.ListBuffer
class HandleMessagesFromPython() extends Emitter.Listener {
  override def call(objects: Object*): Unit = {
    val size = objects.apply(0).toString
    println("player size is " + size)
  }
}
object GUI extends JFXApp {
  val socket: Socket = IO.socket("http://localhost:6010/")
  socket.on("message", new HandleMessagesFromPython)
  socket.connect()
  socket.emit("register", "ScalaUser")
  socket.emit("players")
  socket.emit("food")

  val g = new Group
  val player = new Player
  val player2 = new Player
  val game = new Game
  player2.radius.value = 4
  val f = new Food
  val food = f.multipleFood()
  stage = new application.JFXApp.PrimaryStage {
    title = "CSE116.io"
    scene = new Scene(game.world.width(), game.world.height()) {
      var players = ListBuffer(player, player2)
      val list: ListBuffer[Circle] = ListBuffer()
      while (!food.isEmpty){
        val f: Food = food.remove(food.length - 1)
        g.children.add(f)
      }
      while(!players.isEmpty){
       val p: Player = players.remove(players.length - 1)
        g.children.add(p)
      }
      content = List(g)
      addEventHandler(KeyEvent.KEY_PRESSED, (event: KeyEvent) => player.movePlayer(event.getCode))
      player.eat()
    }
 }
}