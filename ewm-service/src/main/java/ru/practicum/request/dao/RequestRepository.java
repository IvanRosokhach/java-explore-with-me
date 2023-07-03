package ru.practicum.request.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.request.model.ParticipationRequest;
import ru.practicum.request.model.RequestStatus;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<ParticipationRequest, Long> {

    boolean existsByRequesterIdAndEventId(long userId, long eventId);

    long countByEventIdAndStatus(long eventId, RequestStatus status);

    List<ParticipationRequest> findAllByRequesterId(long userId);

    List<ParticipationRequest> findAllByEventIdAndStatus(long eventId, RequestStatus status);

    List<ParticipationRequest> findAllByEventId(long eventId);

    List<ParticipationRequest> findAllByIdIn(List<Long> requestIds);

    Long countAllByEventId(Long eventId);

}
