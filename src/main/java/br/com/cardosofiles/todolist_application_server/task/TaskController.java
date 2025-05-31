package br.com.cardosofiles.todolist_application_server.task;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.cardosofiles.todolist_application_server.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {

  // private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

  @Autowired
  private ITaskRepository taskRepository;

  @PostMapping("/")
  public ResponseEntity<Object> create(@RequestBody @Valid TaskModel taskModel,
      HttpServletRequest request) {
    try {
      var idUser = (UUID) request.getAttribute("idUser");
      taskModel.setIdUser(idUser);

      taskModel.validate();

      var task = this.taskRepository.save(taskModel);
      return ResponseEntity.status(HttpStatus.CREATED).body(task);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Internal server error: " + e.getMessage());
    }
  }

  @GetMapping("/")
  public ResponseEntity<List<TaskModel>> list(HttpServletRequest request) {
    var idUser = (UUID) request.getAttribute("idUser");
    var tasks = this.taskRepository.findByIdUser(idUser);
    return ResponseEntity.ok(tasks);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> update(@RequestBody TaskModel taskModel, @PathVariable UUID id,
      HttpServletRequest request) {
    try {
      var task = this.taskRepository.findById(id).orElse(null);

      if (task == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
      }

      var idUser = (UUID) request.getAttribute("idUser");

      if (!task.getIdUser().equals(idUser)) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body("You don't have permission to update this task");
      }

      Utils.copyNonNullProperties(taskModel, task);
      task.validate();

      var updatedTask = this.taskRepository.save(task);
      return ResponseEntity.ok().body(updatedTask);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Internal server error: " + e.getMessage());
    }
  }
}
