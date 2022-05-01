import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.spi.HttpServerProvider
import java.io.File
import java.io.IOException
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket

object HttpServerTest {
    lateinit var server: ServerSocket
    lateinit var mySocket:Socket
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val fuck=File("esp32.mp3").readBytes()
        var remain=fuck.size
        println("fuck "+fuck.size)
       Thread{
           server = ServerSocket(8899)
           mySocket= server.accept()
           while (remain>0){
                if(remain>1024){
                    send(fuck.copyOfRange(remain-1024,remain))
                }else{
                    send(fuck.copyOfRange(0,remain))
                }
           }
           mySocket.close()

       }.start()
    }

    fun send(b: ByteArray) {
        val output = mySocket.getOutputStream()
        output.write(b)
        output.flush()
    }


}