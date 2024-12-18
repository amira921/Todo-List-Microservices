package com.microservices.service.impl;
import com.microservices.dto.TaskDTO;
import com.microservices.entity.Task;
import com.microservices.mapper.TaskMapper;
import com.microservices.repository.TaskRepository;
import com.microservices.service.TaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.*;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskRepository repository;
    private final TaskMapper mapper;

    @Override
    public List<TaskDTO> getTasksByUserEmail(String email) {
        return repository.findAllByUserEmail(email)
                .stream()
                .map(mapper::mapToDTO)
                .toList();
    }

    @Override
    public List<TaskDTO> getPersonalTasksByUserEmail(String email) {
        return repository.findByUserEmailAndCategory(email, "personal")
                .stream()
                .map(mapper :: mapToDTO)
                .toList();
    }

    @Override
    public List<TaskDTO> getWorkTasksByUserEmail(String email) {
        return repository.findByUserEmailAndCategory(email, "work")
                .stream()
                .map(mapper::mapToDTO)
                .toList();
    }

    @Override
    public List<TaskDTO> getTodayTasksByUserEmail(String email) {
        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);
        return repository.findByUserEmailAndDate(email, startOfDay, endOfDay)
                .stream()
                .map(mapper::mapToDTO)
                .toList();
    }
    @Override
    public List<TaskDTO> getWeeklyTasksByUserEmail(String email) {
        LocalDateTime startOfWeek = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endOfWeek = LocalDateTime.now().plusDays(6).with(LocalTime.MAX);
        return repository.findByUserEmailAndDate(email, startOfWeek, endOfWeek)
                .stream()
                .map(mapper::mapToDTO)
                .toList();
    }

    @Override
    public List<TaskDTO> getInProgressTasksByUserEmail(String email) {
        return repository.findByUserEmailAndStatus(email,"inprogress")
                .stream()
                .map(mapper::mapToDTO)
                .toList();
    }

    @Override
    public List<TaskDTO> getOverdueTasksByUserEmail(String email) {
        return repository.findByUserEmailAndStatusAndDate(email,"inprogress",LocalDateTime.now())
                .stream()
                .map(mapper::mapToDTO)
                .toList();
    }

    @Override
    public List<TaskDTO> getCompletedTasksByUserEmail(String email) {
        return repository.findByUserEmailAndStatus(email, "completed")
                .stream()
                .map(mapper::mapToDTO)
                .toList();
    }

    @Override
    public TaskDTO addTask(TaskDTO newTask, String email) {
        newTask.setStatus("inprogress");
        Task task = mapper.mapToEntity(newTask);
        task.setUserEmail(email);
        repository.save(task);
        return mapper.mapToDTO(task);
    }

    @Override
    public TaskDTO updateTask(String email, Integer taskId, TaskDTO updatedTaskDTO) {
        Task task = repository.findById(taskId).orElse(null);
        task.setId(taskId);
        task.setUserEmail(email);
        task.setTitle(updatedTaskDTO.getTitle());
        task.setDescription(updatedTaskDTO.getDescription());
        task.setDueDate(updatedTaskDTO.getDueDate());
        task.setStatus(updatedTaskDTO.getStatus());
        task.setCategory(updatedTaskDTO.getCategory());
        repository.save(task);
        return mapper.mapToDTO(task);
    }

    @Override
    public void deleteTaskById(Integer taskId) {
        repository.deleteById(taskId);
    }
}
