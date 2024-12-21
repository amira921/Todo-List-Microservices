package com.microservices.mapper;

import com.microservices.dto.TaskDTO;
import com.microservices.entity.Task;
import org.mapstruct.Mapper;

@Mapper
public interface TaskMapper {
    TaskDTO mapToDTO(Task entity);
    Task mapToEntity(TaskDTO dto);
}
