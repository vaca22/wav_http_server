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
        File("fuck.pcm").delete()
        val fuck=File("fuck.pcm")
       Thread{
           val aa=ByteArray(1024)
           server = ServerSocket(8899)
           mySocket= server.accept()
           while (true){
               val ll= mySocket.getInputStream().read(aa)
               if(ll<0){
                   break;
               }
               if(ll>0){
                   fuck.appendBytes(aa.copyOfRange(0,ll))
               }
           }
           mySocket.close()

       }.start()
    }




}