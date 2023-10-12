package br.com.thenriquedb.todolist.task;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        var task = this.taskRepository.findByTitle(taskModel.getTitle());

        if(task != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("There is already a task with this title");
        }

        LocalDateTime today = LocalDateTime.now();

        if(today.isAfter(taskModel.getStartAt())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("The start date cannot be less than the current date ");
        }

        if(today.isAfter(taskModel.getEndAt())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("The end date cannot be less than the current date ");
        }

        if(taskModel.getEndAt().isBefore(taskModel.getStartAt())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("The end date cannot be less than the start date");
        }


        var userId = (UUID) request.getAttribute("userId");
        taskModel.setUserId(userId);

        var createdTask = this.taskRepository.save(taskModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }
}
