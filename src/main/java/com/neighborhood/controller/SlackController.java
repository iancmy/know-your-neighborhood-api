package com.neighborhood.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neighborhood.model.SlackMessage;
import com.neighborhood.service.SlackService;

@RestController
@RequestMapping("/api/public/contact")
public class SlackController {
  @Autowired
  private SlackService slackService;

  @PostMapping("/")
  public ResponseEntity<?> sendMessage(@RequestBody SlackMessage slackMessage) {
    Map<String, Object> response = new HashMap<>();
    boolean success = false;

    String slackResponse = slackService.sendMessage(slackMessage);

    if (slackResponse.equals("ok")) {
      success = true;
      response.put("success", success);
      response.put("message", "Message sent successfully");
      response.put("data", slackMessage);

      return new ResponseEntity<>(response, HttpStatus.OK);
    } else {
      response.put("success", success);
      response.put("message", "Message failed to send");
      response.put("data", null);

      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
