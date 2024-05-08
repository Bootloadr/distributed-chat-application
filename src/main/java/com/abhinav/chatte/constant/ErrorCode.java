package com.abhinav.chatte.constant;

public enum ErrorCode {
  USER_NOT_ONLINE("CHATTE_000001","User is not online"),
  JAVA_EXCEPTION("CHATTE_000002","Exception caught"),
  DATA_NOT_FOUND("CHATTE_000004","Data not found for this CHAT");
  private String code;
  private String description;

  private ErrorCode(String code, String description){
    this.code = code;
    this.description = description;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}