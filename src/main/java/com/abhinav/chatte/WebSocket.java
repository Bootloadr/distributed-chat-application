package com.abhinav.chatte;

import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;

public class WebSocket {
  private final HashMap<String, WebSocketSession> sessions;
  public WebSocket(){
    this.sessions = new HashMap<>();
  }

  public HashMap<String, WebSocketSession> getSessions() {
    return sessions;
  }
}
