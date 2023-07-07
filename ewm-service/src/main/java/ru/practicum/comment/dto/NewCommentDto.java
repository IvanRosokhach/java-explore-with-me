package ru.practicum.comment.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NewCommentDto {

    @NotBlank
    private String text;

}
