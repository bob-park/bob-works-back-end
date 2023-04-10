package org.bobpark.authorizationservice.domain.user.service.impl;

import java.util.Collections;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.bobpark.authorizationservice.domain.user.entity.User;
import org.bobpark.authorizationservice.domain.user.model.UserDetailsImpl;
import org.bobpark.authorizationservice.domain.user.repository.UserRepository;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user =
            userRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new UserDetailsImpl(user.getUserId(), user.getEncryptPassword(),
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
