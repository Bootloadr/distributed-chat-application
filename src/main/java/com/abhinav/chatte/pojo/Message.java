package com.abhinav.chatte.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
@JsonSerialize
public class Message implements Serializable {
  private String toUser;
  private String fromUser;
  private String content;

  public Message(){
  }
  public Message(String fromUser, String toUser, String content){
    this.toUser = toUser;
    this.fromUser = fromUser;
    this.content = content;
  }
  public String getToUser() {
    return toUser;
  }

  public void setToUser(String toUser) {
    this.toUser = toUser;
  }

  public String getFromUser() {
    return fromUser;
  }

  public void setFromUser(String fromUser) {
    this.fromUser = fromUser;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
