package com.sparta.todoparty.todo;

import lombok.Getter;

@Getter
public class TodoResponseDto {
    private Long id;
    private String title;
    private String content;

    public TodoResponseDto(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.content = todo.getContent();
    }
}
