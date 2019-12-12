package com.devian.detected.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private UUID tagId;
    private int reward;
    private int type;
    private float Latitude;
    private float Longitude;
    private boolean completed;
    private String executor;
    private Date completedTime;

    public static final int TYPE_MAP = 1;
    public static final int TYPE_TEXT = 2;
}
