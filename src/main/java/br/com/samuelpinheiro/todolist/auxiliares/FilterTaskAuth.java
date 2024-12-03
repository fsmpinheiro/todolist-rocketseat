package br.com.samuelpinheiro.todolist.auxiliares;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.samuelpinheiro.todolist.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter{
	
	@Autowired
	private UsuarioRepository userRepository; 

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException{
		
				var servletPath = request.getServletPath();
				if(servletPath.startsWith("/tasks/")) {
					
					var authorization = request.getHeader("Authorization");
					var authEncoded = authorization.substring("Basic".length( )).trim( );
					
					byte[] authDecode = Base64.getDecoder().decode(authEncoded);
					var authString = new String(authDecode);
					
					String[] credentials = authString.split(":");
					String userName = credentials[0];
					String userPasswrd = credentials[1];
					
					//user validation
					var user = this.userRepository.findByUsername(userName);
					if (user == null ) {
						response.sendError(401, "Unauthorized user");
					}else {
						//password validation
						var verification = BCrypt.verifyer().verify(userPasswrd.toCharArray(), user.getPassword() );
						if(verification.verified) {
							
							request.setAttribute("userId", user.getId());
							filterChain.doFilter(request, response);
							
						}else {
							
							response.sendError(401, "Unauthorized user");
							
						}
						
					}
					
				}
		
		
				
		
	}
}
