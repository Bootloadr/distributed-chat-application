package com.abhinav.chatte.controller;

import com.abhinav.chatte.WebSocket;
import com.abhinav.chatte.constant.ErrorCode;
import com.abhinav.chatte.pojo.ApiInput;
import com.abhinav.chatte.pojo.ApiOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value="/chatte")
public class CrossServerController {
  private static final Logger logger = LoggerFactory.getLogger(CrossServerController.class);
  @Autowired
  private WebSocket webSocket;
  @RequestMapping(value = "/process",method = RequestMethod.POST)
  public ApiOutput sendMessage(@RequestBody ApiInput input) throws IOException {
    if( webSocket.getSessions().containsKey(input.getMessagePayload().getToUser())) {
      //logger.info(input.getTextMessage().toString());
      webSocket.getSessions().get(input.getMessagePayload().getToUser()).sendMessage(input.getTextMessage());
      return new ApiOutput();
    }
    else
      return new ApiOutput(ErrorCode.USER_NOT_ONLINE);
  }
}
