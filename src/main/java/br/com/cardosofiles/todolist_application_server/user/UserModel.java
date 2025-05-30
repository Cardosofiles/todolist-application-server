package br.com.cardosofiles.todolist_application_server.user;

import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_users")
public class UserModel {

  @Id
  @GeneratedValue(generator = "UUID")
  private UUID id;

  @Column(unique = true)
  private String name;
  private String username;
  private String password;

  @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
  @CreationTimestamp
  private LocalDateTime createdAt;
}
