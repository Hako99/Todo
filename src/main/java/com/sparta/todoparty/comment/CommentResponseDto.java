package com.sparta.todoparty.comment;

import com.sparta.todoparty.CommonResponseDto;
import com.sparta.todoparty.todo.Todo;
import com.sparta.todoparty.user.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDto extends CommonResponseDto {
    private Long id;
    private String text;
    private LocalDateTime createDate;
    private UserDto userDto;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.createDate = comment.getCreateDate();
        this.userDto = new UserDto(comment.getUser());
    }
}
