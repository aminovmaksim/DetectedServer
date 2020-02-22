package com.devian.detected.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "admins")
public class Admin {
    @Id
    String uid;
    String name;
    String email;
}
