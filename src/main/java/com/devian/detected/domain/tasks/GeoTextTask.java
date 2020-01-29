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
@Table(name = "geo_text_tasks")
public class GeoTextTask extends Task {

    private String title;
    private String description;
    private String imgUrl;
}
