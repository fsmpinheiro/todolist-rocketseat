package br.com.samuelpinheiro.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.samuelpinheiro.todolist.model.Usuario;
import br.com.samuelpinheiro.todolist.repository.UsuarioRepository;

@RestController
@RequestMapping("/users")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository userRepository;

	@PostMapping("/")
	public ResponseEntity createUsuario(@RequestBody Usuario usuario) {
		var user = this.userRepository.findByUsername(usuario.getName());
		
		if(user != null ) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("username already exists");
		}
		
		var passwordHashred = BCrypt.withDefaults().hashToString(12, usuario.getPassword().toCharArray());
		usuario.setPassword(passwordHashred);
		
		var newUser = this.userRepository.save(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
	}
}
