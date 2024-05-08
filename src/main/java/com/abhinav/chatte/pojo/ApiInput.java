package com.abhinav.chatte.pojo;

import org.springframework.web.socket.TextMessage;

public class ApiInput{
  private Message messagePayload;
  private TextMessage textMessage;

  public Message getMessagePayload() {
    return messagePayload;
  }

  public void setMessagePayload(Message messagePayload) {
    this.messagePayload = messagePayload;
  }

  public TextMessage getTextMessage() {
    return textMessage;
  }

  public void setTextMessage(TextMessage textMessage) {
    this.textMessage = textMessage;
  }
}