package br.com.cardosofiles.todolist_application_server.user;

import lombok.Data;

@Data
public class UserModel {
  private String name;

  private String username;

  private String password;
}
