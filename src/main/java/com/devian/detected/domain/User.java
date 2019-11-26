package com.devian.detected.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private UUID uuid;
    private String login;
    private String email;

    public User(UUID uuid, String login, String email) {
        this.uuid = uuid;
        this.login = login;
        this.email = email;
    }
}
