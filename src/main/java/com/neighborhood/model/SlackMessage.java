package com.neighborhood.model;

public class SlackMessage {
  String name;
  String subject;
  String message;

  public SlackMessage() {
  }

  public SlackMessage(String name, String subject, String message) {
    this.name = name;
    this.subject = subject;
    this.message = message;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSubject() {
    return this.subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String toJSONString() {
    // convert to JSON formatted as {"text":"*Name: * this.name\n*Subject: *
    // this.subject\n*Message:*\n>this.message"}
    return "{\"text\":\"*Name: * " + this.name + "\\n*Subject: * " + this.subject + "\\n*Message:*\\n>" + this.message
        + "\"}";
  }
}
