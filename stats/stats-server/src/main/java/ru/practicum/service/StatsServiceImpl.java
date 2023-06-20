package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.EndpointHitDto;
import ru.practicum.ViewStatsDto;
import ru.practicum.model.Stats;
import ru.practicum.model.ViewStats;
import ru.practicum.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;

    @Override
    public EndpointHitDto save(EndpointHitDto endpointHitDto) {
        Stats saved = statsRepository.save(StatsMapper.toStats(endpointHitDto));
        return StatsMapper.toEndpointHitDto(saved);
    }

    @Override
    public List<ViewStatsDto> get(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        List<ViewStats> stats;
        if (unique) {
            if (uris == null) {
                stats = statsRepository.getStatsUniqueWithOutUris(start, end);
            } else {
                stats = statsRepository.getStatsUnique(start, end, uris);
            }
        } else {
            if (uris == null) {
                stats = statsRepository.getStatsWithOutUris(start, end);
            } else {
                stats = statsRepository.getStats(start, end, uris);
            }
        }
        return StatsMapper.toViewStatsDtos(stats);
    }

}
