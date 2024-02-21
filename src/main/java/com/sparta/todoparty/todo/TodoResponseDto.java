package com.sparta.todoparty.todo;

import com.sparta.todoparty.CommonResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoResponseDto extends CommonResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createDate;

    public TodoResponseDto(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.createDate = todo.getCreateDate();
    }
}
