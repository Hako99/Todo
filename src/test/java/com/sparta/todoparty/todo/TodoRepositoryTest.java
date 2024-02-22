package com.sparta.todoparty.todo;

import com.fasterxml.jackson.core.JsonFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class TodoRepositoryTest {
    
    @Autowired
    private TodoRepository todoRepository;
    
    private Todo todo;
    
    //초기화
    @BeforeEach
    public void setUp()  {

        JsonFactory Todo1;
        todo = Todo
                .builder()
                .id(1L)
                .title("test_title")
                .content("test_content")
                .createDate(LocalDateTime.now())
                .isCompleted(false)
                .build();
    }

    @Test
    @DisplayName("게시글 저장 테스트")
    public void saveTest(){
        //given

        //when
        Todo saveTodo = todoRepository.save(todo);

        //then
        assertNotNull(saveTodo.getId());

        assertEquals("test_title",saveTodo.getTitle());
        assertEquals("test_content",saveTodo.getContent());
        assertNotNull(saveTodo.getCreateDate());
        assertEquals(false,saveTodo.isCompleted());

    }
}