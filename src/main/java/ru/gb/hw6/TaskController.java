package ru.gb.hw6;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task")
@Tag(name = "Task", description = "Task API")
public class TaskController {
    private final TaskService taskService;

    @ApiResponse(responseCode = "200", description = "Tasks list retrieved", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", array = @ArraySchema(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Task.class))))
    @GetMapping
    @Operation(summary = "Get all tasks", description = "Get all tasks from the DB")
    public List<Task> getAll() {
        return taskService.findAll();
    }

    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Task retrieved", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Task.class))), @ApiResponse(responseCode = "404", description = "Entity not found", content = @Content), @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @GetMapping("/{id}")
    @Operation(summary = "Get task by id", description = "Get task by id from the DB")
    public ResponseEntity<Task> getById(@PathVariable("id") @Parameter(description = "Task id", required = true, example = "1") Long id) {
        Task task = taskService.findById(id);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(task);
    }

    @ApiResponse(responseCode = "200", description = "Task created", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Task.class)))
    @Operation(summary = "Create new task", description = "Create new task in the DB")
    @PostMapping
    public Task createNew(@RequestBody Task task) {
        return taskService.save(task);
    }

    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Task retrieved", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Task.class))), @ApiResponse(responseCode = "404", description = "Entity not found", content = @Content), @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @Operation(summary = "Update task by id", description = "Update task by id in the DB")
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable("id") @Parameter(description = "Task id", required = true, example = "1") Long id, @RequestBody Task task) {
        return taskService.update(id, task);
    }

    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Task retrieved", content = @Content), @ApiResponse(responseCode = "404", description = "Entity not found", content = @Content), @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete task by id", description = "Delete task by id from the DB")
    public void deleteTask(@PathVariable("id") @Parameter(description = "Task id", required = true, example = "1") Long id) {
        taskService.deleteById(id);
    }

    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Task retrieved", content = @Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Task.class))), @ApiResponse(responseCode = "404", description = "Entity not found", content = @Content), @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @Operation(summary = "Add task to user", description = "Add task to user in the DB")
    @PutMapping("/{taskId}/user/{userId}")
    public ResponseEntity<Task> addTaskToUser(@PathVariable("userId") @Parameter(description = "User id", required = true, example = "1") Long userId, @PathVariable("taskId") @Parameter(description = "Task id", required = true, example = "1") Long taskId) {
        Task task = taskService.addUserToTask(taskId, userId);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(task);
    }

    @ApiResponse(responseCode = "200", description = "Task retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Task.class)))
    @Operation(summary = "Remove user from task", description = "Remove user from task in the DB")
    @DeleteMapping("/{taskId}/user/{userId}")
    public ResponseEntity<Task> removeUserFromTask(@PathVariable("userId") @Parameter(description = "User id", required = true, example = "1") Long userId, @PathVariable("taskId") @Parameter(description = "Task id", required = true, example = "1") Long taskId) {
        Task task = taskService.removeUserFromTask(taskId, userId);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(task);
    }

    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Task retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Task.class))), @ApiResponse(responseCode = "404", description = "Entity not found", content = @Content), @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @Operation(summary = "Update task state", description = "Update task state in the DB")
    @PutMapping("/{id}/state")
    public ResponseEntity<Task> updateState(@PathVariable("id") @Parameter(description = "Task id", required = true, example = "1") Long id, @RequestBody String state) {
        Task task = taskService.updateState(id, state);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(task);
    }

}
