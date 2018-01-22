package control

import com.jfoenix.controls.JFXTabPane
import com.jfoenix.controls.JFXTextField
import com.jfoenix.controls.JFXToggleButton
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Alert
import javafx.scene.control.Tab
import java.net.BindException
import java.net.URL
import java.util.*

class Main:Initializable {

    @FXML
    private lateinit var tabPane:JFXTabPane

    @FXML
    private lateinit var tabStats:Tab

    @FXML
    private lateinit var tabLights:Tab

    private lateinit var thread:Thread

    private lateinit var server:ServerTask

    @FXML
    private lateinit var txfPort: JFXTextField

    @FXML
    private lateinit var switchServer:JFXToggleButton

    private lateinit var rb:ResourceBundle

    @FXML
    private fun startStopUDP(){
        if(switchServer.isSelected){

            try {
                server = ServerTask(txfPort.text.toInt())
            } catch (bind: BindException) {
                val alert = Alert(Alert.AlertType.ERROR)
                alert.contentText = rb.getString("port_already_used")
                alert.headerText = rb.getString("error")
                alert.title = rb.getString("app_name")

                alert.showAndWait()

                switchServer.isSelected = false

                return
            }
            server.start()

            txfPort.isDisable=true

        }else{

            server.explode()
            txfPort.isDisable = false
        }
    }


    override fun initialize(location: URL?, resources: ResourceBundle?) {

        rb=resources!!

        txfPort.textProperty().addListener { _, _, newValue ->
            if (!newValue.matches("\\d*".toRegex()))
                txfPort.text = newValue.replace("[^\\d]".toRegex(), "")
            switchServer.isDisable = newValue.isEmpty() || Integer.parseInt(newValue) < 1024 || Integer.parseInt(newValue) > 49151
        }

    }

}