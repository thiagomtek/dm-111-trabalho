package com.edu.inatel.br.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.edu.inatel.br.dto.LoginRequest;
import com.edu.inatel.br.dto.UserDTO;
import com.edu.inatel.br.security.JwtUtil;

@Service
public class AuthService {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${user.service.url}")
	private String userServiceUrl;

	public String authenticate(LoginRequest loginRequest) {

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		try {
			String url = userServiceUrl + "/internal/email/" + loginRequest.getEmail();
			UserDTO user = restTemplate.getForObject(url, UserDTO.class);

			if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
				return jwtUtil.generateToken(user.getEmail());
			} else {
				throw new RuntimeException("Credenciais inválidas");
			}
		} catch (HttpClientErrorException.NotFound ex) {
			throw new RuntimeException("Credenciais inválidas");
		} catch (Exception e) {
			throw new RuntimeException("Erro ao autenticar: " + e.getMessage());
		}
	}

}