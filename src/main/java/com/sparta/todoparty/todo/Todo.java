package com.sparta.todoparty.todo;

import com.sparta.todoparty.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Todo(TodoRequestDto todoRequestDto) {
        this.title = todoRequestDto.getTitle();
        this.content = todoRequestDto.getContent();
        this.createDate = LocalDateTime.now();
    }

    //연관관계 메서드
    public void setUser(User user){
        this.user = user;
    }

    //서비스 메서드
    public void setTitle(String title){
        this.title = title;
    }
    public void setContent(String content){
        this.content = content;
    }
}
