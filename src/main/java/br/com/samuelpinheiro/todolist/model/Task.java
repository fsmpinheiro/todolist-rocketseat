package br.com.samuelpinheiro.todolist.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name="tb_tasks")
public class Task {

	@Id
	@GeneratedValue(generator="UUID")
	private UUID id;
	private String description;
	
	@Column(length=50)
	private String title;
	private LocalDateTime startAt;
	private LocalDateTime endAt;
	private String priority;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	private UUID userId;
	
	public void setTitle(String title) throws Exception{
		if (title.length() > 50) {
			throw new Exception("The Title should contain a maximum of 50 characters");
		}
		this.title = title;
	}
}
