package com.neighborhood.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.neighborhood.model.SlackMessage;

@Service
public class SlackService {
  private static final String WEBHOOK_URL = "https://hooks.slack.com/services";
  private static final String CHANNEL = "/T04RKQK9GD9/B04SB9C7N7P/eeQQfXNAVZzTSPISZT2F88ct";

  public String sendMessage(SlackMessage slackMessage) {
    String url = WEBHOOK_URL + CHANNEL;
    String json = slackMessage.toJSONString();

    HttpHeaders header = new HttpHeaders();
    header.add("Content-Type", "application/json");

    HttpEntity<String> entity = new HttpEntity<>(json, header);

    RestTemplate restTemplate = new RestTemplate();

    try {
      return restTemplate.postForObject(url, entity, String.class);
    } catch (Exception e) {
      e.printStackTrace();
      return HttpStatus.INTERNAL_SERVER_ERROR.toString();
    }
  }
}
