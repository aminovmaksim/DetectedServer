package com.devian.detected.repository;

import lombok.Builder;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Builder
@Component
public class Database {
    @Getter
    private final UserRepository userRepository;
    @Getter
    private final StatsRepository statsRepository;
    @Getter
    private final GeoTextTaskRepository geoTextTaskRepository;
    @Getter
    private final GeoTaskRepository geoTaskRepository;
    @Getter
    private final RankRepository rankRepository;
    @Getter
    private final AvailableTagsRepository availableTagsRepository;
    @Getter
    private final AdminsRepository adminsRepository;
    @Getter
    private final EventRepository eventRepository;

}
