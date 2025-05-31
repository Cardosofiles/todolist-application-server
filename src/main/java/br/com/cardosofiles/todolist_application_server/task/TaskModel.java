package br.com.cardosofiles.todolist_application_server.task;

import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {
  @Id
  @GeneratedValue(generator = "UUID")
  private UUID id;

  @NotBlank(message = "Description is required")
  @Size(max = 255, message = "Description must be less than 255 characters")
  private String description;

  @NotBlank(message = "Title is required")
  @Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters")
  @Column(length = 50)
  private String title;

  @jakarta.validation.constraints.NotNull(message = "Start date is required")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @FutureOrPresent(message = "Start date must be in the present or future")
  private LocalDateTime startAt;

  @jakarta.validation.constraints.NotNull(message = "End date is required")
  @FutureOrPresent(message = "End date must be in the present or future")
  private LocalDateTime endAt;

  @NotBlank(message = "Priority is required")
  @Size(min = 3, max = 20, message = "Priority must be between 3 and 20 characters")
  @NotBlank(message = "Priority is required")
  @Size(min = 3, max = 20, message = "Priority must be between 3 and 20 characters")
  private String priority;

  @jakarta.validation.constraints.NotNull(message = "User ID is required")
  private UUID idUser;

  @CreationTimestamp
  private LocalDateTime createdAt;

  public void validate() {
    if (this.startAt != null && this.endAt != null && this.startAt.isAfter(this.endAt)) {
      throw new IllegalArgumentException("Start date must be before end date");
    }
  }
}


