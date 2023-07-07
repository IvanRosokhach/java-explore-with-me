package ru.practicum.comment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.event.model.Event;
import ru.practicum.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "text", length = 1000)
    private String text;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User creator;

    @JoinColumn(name = "event_id")
    @ManyToOne
    private Event event;

    @Column(name = "created")
    private LocalDateTime created;

}
