package com.sparta.todoparty.todo;

import com.sparta.todoparty.user.User;
import com.sparta.todoparty.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoResponseDto createTodo(TodoRequestDto todoRequestDto, User user){
        Todo todo = new Todo(todoRequestDto);
        todo.setUser(user);
        todoRepository.save(todo);
        return new TodoResponseDto(todo);
    }

    public TodoResponseDto getTodoDto(Long todoid) {
        Todo todo =  getTodo(todoid);
        return new TodoResponseDto(todo);

    }

    public  Map<UserDto, List<TodoResponseDto>>getUserTodoMap() {
        Map<UserDto, List<TodoResponseDto>> userTodoMap = new HashMap<>();

        List<Todo> todoList = todoRepository.findAll(Sort.by(Sort.Direction.DESC,"createDate"));
        // 작성일 기준 내림차순 정렬

        todoList.stream().forEach(todo-> {
            var UserDto = new UserDto(todo.getUser());
            var todoDto = new TodoResponseDto(todo);
            if(userTodoMap.containsKey(UserDto)){
                // 유저 할일목록에 항목을 추가
                    userTodoMap.get(UserDto).add(todoDto);
            }else{
                // 유저 할일목록을 새로 추가
                userTodoMap.put(UserDto,new ArrayList<>(List.of(todoDto)));
            }
        });
        return userTodoMap;
    }

    @Transactional  // 내부에 save 가 없어서 내부에서 변경점이있으면 db에 반영해줌
    public TodoResponseDto updateTodo(Long todoid,TodoRequestDto todoRequestDto, User user) {
        Todo todo = getUserTodo(todoid, user);
        todo.setTitle(todoRequestDto.getTitle());
        todo.setContent(todoRequestDto.getContent());

        return new TodoResponseDto(todo);
    }
    @Transactional
    public TodoResponseDto completeTodo(Long todoid, User user) {
        Todo todo = getUserTodo(todoid, user);
        todo.complete(); // 완료 처리

        return new TodoResponseDto(todo);
    }

    public Todo getUserTodo(Long todoid, User user) {
        Todo todo = getTodo(todoid);

        if (!user.getId().equals(todo.getUser().getId())) {
            throw new RejectedExecutionException("작성자만 수정할 수 있습니다.");
        }
        return todo;
    }

    public Todo getTodo(Long todoid) {

        return todoRepository.findById(todoid)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 할일 ID 입니다"));
    }

}
