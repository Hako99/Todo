package com.sparta.todoparty.todo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TodoTest {

    TodoRepository todoRepository;

    @Test
    @DisplayName("Todo 엔티티 저장 테스트")
    public void test01(){
        //given
        Todo todo = new Todo();
        todo.setTitle("제목");
        todo.setContent("내용");

        //when
        //then
        Assertions.assertEquals("제목",todo.getTitle());
        Assertions.assertEquals("내용",todo.getContent());
    }



}