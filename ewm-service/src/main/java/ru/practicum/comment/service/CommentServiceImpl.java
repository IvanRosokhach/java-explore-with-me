package ru.practicum.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.comment.dao.CommentRepository;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.model.Comment;
import ru.practicum.event.dao.EventRepository;
import ru.practicum.event.model.Event;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ValidationException;
import ru.practicum.user.dao.UserRepository;
import ru.practicum.user.model.User;

import java.util.List;

import static ru.practicum.Constant.*;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;

    private final EventRepository eventRepository;

    private final CommentRepository commentRepository;

    @Override
    public CommentDto saveComment(long userId, long eventId, NewCommentDto commentDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_USER, userId)));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_EVENT, eventId)));
        Comment comment = commentRepository.save(CommentMapper.createComment(user, event, commentDto));
        return CommentMapper.toCommentDto(comment);
    }

    @Override
    public CommentDto updateComment(long userId, long comId, NewCommentDto updateComment) {
        Comment comment = commentRepository.findById(comId)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_COMMENT, comId)));
        if (comment.getCreator().getId() != userId) {
            throw new ValidationException(String.format(USER_NOT_CREATOR, userId, comId));
        }
        comment.setText(updateComment.getText());
        return CommentMapper.toCommentDto(commentRepository.save(comment));
    }

    @Override
    public CommentDto getCommentById(long comId) {
        Comment comment = commentRepository.findById(comId)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_COMMENT, comId)));
        return CommentMapper.toCommentDto(comment);
    }

    @Override
    public List<CommentDto> getCommentsByEventId(long eventId, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from, size, Sort.by(Sort.Direction.DESC, "created"));
        List<Comment> allByEventId = commentRepository.findAllByEventId(eventId, pageRequest);
        return CommentMapper.listToCommentDto(allByEventId);
    }

    @Override
    public void deleteComment(long userId, long comId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_USER, userId)));
        Comment comment = commentRepository.findById(comId)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_COMMENT, comId)));
        if (comment.getCreator().getId() != userId) {
            throw new ValidationException(String.format(USER_NOT_CREATOR, userId, comId));
        }
        commentRepository.deleteById(comId);
    }

}
