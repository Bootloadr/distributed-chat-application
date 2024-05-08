package com.abhinav.chatte.handler;

import com.abhinav.chatte.WebSocket;
import com.abhinav.chatte.model.Conversation;
import com.abhinav.chatte.pojo.ApiInput;
import com.abhinav.chatte.pojo.ApiOutput;
import com.abhinav.chatte.pojo.Message;
import com.abhinav.chatte.repository.ConversationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestClient;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;

public class ChatWebSocketHandler extends TextWebSocketHandler {
  private final String path = "/chatte/process";
  @Value("${server.url}")
  private String serverUrl;
  @Autowired
  private RedisTemplate<String, String> redisTemplate;
  @Autowired
  private WebSocket sessions;
  @Autowired
  private ConversationRepository conversationRepository;
  private static final Logger logger = LoggerFactory.getLogger(ChatWebSocketHandler.class);

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    //Validation token
    String userName = session.getUri().getQuery().split("=")[1];
    logger.info("Connection established for user: " + userName);
    redisTemplate.opsForValue().set(userName,serverUrl);
    sessions.getSessions().put(userName,session);
    List<Conversation> unreadMessages = conversationRepository.findByToUserAndReadStatus(userName,false);
    ObjectMapper objectMapper = new ObjectMapper();
    for(Conversation conversation : unreadMessages){
      Message message = new Message(conversation.getFromUser(), conversation.getToUser(),conversation.getMessage());
      TextMessage textMessage = new TextMessage(new StringBuilder(objectMapper.writeValueAsString(message)));
      session.sendMessage(textMessage);
      conversation.setReadStatus(true);
      conversationRepository.save(conversation);
    }
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    Message conversation = objectMapper.readValue(message.getPayload(), Message.class);
    logger.info("Message received from user: " + conversation.getFromUser());
    UUID id = UUID.randomUUID();
    Conversation entity = new Conversation(id, conversation.getFromUser(), conversation.getToUser(), conversation.getContent(), true);
    if(sessions.getSessions().containsKey(conversation.getToUser())){
      sessions.getSessions().get(conversation.getToUser()).sendMessage(message);
      logger.info("Message sent to user: " + conversation.getToUser());
    }
    else {
      String serverAddress = redisTemplate.opsForValue().get(conversation.getToUser());//Create a Pojo for redis stored object
      if(serverAddress == null) {
        entity.setReadStatus(false);
        conversationRepository.save(entity);
        logger.info("Message stored in Db for user: " + conversation.getToUser());
        return;
      }
      ApiOutput output = sendPayloadToServerHoldingConnection(conversation, message, serverAddress);
      if(!output.isSuccess()){
        entity.setReadStatus(false);
      }
    }
    conversationRepository.save(entity);
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    String userName = session.getUri().getQuery().split("=")[1];
    sessions.getSessions().remove(userName);
    logger.info("Session closed for user: " + userName);
  }

  private ApiOutput sendPayloadToServerHoldingConnection(Message conversation, TextMessage message, String serverAddress){
    RestClient restClient = RestClient.create();
    ApiInput input = new ApiInput();
    input.setMessagePayload(conversation);
    input.setTextMessage(message);
    ApiOutput output = restClient.post()
        .uri(serverAddress + path)
        .contentType(APPLICATION_JSON)
        .body(input)
        .retrieve()
        .body(ApiOutput.class);
    return output;
  }
}
