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
@RequestMapping("/api/user")
@Tag(name = "User", description = "User API")
public class UserController {
    private final UserService userService;

    @ApiResponse(responseCode = "200", description = "Users list retrieved", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class))))
    @GetMapping
    @Operation(summary = "Get all users", description = "Get all users from the DB")
    public List<User> getAll() {
        return userService.findAll();
    }

    @Operation(summary = "Get user by id", description = "Get user by id from the DB")
    @GetMapping("/{id}")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))), @ApiResponse(responseCode = "404", description = "Entity not found", content = @Content), @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    public User getById(@PathVariable(name = "id") @Parameter(description = "User id", required = true, example = "1") Long id) {
        return userService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Create new user", description = "Create new user in the DB")
    @ApiResponse(responseCode = "200", description = "User created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
    public User createNew(@RequestBody User user) {
        return userService.save(user);
    }

    @Operation(summary = "Update user by id", description = "Update user by id in the DB")
    @PutMapping("/{id}")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))), @ApiResponse(responseCode = "404", description = "Entity not found", content = @Content), @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    public User updateUser(@PathVariable("id") @Parameter(description = "User id", required = true, example = "1") Long id, @RequestBody User user) {
        return userService.update(id, user);
    }

    @Operation(summary = "Delete user by id", description = "Delete user by id from the DB")
    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "User retrieved", content = @Content)
    public void deleteUser(@PathVariable("id") @Parameter(description = "User id", required = true, example = "1") Long id) {
        userService.deleteById(id);
    }

    @Operation(summary = "Add task to user", description = "Add task to user in the DB")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))), @ApiResponse(responseCode = "404", description = "Entity not found", content = @Content), @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @PutMapping("/{userId}/task/{taskId}")
    public ResponseEntity<User> addTaskToUser(@PathVariable("userId") @Parameter(description = "User id", required = true, example = "1") Long userId, @PathVariable("taskId") @Parameter(description = "Task id", required = true, example = "1") Long taskId) {
        User user = userService.addTaskToUser(userId, taskId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))), @ApiResponse(responseCode = "404", description = "Entity not found", content = @Content), @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @DeleteMapping("/{userId}/task/{taskId}")
    @Operation(summary = "Remove task from user", description = "Remove task from user in the DB")
    public ResponseEntity<User> removeTaskFromUser(@PathVariable("userId") @Parameter(description = "User id", required = true, example = "1") Long userId, @PathVariable("taskId") @Parameter(description = "Task id", required = true, example = "1") Long taskId) {
        User user = userService.removeTaskFromUser(userId, taskId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
}
