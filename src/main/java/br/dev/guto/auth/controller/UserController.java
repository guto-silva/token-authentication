package br.dev.guto.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.dev.guto.auth.model.User;
import br.dev.guto.auth.security.MyJwtTokenUtil;
import br.dev.guto.auth.security.MyToken;

@RestController
public class UserController {

	@PostMapping("/login")
	public ResponseEntity<MyToken> login(@RequestBody User user){
		if(user.getUsername().equals("guto")) {
			if(user.getPassword().equals("1234")) {
				MyToken token = new MyToken();
				String strToken = MyJwtTokenUtil.createToken(user);
				token.setToken(strToken);
				
				return ResponseEntity.ok(token);
			}
			return ResponseEntity.status(403).build();
		}
		return ResponseEntity.notFound().build();
	}
	
}
