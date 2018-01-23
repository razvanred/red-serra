package control

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.layout.GridPane
import java.net.URL
import java.util.*

class Stats:Initializable {

    @FXML
    private lateinit var grid: GridPane

    lateinit var array: ArrayList<Temp>

    override fun initialize(location: URL?, resources: ResourceBundle?) {

        array = ArrayList()

        for (c in 0 until Lights.COLUMNS) {
            for (r in 0 until Lights.ROWS) {
                val fxLoader = FXMLLoader(javaClass.getResource("/view/temp.fxml"), resources)
                grid.add(fxLoader.load(), c, r, 1, 1)
                array.add(fxLoader.getController())
            }
        }
    }


}