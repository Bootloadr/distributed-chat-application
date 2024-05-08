package com.abhinav.chatte;

import com.abhinav.chatte.controller.CrossServerController;
import com.abhinav.chatte.pojo.ApiInput;
import com.abhinav.chatte.pojo.ApiOutput;
import com.abhinav.chatte.pojo.Message;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ControllerTest {
  @Mock
  private WebSocket webSocket;

  @Mock
  private WebSocketSession session;
  @InjectMocks
  private CrossServerController controller;

  @Test
  public void sendMessageWithSuccess() throws IOException {
    HashMap<String, WebSocketSession> map = new HashMap<>();
    map.put("user1", session);
    Mockito.doNothing().when(session).sendMessage(any());
    when(webSocket.getSessions()).thenReturn(map);

    Message message = new Message();
    message.setToUser("user1");
    ApiInput input = new ApiInput();
    input.setMessagePayload(message);
    ApiOutput output = controller.sendMessage(input);

    Assert.assertEquals(output.isSuccess(), true);
  }

}

