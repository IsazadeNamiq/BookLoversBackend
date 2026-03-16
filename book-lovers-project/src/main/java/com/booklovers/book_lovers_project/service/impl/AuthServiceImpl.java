package com.booklovers.book_lovers_project.service.impl;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.booklovers.book_lovers_project.entity.RoleEntity;
import com.booklovers.book_lovers_project.entity.UserEntity;
import com.booklovers.book_lovers_project.repository.RoleRepository;
import com.booklovers.book_lovers_project.repository.UserRepository;
import com.booklovers.book_lovers_project.request.AuthRequest;
import com.booklovers.book_lovers_project.request.RegisterRequest;
import com.booklovers.book_lovers_project.response.AuthResponse;
import com.booklovers.book_lovers_project.security.JwtUtil;
import com.booklovers.book_lovers_project.service.AuthService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	@Autowired
	public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
			RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public AuthResponse login(AuthRequest request) {
		// authenticate — əgər uğursuz olarsa AuthenticationException atılacaq
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		UserEntity user = userRepository.findByUsername(request.getUsername())
				.orElseThrow(() -> new RuntimeException("User not found"));

		String token = jwtUtil.generateToken(user.getUsername());
		return new AuthResponse(token);
	}

	@Override
	public void register(RegisterRequest request) {
		if (userRepository.existsByUsername(request.getUsername())) {
			throw new RuntimeException("İstifadəçi adı artıq mövcuddur");
		}
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new RuntimeException("Email artıq qeydiyyatdan keçib");
		}

		UserEntity user = new UserEntity();
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		// Default role: ROLE_USER
		RoleEntity role = roleRepository.findByName("ROLE_USER").orElseGet(() -> {
			RoleEntity r = new RoleEntity();
			r.setName("ROLE_USER");
			return roleRepository.save(r);
		});

		user.setRoles(Collections.singleton(role));
		userRepository.save(user);
	}
}