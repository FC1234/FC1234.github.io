/*
import agario.Backend.circles.{Food, Player}
import org.scalatest._

class TestEatFood extends FunSuite {

  test(testName = "eatFood Test"){
    Player.size = 10
    Food.size = 5
    Player.playerLocationX = 5
    Player.playerLocationY = 6      // Location is exactly the same, therefore adds one to the player's size
    Food.foodLocationX = 5
    Food.foodLocationY = 6
    assert(Player.eatFood() == 15)
    Player.size = 10
    Food.size = 5   // I reset the players size for the next assertion, it would save 11 if I didn't
    Player.playerLocationX = 5
    Player.playerLocationY = 3
    Food.foodLocationX = 2
    Food.foodLocationY =1
    assert(Player.eatFood() == 10)
    Food.size = -1
    Player.playerLocationX = 5
    Player.playerLocationY = 3
    Food.foodLocationX = 5
    Food.foodLocationY =3
    assert(Player.eatFood() == 10)
    Food.size = 0
    Player.playerLocationX =20
    Player.playerLocationY = 65
    Food.foodLocationX = 20
    Food.foodLocationY =65
    assert(Player.eatFood() == 10)
  }

}
*/