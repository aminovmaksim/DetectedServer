package com.devian.detected.repository;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public class Database {
    @Getter
    private UserRepository userRepository;
}
