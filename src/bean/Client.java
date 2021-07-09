package bean;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;

public class Client {
    public WebSocket getWebSocketClient() {
        return webSocketClient;
    }

    public void setWebSocketClient(WebSocket webSocketClient) {
        this.webSocketClient = webSocketClient;
    }

    public String getClientUUID() {
        return clientUUID;
    }

    public void setClientUUID(String clientUUID) {
        this.clientUUID = clientUUID;
    }

    WebSocket webSocketClient;
    String clientUUID;
}
