package webscoket;

import bean.Client;
import bean.Console;
import bean.Content;
import com.google.gson.Gson;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

import static webscoket.Pool.ClientList;
import static webscoket.Pool.ConsoleList;

public class Server extends WebSocketServer {
    Gson gson =new Gson();
    String connUUID;
    String connOrigin;
    public Server(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {

    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
//        if (connOrigin!=null){
//            if (connOrigin.equals("client")){
//                for (int j =0;j<ClientList.size();j++){
//                    if (ClientList.get(j).getClientUUID().equals(connUUID)){
//                        ClientList.remove(j);
//                        break;
//                    }
//                }
//            }else {
//                for (int j =0;j<ConsoleList.size();j++){
//                    if (ConsoleList.get(j).getConsoleUUID().equals(connUUID)){
//                        ConsoleList.remove(j);
//                        break;
//                    }
//                }
//            }
//        }
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        System.out.println(s);
        Content content = gson.fromJson(s, Content.class);
        if (content.getType().equals("connection")) {
            connection(webSocket,content);
        }
        if (content.getType().equals("sendSMS")){
            //发送短信操作
            sendSMS(webSocket,content);
        }
        if(content.getType().equals("getSMS")){
            //收到短信 推送到控制端
            System.out.println("StartGetSMS");
            getSMS(webSocket,content);

        }
        if (content.getType().equals("close")){
            if (content.getOrigin().equals("client")){
                for (int i =0; i<ClientList.size();i++){
                    if (content.getSenderUUID().equals(ClientList.get(i).getClientUUID())){
                        ClientList.get(i).getWebSocketClient().close();
                        ClientList.remove(i);

                    }
                }
            }else if (content.getOrigin().equals("console")){
                for (int i =0; i<ConsoleList.size();i++){
                    if (content.getSenderUUID().equals(ConsoleList.get(i).getConsoleUUID())){
                        ConsoleList.get(i).getWebSocketConsole().close();
                        ConsoleList.remove(i);
                    }
                }
            }
        }


    }

    private void getSMS(WebSocket webSocket,Content content){
        System.out.println("In GetSMS");
        int i = 0;
        for(Console console : ConsoleList){
            i++;
            if (console.getConsoleUUID().equals(content.getRecipientUUID())){
                System.out.println("PushStart");
                console.getWebSocketConsole().send(gson.toJson(content));
                //回报
                Content content1 = new Content("推送成功","reply","null",content.getSenderUUID(),"server");
                webSocket.send(gson.toJson(content1));
                System.out.println("PushEnd");
                i=0;
                break;
            }

        }
        if (i==ConsoleList.size()){
            Content content1 = new Content("未找到设备","reply","null",content.getSenderUUID(),"server");
            webSocket.send(gson.toJson(content1));
            System.out.println("没有设备");
        }
    }

    private void sendSMS(WebSocket webSocket, Content content) {
        System.out.println("sendSMS");
        String recipientUUID = content.getRecipientUUID();
        for (int i =0;i<=ClientList.size();i++){
            //TODO 设备UUID遍历存在问题
            if (i==ClientList.size()){
                //没有设备
                Content content1 = new Content("未找到目标设备","reply","null",content.getSenderUUID(),"server");
                webSocket.send(gson.toJson(content1));
            }

            if (ClientList.get(i).getClientUUID().equals(recipientUUID)){
                ClientList.get(i).getWebSocketClient().send(gson.toJson(content));
                //回报
                Content content1 = new Content("发送成功","reply","null",content.getSenderUUID(),"server");
                webSocket.send(gson.toJson(content1));
                break;
            }

        }
    }

    private void connection(WebSocket webSocket,Content content){
        System.out.println("isConnection:"+content.getSenderUUID());
        connUUID = content.getSenderUUID();
        connOrigin = content.getOrigin();
        //检查发起连接请求的客户端类型
        if (content.getOrigin().equals("client")) {
            System.out.println("client");
            Client client = new Client();
            client.setWebSocketClient(webSocket);
            client.setClientUUID(content.getSenderUUID());
            ClientList.add(client);


        }else if(content.getOrigin().equals("console")){
            System.out.println("console");
            Console console = new Console();
            console.setConsoleUUID(content.getSenderUUID());
            console.setBindUUID(content.getRecipientUUID());
            console.setWebSocketConsole(webSocket);
            ConsoleList.add(console);
            for (Client client: ClientList){
                if (client.getClientUUID().equals(console.getBindUUID())){
                    Content consoleToClient = new Content();
                    consoleToClient.setType("bindConsole");
                    consoleToClient.setSenderUUID(console.getConsoleUUID());
                    consoleToClient.setRecipientUUID(client.getClientUUID());
                    consoleToClient.setOrigin("console");
                    consoleToClient.setContent(console.getConsoleUUID());
                    String json = gson.toJson(consoleToClient);
                    client.getWebSocketClient().send(json);
                    System.out.println("bind");
                    break;
                }else {
                    System.out.println("not found");
                }

            }



        }
        Content connOK =new Content();
        connOK.setType("200");
        connOK.setSenderUUID("null");
        connOK.setOrigin("server");
        connOK.setRecipientUUID(content.getSenderUUID());
        webSocket.send(new Gson().toJson(connOK));
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        webSocket.send(e.getMessage());
    }

    @Override
    public void onStart() {
        System.out.println("Server Start");
    }


}
