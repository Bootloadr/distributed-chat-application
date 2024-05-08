package com.abhinav.chatte.model;

import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class Conversation {
  @PrimaryKey
  private UUID id;
  @Column("from_user")
  private String fromUser;
  @Column("to_user")
  private String toUser;
  @Column("read_status")
  private boolean readStatus;
  @Column("message")
  private String message;

  public Conversation() {
  }

  public Conversation(UUID id, String fromUser, String toUser, String text,boolean readStatus) {
    this.id = id;
    this.fromUser = fromUser;
    this.toUser = toUser;
    this.message = text;
    this.readStatus = readStatus;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getFromUser() {
    return fromUser;
  }

  public void setFromUser(String fromUser) {
    this.fromUser = fromUser;
  }

  public String getToUser() {
    return toUser;
  }

  public void setToUser(String toUser) {
    this.toUser = toUser;
  }

  public boolean isReadStatus() {
    return readStatus;
  }

  public void setReadStatus(boolean read_status) {
    this.readStatus = read_status;
  }

  public String getMessage() {
    return message;
  }

  public void setText(String text) {
    this.message = text;
  }
}
