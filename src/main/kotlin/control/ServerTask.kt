package control

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.SocketException

class ServerTask(PORT: Int) : Thread() {

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
                println(arrayByte)
            }catch(socketExc: SocketException){
                println("Now closing")
                boom=true
            }
        }


    }


    fun explode() {
        if (!socket.isClosed)
            socket.close()
    }

}