package ru.practicum.comment.service;

import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.model.Comment;
import ru.practicum.event.model.Event;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CommentMapper {

    private CommentMapper() {
    }

    public static CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .userId(comment.getCreator().getId())
                .eventId(comment.getEvent().getId())
                .created(comment.getCreated())
                .build();
    }

    public static List<CommentDto> listToCommentDto(List<Comment> comments) {
        return comments.stream().map(CommentMapper::toCommentDto).collect(Collectors.toList());
    }

    public static Comment createComment(User user, Event event, NewCommentDto commentDto) {
        return Comment.builder()
                .text(commentDto.getText())
                .creator(user)
                .event(event)
                .created(LocalDateTime.now())
                .build();
    }

}
