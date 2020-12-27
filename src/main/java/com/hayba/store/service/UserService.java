package com.hayba.store.service;

import com.hayba.store.repository.UserRepository;
import com.hayba.store.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    if (!userRepository.findByUsername(s).isPresent())
      throw new UsernameNotFoundException(String.format("User does not exist with username: ", s));
    return userRepository
        .findByUsername(s)
        .map(
            u ->
                new org.springframework.security.core.userdetails.User(
                    u.getUsername(),
                    u.getPassword(),
                    true,
                    true,
                    true,
                    true,
                    List.of(new SimpleGrantedAuthority(u.getRole()))))
        .get();
  }

  public User createUser(User user) {
    if (userRepository.findByUsername(user.getUsername()).isPresent())
      throw new IllegalStateException("User already exists with username: " + user.getUsername());
    return userRepository.save(
        new User(
            user.getUsername(), passwordEncoder.encode(user.getPassword())));
  }
}
