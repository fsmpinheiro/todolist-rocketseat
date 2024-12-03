package br.com.samuelpinheiro.todolist.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.samuelpinheiro.todolist.model.Task;

public interface TaskRepository extends JpaRepository<Task, UUID>{
	List<Task> findByUserId(UUID userId);
	Task findByIdAndUserId(UUID id, UUID userId);
}
