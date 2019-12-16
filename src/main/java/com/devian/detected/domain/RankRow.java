package com.devian.detected.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@Table(name = "ranking")
public class RankRow {
    @Id
    private String uid;
    private long rank;
}
