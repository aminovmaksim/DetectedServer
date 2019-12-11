package com.devian.detected.repository;

import lombok.Builder;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Builder
@Component
public class Database {
    @Getter
    private final UserRepository userRepository;
}
