package agario.Backend
class Score {
  def scoreboard(): String = {
    val p = new Player
    val score = p.radius.value
    score.toString
  }
}
