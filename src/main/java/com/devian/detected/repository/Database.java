package com.devian.detected.repository;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class Database {
    @Getter
    private UserRepository userRepository;
}
