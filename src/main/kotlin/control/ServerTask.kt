package control

import javafx.application.Platform
import javafx.geometry.Insets
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.SocketException
import kotlin.experimental.and
import kotlin.math.roundToInt


class ServerTask(PORT: Int, val lights: Lights, val stats: Stats) : Thread() {

    private val socket = DatagramSocket(PORT)
    private lateinit var packet: DatagramPacket


    @Volatile
    private var boom = false

    override fun run() {
        while (!boom) {
            try {
                val arrayByte = ByteArray(4)
                packet = DatagramPacket(arrayByte, arrayByte.size)
                socket.receive(packet)

                var i = 1
                while (i < 255) {

                    val color = if (arrayByte[1].toInt() < 0) arrayByte[1].toInt() + 256 else arrayByte[1].toInt()


                    if ((arrayByte[0].toShort() and i.toShort()) == i.toShort()) {
                        for (o in 0 until lights.arrayPane.size) {
                            if ((Math.log(i.toDouble()) / Math.log(2.toDouble())).roundToInt() == o) {
                                writeOnFile(packet.address.toString(), o, color, arrayByte[2].toInt(), arrayByte[3].toInt())
                                Platform.runLater {
                                    lights.arrayPane[o].background = Background(BackgroundFill(Color.web(String.format("#%02x%02x%02x", color, color, color)), CornerRadii.EMPTY, Insets.EMPTY))
                                    stats.array[o].lblTemperature.text = arrayByte[2].toInt().toString()
                                    stats.array[o].lblHumidity.text = arrayByte[3].toInt().toString()
                                    stats.array[o].lblLight.text = color.toString()
                                }
                            }
                        }
                    }

                    i = i shl 1

                }


                println("Rooms: ${arrayByte[0]}\n" +
                        "Light: ${arrayByte[1]}\n" +
                        "Temperature: ${arrayByte[2]}\n" +
                        "Humidity: ${arrayByte[3]}\n")


            } catch (socketExc: SocketException) {
                println("Now closing")
                boom = true
            }
        }


    }

    private fun writeOnFile(ipAddress: String, room: Int, light: Int, temp: Int, humidity: Int) {

        val fileName = "log.json"

        var jArray: JSONArray

        try {
            val input = FileReader(fileName)
            val reader = BufferedReader(input)
            jArray = JSONArray(reader.readLine())
            input.close()
            reader.close()
        } catch (jexc: JSONException) {
            jArray = JSONArray()
        } catch (fout: FileNotFoundException) {
            jArray = JSONArray()
        }

        val jObject = JSONObject()
        jObject.put("IP client", ipAddress)
        jObject.put("room", room)
        jObject.put("light", light)
        jObject.put("temperature", temp)
        jObject.put("humidity", humidity)

        jArray.put(jObject)

        val file = File(fileName)
        if (file.exists())
            file.delete()

        val out = FileWriter(fileName)
        val writer = BufferedWriter(out)

        writer.write(jArray.toString())
        writer.close()
        out.close()

    }

    fun explode() {
        if (!socket.isClosed)
            socket.close()
    }

}