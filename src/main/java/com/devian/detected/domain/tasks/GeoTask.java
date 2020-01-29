package com.devian.detected.domain.tasks;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Getter
@Setter
@SuppressWarnings("unused")
@Table(name = "geo_tasks")
public class GeoTask extends Task {

    private float Latitude;
    private float Longitude;
}
