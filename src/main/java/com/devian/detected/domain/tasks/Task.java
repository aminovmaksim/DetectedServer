package com.devian.detected.domain.tasks;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
@SuppressWarnings("unused")
public class Task {

    @Id
    private String tagId;
    private int reward;
    private boolean completed;
    private String executor;
    private Date completedTime;
}
