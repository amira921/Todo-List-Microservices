package com.microservices.controller;

import com.microservices.service.TaskService;
import com.microservices.dto.TaskDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/profile/{email}")
@AllArgsConstructor
@Slf4j
public class TaskController {
    private final TaskService service;

    @GetMapping("")
    public ResponseEntity<?> getTasks(@PathVariable String email) {
        List<TaskDTO> tasks = service.getTasksByUserEmail(email);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(tasks);
    }

    @GetMapping("/filter=personal")
    public ResponseEntity<?> getPersonalTasks(@PathVariable String email) {
        List<TaskDTO> tasks = service.getPersonalTasksByUserEmail(email);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(tasks);
    }

    @GetMapping("/filter=work")
    public ResponseEntity<?> getWorkTasks(@PathVariable String email) {
        List<TaskDTO> tasks = service.getWorkTasksByUserEmail(email);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(tasks);
    }

    @GetMapping("/filter=today")
    public ResponseEntity<?> getTodayTasks(@PathVariable String email) {
        List<TaskDTO> tasks = service.getTodayTasksByUserEmail(email);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(tasks);
    }

    @GetMapping("/filter=next-week")
    public ResponseEntity<?> getWeeklyTasks(@PathVariable String email) {
        List<TaskDTO> tasks = service.getWeeklyTasksByUserEmail(email);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(tasks);
    }

    @GetMapping("/filter=in-progress")
    public ResponseEntity<?> getInProgressTasks(@PathVariable String email) {
        List<TaskDTO> tasks = service.getInProgressTasksByUserEmail(email);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(tasks);
    }

    @GetMapping("/filter=completed")
    public ResponseEntity<?> getCompletedTasks(@PathVariable String email) {
        List<TaskDTO> tasks = service.getCompletedTasksByUserEmail(email);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(tasks);
    }

    @GetMapping("/filter=overdue")
    public ResponseEntity<?> getOverdueTasks(@PathVariable String email) {
        List<TaskDTO> tasks = service.getOverdueTasksByUserEmail(email);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(tasks);
    }

    @PostMapping("/addTask")
    public ResponseEntity<?> createTask(@RequestBody @Valid TaskDTO task, @PathVariable String email) {
        service.addTask(task,email);
        return ResponseEntity.status(HttpStatus.CREATED).body("task created successfully!");
    }
    @PostMapping("/updateTask/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable("email")  String email,
                                        @PathVariable("taskId") Integer taskId,
                                        @RequestBody @Valid TaskDTO task) {
        service.updateTask(email,taskId, task);
        return ResponseEntity.status(HttpStatus.OK).body("task updated successfully!");
    }
    @GetMapping ("/deleteTask/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable("email")  String email, @PathVariable("taskId") Integer taskId) {
        service.deleteTaskById(taskId);
        return ResponseEntity.status(HttpStatus.OK).body("task deleted successfully!");
    }
}
