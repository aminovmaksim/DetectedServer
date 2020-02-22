package com.devian.detected.domain.tasks;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "available_tags")
public class Tag {
    @Id
    private String tagId;
    private int type;
    private float latitude;
    private float longitude;
}
