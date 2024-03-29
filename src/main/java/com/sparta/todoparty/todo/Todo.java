package com.sparta.todoparty.todo;

import com.fasterxml.jackson.core.JsonToken;
import com.sparta.todoparty.comment.Comment;
import com.sparta.todoparty.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Column
    private boolean isCompleted;    // 작업 완료시 true 로

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "todo")
    private List<Comment> comments;


    public Todo(TodoRequestDto todoRequestDto) {
        this.title = todoRequestDto.getTitle();
        this.content = todoRequestDto.getContent();
        this.createDate = LocalDateTime.now();
        this.isCompleted = false;
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

    public void complete() {
        this.isCompleted = true;
    }

}
