package br.com.samuelpinheiro.todolist.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.samuelpinheiro.todolist.auxiliares.Utilitario;
import br.com.samuelpinheiro.todolist.model.Task;
import br.com.samuelpinheiro.todolist.repository.TaskRepository;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	private TaskRepository taskRepository;
	
	@PostMapping("/")
	public ResponseEntity createTask(@RequestBody Task task, HttpServletRequest request) {
		var userId = request.getAttribute("userId");
		task.setUserId((UUID) userId);
		
		var currentDate = LocalDateTime.now();
		
		if(currentDate.isAfter(task.getStartAt() ) ){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Start date must be after current date");
		}
		
		if(currentDate.isAfter(task.getEndAt() ) ) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("End date must be greater than current date");
		}
		
		if(task.getStartAt().isAfter(task.getStartAt() ) ){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Start date must be less than current date");
		}
		
		var newTask = this.taskRepository.save(task);
		return ResponseEntity.status(HttpStatus.OK).body(newTask);
	}
	
	@GetMapping("/")
	public List<Task> listByUser(HttpServletRequest request){
		var userId = request.getAttribute("userId");
		var taskList = this.taskRepository.findByUserId((UUID) userId);
		return taskList;
	}
	
	@PutMapping("/{id}")
	public ResponseEntity updateTask(@RequestBody Task task, @PathVariable UUID taskId, HttpServletRequest request) {
		var tempTask = this.taskRepository.findById(taskId).orElse(null);		
		
		if(tempTask == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Task not found");
		}
		
		var userId = request.getAttribute("userId");
		
		if(!task.getUserId().equals(userId)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not have ownership of this task");
		}

		Utilitario.copyNonNullProperties(task, tempTask);
		var updatedTask = this.taskRepository.save(tempTask);
				
		return ResponseEntity.ok().body(updatedTask);
	}
}
