package br.com.cardosofiles.todolist_application_server.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  @PostMapping("/")
  public String create(@RequestBody UserModel userModel) {
    System.out.println(userModel.getUsername());
    System.out.println(userModel.getPassword());
    System.out.println(userModel.getName());
    return "OK";
  }
}
