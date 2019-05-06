package agario.Backend

import play.api.libs.json.{JsValue, Json}

class Game {

  var players: Map[String, Player] = Map()
  var food: Map[String, Food] = Map()
  var world = new WorldMap(300,300,300,300)

  def addPlayer(id: String): Unit = {
    val player = new Player
    players += (id -> player)
  }
  def removePlayer(id: String): Unit = {
    players(id).radius.value = 0
    players -= id
  }
  def update(): Unit = {
    for((k,v) <- players){
      v.eat()
    }
  }
  def gameState(): String ={
    val gameState: Map[String, JsValue] = Map(
      "mapSize" -> Json.toJson(Map("x" -> world.width, "y" -> world.height)),
      "players" -> Json.toJson(this.players.map({ case (k, v) => Json.toJson(Map(
        "x" -> Json.toJson(v.centerX.value),
        "y" -> Json.toJson(v.centerY.value),
        "size" -> Json.toJson(v.radius.value),
        "id" -> Json.toJson(k)))})))
      "food" -> Json.toJson(this.food.map({case(k,v) => Json.toJson(Map(
        "x" -> Json.toJson(v.centerX.value),
        "y" -> Json.toJson(v.centerY.value),
        "size" -> Json.toJson(v.radius.value)))}))
    Json.stringify(Json.toJson(gameState))
  }
}