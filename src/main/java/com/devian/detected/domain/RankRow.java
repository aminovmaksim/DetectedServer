package com.devian.detected.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ranking")
public class RankRow {
    @Id
    private String uid;
    private long rank;
    private String nickname;
    private long points;
}
