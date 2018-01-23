import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import java.util.*


class Principale: Application() {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(Principale::class.java)
        }

    }

    override fun start(primaryStage: Stage) {

        val rb = ResourceBundle.getBundle("bundle.lang",Locale.ITALIAN)

        val fxLoader = FXMLLoader(javaClass.getResource("/view/main.fxml"), rb)

        primaryStage.scene= Scene(fxLoader.load())

        //primaryStage.scene.stylesheets.add("css/style.css")

        primaryStage.title=rb.getString("app_name")
        primaryStage.show()


    }

}