package com.abhinav.chatte.pojo;

import com.abhinav.chatte.constant.ErrorCode;

public class ApiOutput{
  private boolean success = true;
  private String errorCode;
  private String errorMessage;

  public ApiOutput(){}

  public ApiOutput(ErrorCode code){
    success = false;
    errorCode = code.getCode();
    errorMessage = code.getDescription();
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}
