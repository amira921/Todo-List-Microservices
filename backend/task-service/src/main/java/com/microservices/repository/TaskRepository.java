package com.microservices.repository;

import com.microservices.entity.Task;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Query("SELECT t FROM Task t WHERE t.userEmail = :email")
    List<Task> findAllByUserEmail(String email);

    @Query("SELECT t FROM Task t WHERE t.userEmail = :email AND t.category = :category")
    List<Task> findByUserEmailAndCategory(String email, String category);

    @Query("SELECT t FROM Task t WHERE t.userEmail = :email AND t.dueDate >= :startOfDay AND t.dueDate <= :endOfDay")
    List<Task> findByUserEmailAndDate(String email, LocalDateTime startOfDay, LocalDateTime endOfDay);

    @Query("SELECT t FROM Task t WHERE t.userEmail = :email AND t.status = :status AND t.dueDate = :now")
    List<Task> findByUserEmailAndStatusAndDate(String email, String status, LocalDateTime now);

    @Query("SELECT t FROM Task t WHERE t.userEmail = :email AND t.status = :status")
    List<Task> findByUserEmailAndStatus(String email, String status);
}

