package br.com.thenriquedb.todolist.task;

import br.com.thenriquedb.todolist.utils.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@SecurityRequirement(name = "basic-auth")
@Tag(name = "Task")
public class TaskController {
    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping
    @Operation(summary = "Create new task")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
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

    @GetMapping
    @Operation(summary = "List all tasks by user")
    public ResponseEntity list( HttpServletRequest request) {
        var userId = (UUID) request.getAttribute("userId");

        var tasks = this.taskRepository.findAllByUserId(userId);

        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{taskId}")
    @Operation(summary = "Get task by ID")
    @SecurityRequirement(name = "basic-auth")
    public ResponseEntity<Optional<TaskModel>> get(@PathVariable UUID taskId, HttpServletRequest request) {
        var task = this.taskRepository.findById(taskId).orElse(null);
        UUID userId = (UUID) request.getAttribute("userId");

        if(task == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        if(!task.getUserId().equals(userId)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }

        return ResponseEntity.ok().body(task);
    }


    @DeleteMapping("/{taskId}")
    @Operation(summary = "Delete an existing task")
    @SecurityRequirement(name = "basic-auth")
    public ResponseEntity delete(
            @PathVariable UUID taskId,
            HttpServletRequest request) {
        var task = this.taskRepository.findById(taskId).orElse(null);
        UUID userId = (UUID) request.getAttribute("userId");

        if(task == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Task not found");
        }

        if(!task.getUserId().equals(userId)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Operation unauthorized");
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{taskId}")
    @Operation(summary = "Update existing task")
    @SecurityRequirement(name = "basic-auth")
    public ResponseEntity update(
            @RequestBody TaskModel taskModel,
            @PathVariable UUID taskId,
            HttpServletRequest request) {
        var task = this.taskRepository.findById(taskId).orElse(null);
        UUID userId = (UUID) request.getAttribute("userId");

        if(task == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Task not found");
        }

        if(!task.getUserId().equals(userId)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Operation unauthorized");
        }

        Utils.copyNonNullableProperties(taskModel, task);

        var updatedTask = this.taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.OK).body(updatedTask);
    }
}
