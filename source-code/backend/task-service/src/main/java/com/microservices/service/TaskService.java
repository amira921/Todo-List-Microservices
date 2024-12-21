package com.microservices.service;

import com.microservices.dto.TaskDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface TaskService {
    List<TaskDTO> getTasksByUserEmail(String email);
    List<TaskDTO> getPersonalTasksByUserEmail(String email);
    List<TaskDTO> getWorkTasksByUserEmail(String email);
    List<TaskDTO> getTodayTasksByUserEmail(String email);
    List<TaskDTO> getWeeklyTasksByUserEmail(String email);
    List<TaskDTO> getInProgressTasksByUserEmail(String email);
    List<TaskDTO> getOverdueTasksByUserEmail(String email);
    List<TaskDTO> getCompletedTasksByUserEmail(String email);
    TaskDTO addTask(TaskDTO task, String email);
    TaskDTO updateTask(String email, Integer taskId, TaskDTO taskDTO);
    void deleteTaskById(Integer taskId);
}
