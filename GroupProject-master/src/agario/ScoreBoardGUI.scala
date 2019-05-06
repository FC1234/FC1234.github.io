
/*import scalafx.scene.layout.{GridPane, HBox}
import scalafx.scene.Scene
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.effect.DropShadow
import scalafx.scene.paint.Color._
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.Text



object ScoreBoardGUI extends JFXApp {
//  val frame = new MainFrame {
//    title = "Score Board"
//    menuBar = new MenuBar {
//      contents += new Menu(""){
//        contents += new MenuItem(Action("Exit"){
//          sys.exit(0)
//        })
//      }
//    }
//    contents = new FlowPanel {
//      contents += new Label("Score: 0")
//    }
//  }
  val score = new Score
  stage = new PrimaryStage {
    title = "Score Boards"
    scene = new Scene() {
      fill = LightBlue
      content = new HBox {
        padding = Insets(50)
        children = Seq(
          new Text {
            text = "Score:"
            style = "-fx-font-size: 48pt"
            fill =  Black
          }
        )
      }
    }
  }
}
*/