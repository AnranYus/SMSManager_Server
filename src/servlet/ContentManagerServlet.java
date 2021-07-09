package servlet;

import org.java_websocket.server.WebSocketServer;
import webscoket.Server;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetSocketAddress;
@WebServlet(name = "ContentManagerServlet",value = "/ContentManagerServlet",loadOnStartup = 1)
public class ContentManagerServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        new Thread(new Runnable() {
            @Override
            public void run() {
                WebSocketServer server = new Server(new InetSocketAddress("0.0.0.0",8899));
                server.run();
            }
        }).start();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);


    }
}
