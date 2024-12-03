package br.com.samuelpinheiro.todolist.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.samuelpinheiro.todolist.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID>{
	Usuario findByUsername(String username);
}
