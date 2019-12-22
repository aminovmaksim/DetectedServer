package com.devian.detected.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String tagId;
    private int reward;
    private int type;
    private float Latitude;
    private float Longitude;
    private boolean completed;
    private String executor;
    private Date completedTime;

    private String title;
    private String description;
    private String imgUrl;

    public static final int TYPE_MAP = 1;
    public static final int TYPE_TEXT = 2;
}
