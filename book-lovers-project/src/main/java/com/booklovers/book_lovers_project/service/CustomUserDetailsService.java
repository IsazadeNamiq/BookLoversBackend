//package com.booklovers.book_lovers_project.service;
//
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.booklovers.book_lovers_project.entity.UserEntity;
//import com.booklovers.book_lovers_project.repository.UserRepository;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//	private final UserRepository userRepository;
//
//	@Autowired
//	public CustomUserDetailsService(UserRepository userRepository) {
//		this.userRepository = userRepository;
//	}
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		UserEntity user = userRepository.findByUsername(username)
//				.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
//
//		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
//				user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName()))
//						.collect(Collectors.toList()));
//	}
//}