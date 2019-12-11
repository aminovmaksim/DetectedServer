package com.devian.detected.domain;

import com.devian.detected.utils.LevelManager;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users_stats")
public class UserStats {
    @Id
    private String uid;
    private long points;
    private int level;
    private int tags;

    public UserStats(String uid, long points, int tags) {
        this.uid = uid;
        this.points = points;
        this.level = LevelManager.getLevelByPoints(points);
        this.tags = tags;
    }

    public void setPoints(long points) {
        this.points = points;
        this.level = LevelManager.getLevelByPoints(points);
    }

    public void setTags(int tags) {
        this.tags = tags;
    }

    public String getUid() {
        return uid;
    }

    public long getPoints() {
        return points;
    }

    public int getLevel() {
        return level;
    }

    public int getTags() {
        return tags;
    }
}
