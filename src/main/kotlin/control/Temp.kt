package control

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import java.net.URL
import java.util.*

class Temp : Initializable {

    @FXML
    lateinit var lblHumidity: Label

    @FXML
    lateinit var lblTemperature: Label

    @FXML
    lateinit var lblLight: Label

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        lblHumidity.text = "0"
        lblTemperature.text = "0"
        lblLight.text = "0"
    }

}