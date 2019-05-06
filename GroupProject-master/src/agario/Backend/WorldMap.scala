package agario.Backend

class WorldMap(var bottomY: Int, var topY: Int,rightX: Int, leftX: Int) {
  def width(): Int ={
    (rightX - leftX) / 2
  }
  def height(): Int = {
    (bottomY - topY) / 2
  }
}