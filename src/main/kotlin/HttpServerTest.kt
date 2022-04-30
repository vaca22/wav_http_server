import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.spi.HttpServerProvider
import java.io.IOException
import java.net.InetSocketAddress

object HttpServerTest {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        /*运行服务器*/
        RunServer()
    }

    @Throws(IOException::class)
    fun RunServer() {
        val provider = HttpServerProvider.provider()
        val httpserver = provider.createHttpServer(InetSocketAddress(8200), 100) //监听端口8200,能同时接受100个请求
        httpserver.createContext("/upload", TestResponseHandler())
        httpserver.executor = null
        httpserver.start()
        println("启动服务器")
    }

    class TestResponseHandler : HttpHandler {
        @Throws(IOException::class)
        override fun handle(httpExchange: HttpExchange) {
            val ss = httpExchange.requestBody
            val requestMethod = httpExchange.requestMethod
            if (requestMethod.equals("POST", ignoreCase = true)) { //客户端的请求是POST方法
                //设置服务端响应的编码格式，否则在客户端收到的可能是乱码
                val responseHeaders = httpExchange.responseHeaders
                responseHeaders["Content-Type"] = "text/html;charset=utf-8"
                println("启动服务器")
                //在这里通过httpExchange获取客户端发送过来的消息
                val url = httpExchange.requestURI
                println("启动服务器" + url.rawPath)
                val requestBody = httpExchange.requestBody
                val count = 100000
                val b = ByteArray(count)
                var readCount = 0
                while (true) {
                    readCount += requestBody.read(b, readCount, 500)
                    if (readCount >= count - 500) {
                        break
                    }
                }
            }
        }
    }
}