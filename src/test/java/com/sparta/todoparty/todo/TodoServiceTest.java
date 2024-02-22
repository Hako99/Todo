package com.sparta.todoparty.todo;

import com.sparta.todoparty.user.User;
import com.sparta.todoparty.user.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class TodoServiceTest {
    @InjectMocks
    private TodoService todoService;
    @Mock
    private TodoRepository todoRepository;
    TodoResponseDto createdTodo = null;
    @BeforeEach
    public void beforEach(){
        todoRepository = Mockito.mock(TodoRepository.class);
        todoService = new TodoService(
                todoRepository
        );
    }

    @Test
    @DisplayName("게시글 만들기")
    public void createTodoTest(){
        //given
        String title = "Test 제목";
        String content = "Test 내용";

        TodoRequestDto todoRequestDto = new TodoRequestDto(
                title,
                content
        );

        User user = new User();

        //when

        TodoResponseDto todo = todoService.createTodo(
                todoRequestDto,
                user
        );

        //then
        assertEquals(title,todo.getTitle());
        assertEquals(content,todo.getContent());
        assertNotNull(todo.getCreateDate());
        assertNotNull(todo.getUserDto());
        assertEquals(false,todo.getIsCompleted());
    }


    @Test
    @DisplayName("단일 조회 테스트")
    public void getTodoDtoTest(){
        //given
        Long todoid = 1L;

        //when
        TodoResponseDto todo = todoService.getTodoDto(
                todoid
        );

        //then
        assertNotNull(todo.getTitle());
        assertNotNull(todo.getContent());
        assertNotNull(todo.getCreateDate());
        assertNotNull(todo.getUserDto());
        assertEquals(false,todo.getIsCompleted());
    }


    @Test
    @DisplayName("모든 일정 조회 테스트")
    public void getUserTodoMapTest(){
        //given
        //when
        Map<UserDto, List<TodoResponseDto>> userTodoMap = todoService.getUserTodoMap();

        //then
//        Long createdTodoId = this.createdTodo.getId();
//        TodoResponseDto foundTodo = userTodoMap.stream()
//                .filter(todo -> todo.getId().equals(createdTodoId))
//                .findFirst()
//                .orElse(null);

    }




















}

