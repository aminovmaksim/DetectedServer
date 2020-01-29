package com.devian.detected.domain;

import com.devian.detected.domain.tasks.Task;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users_stats")
@SuppressWarnings("unused")
public class UserStats implements Comparable<UserStats> {
    @Id
    private String uid;
    private long points;
    private int tags;

    public UserStats(String uid, long points, int tags) {
        this.uid = uid;
        this.points = points;
        this.tags = tags;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public void completeTask(Task task) {
        this.points += task.getReward();
        this.tags += 1;
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

    public int getTags() {
        return tags;
    }

    @Override
    public int compareTo(UserStats userStats) {
        Long a = getPoints();
        Long b = userStats.getPoints();
        return b.compareTo(a);
    }
}
