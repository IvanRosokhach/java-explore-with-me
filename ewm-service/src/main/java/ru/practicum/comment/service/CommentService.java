package ru.practicum.comment.service;

import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;

import java.util.List;

public interface CommentService {

    CommentDto saveComment(long userId, long eventId, NewCommentDto commentDto);

    CommentDto updateComment(long userId, long comId, NewCommentDto updateComment);

    CommentDto getCommentById(long comId);

    List<CommentDto> getCommentsByEventId(long eventId, int from, int size);

    void deleteComment(long userId, long comId);

}
