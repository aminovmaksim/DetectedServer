package com.devian.detected.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    private String uid;
    private String displayName;
    private String email;
    private Date lastLogin;

    public User(String uid, String displayName, String email) {
        this.uid = uid;
        this.displayName = displayName;
        this.email = email;
        this.lastLogin = null;
    }

}
