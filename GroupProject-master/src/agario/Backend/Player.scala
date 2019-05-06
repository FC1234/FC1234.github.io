package agario.Backend
import gui.GUI.{food, player, player2}
import javafx.scene.input.KeyCode
import scalafx.animation.AnimationTimer
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle
import scala.util.Random

class Player extends Circle {
  val game = new Game
  centerX = Math.random() * 1000
  centerY = Math.random() * 1000
  radius = 5
  fill = randomColor()
  val speed = 3
  def randomColor(): Color = { //Returns the random color for the player circle.
    val arrayColors: Array[Int] = Array(0, 1, 2, 3, 4, 5, 6, 7)
    val randomNum = Random.shuffle(arrayColors.toList).head
    var color: Color = Color.Red

    if (randomNum == 0) {
      color = Color.Red
    }
    else if (randomNum == 1) {
      color = Color.Blue
    }
    else if (randomNum == 2) {
      color = Color.Brown
    }
    else if (randomNum == 3) {
      color = Color.FireBrick
    }
    else if (randomNum == 4) {
      color = Color.DeepSkyBlue
    }
    else if (randomNum == 5) {
      color = Color.Gold
    }
    else if (randomNum == 6) {
      color = Color.DarkGreen
    }
    else if (randomNum == 7) {
      color = Color.LawnGreen
    }
    color
  }
  def movePlayer(key: KeyCode): Unit = { //This moves the player
    key.getName match {
      case "W" => player.centerY.value -= speed
      case "S" => player.centerY.value += speed
      case "A" => player.centerX.value -= speed
      case "D" => player.centerX.value += speed
      case _ =>
    }
  }
  def eat(): Unit = {
    var lastUpdateTime = System.nanoTime()
    val timer = AnimationTimer(t => {
      if (lastUpdateTime > 0) {
        val delta = (t - lastUpdateTime) / 100
        for (f <- food) {
          f.fill = Color.Green
          val dx = player.centerX.value - f.centerX.value
          val dy = player.centerY.value - f.centerY.value
          val dist = Math.sqrt((dx * dx) + (dy * dy))
          if (dist < player.radius.value) {
            player.radius.value += f.radius.value
            f.radius.value = 0
          }
        }
        val pdx = player.centerX.value - player2.centerX.value
        val pdy = player.centerY.value - player2.centerY.value
        val pdist = Math.sqrt((pdx * pdx) + (pdy * pdy))
        if (pdist < player.radius.value) {
          if (player.radius.value > player2.radius.value) {
            player.radius.value += player2.radius.value
            player2.radius.value = 0
          }
          else if (player.radius.value < player2.radius.value) {
            player2.radius.value += player.radius.value
            player.radius.value = 0
          }
        }
        else if (pdist < player2.radius.value) {
          if (player.radius.value > player2.radius.value) {
            player.radius.value += player2.radius.value
            player2.radius.value = 0
          }
          else if (player.radius.value < player2.radius.value) {
            player2.radius.value += player.radius.value
            player.radius.value = 0
          }
        }
      }
      lastUpdateTime = t.toInt
    })
    timer.start()
  }
}