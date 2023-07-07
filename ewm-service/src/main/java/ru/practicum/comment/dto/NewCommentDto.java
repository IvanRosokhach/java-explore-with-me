package ru.practicum.comment.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class NewCommentDto {

    @NotBlank
    @Size(min = 1, max = 1000)
    private String text;

}
