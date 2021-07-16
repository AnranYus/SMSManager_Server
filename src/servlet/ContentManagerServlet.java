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
    Thread thread;


    @Override
    public void init() throws ServletException {
        super.init();
        thread = new MyServer();
        thread.start();

    }

    @Override
    public void destroy() {
        super.destroy();
        thread.interrupt();
        try {
            thread.join();
            System.out.println("Server End");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);

    }

    class MyServer extends Thread{
        @Override
        public void run() {
            WebSocketServer server = new Server(new InetSocketAddress("0.0.0.0",8899));
            server.run();

        }
    }
}

