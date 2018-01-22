package control

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.geometry.Insets
import javafx.scene.layout.*
import javafx.scene.paint.Color
import java.net.URL
import java.util.*

class Lights : Initializable {

    companion object {
        private val COLUMNS = 4
        private val ROWS = 2
    }

    @FXML
    private lateinit var grid: GridPane


    override fun initialize(location: URL?, resources: ResourceBundle?) {
        for (c in 0 until COLUMNS) {
            for (r in 0 until ROWS) {
                val pane = Pane()
                pane.prefHeight = 200.toDouble()
                pane.prefWidth = 200.toDouble()

                pane.background = Background(BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY))

                grid.add(pane, c, r, 1, 1)
            }
        }

        grid.styleClass.add("myGridStyle")
    }


}