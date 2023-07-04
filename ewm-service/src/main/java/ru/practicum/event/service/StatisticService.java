package ru.practicum.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.EndpointHitDto;
import ru.practicum.StatsClient;
import ru.practicum.ViewStatsDto;
import ru.practicum.event.model.Event;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.Constant.SERVICE_NAME;
import static ru.practicum.Constant.formatter;

@Service
@RequiredArgsConstructor
public class StatisticService {

    private final StatsClient statsClient;

    public EndpointHitDto saveStats(HttpServletRequest request) {
        EndpointHitDto hitDto = EndpointHitDto.builder()
                .app(SERVICE_NAME)
                .ip(request.getRemoteAddr())
                .uri(request.getRequestURI())
                .timestamp(LocalDateTime.now().format(formatter)).build();
        return statsClient.saveStats(hitDto);
    }

    public long getViews(Event event) {
        long views;
        List<String> uri = List.of("/events/" + event.getId());
        List<ViewStatsDto> viewStats = statsClient.getStats(
                event.getCreatedOn().format(formatter),
                LocalDateTime.now().format(formatter),
                uri,
                true);
        if (viewStats.isEmpty()) {
            return 0;
        } else {
            views = viewStats.get(0).getHits();
        }
        return views;
    }

}
