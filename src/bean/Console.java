package bean;

import org.java_websocket.WebSocket;

public class Console {
    WebSocket webSocketConsole;
    String bindUUID;
    String ConsoleUUID;

    public WebSocket getWebSocketConsole() {
        return webSocketConsole;
    }

    public void setWebSocketConsole(WebSocket webSocketConsole) {
        this.webSocketConsole = webSocketConsole;
    }

    public String getBindUUID() {
        return bindUUID;
    }

    public void setBindUUID(String bindUUID) {
        this.bindUUID = bindUUID;
    }

    public String getConsoleUUID() {
        return ConsoleUUID;
    }

    public void setConsoleUUID(String consoleUUID) {
        ConsoleUUID = consoleUUID;
    }
}
