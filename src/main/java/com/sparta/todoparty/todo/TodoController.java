package com.sparta.todoparty.todo;

import com.sparta.todoparty.CommonResponseDto;
import com.sparta.todoparty.user.User;
import com.sparta.todoparty.user.UserDetailsImpl;
import com.sparta.todoparty.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    // 작성하기
    @PostMapping
    public ResponseEntity<TodoResponseDto> postTodo(@RequestBody TodoRequestDto todoRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        TodoResponseDto responseDto = todoService.createTodo(todoRequestDto, userDetails.getUser());
        return ResponseEntity.status(201).body(responseDto);
    }

    //단건 조회
    @GetMapping("/{todoid}")
    public ResponseEntity<CommonResponseDto> getTodo(@PathVariable Long todoId){
        try {
            TodoResponseDto responseDto = todoService.getTodoDto(todoId);
            return ResponseEntity.ok().body(responseDto);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
    }

    //회원의 전체 글 조회
    @GetMapping
    public ResponseEntity <List<TodoListResponseDto>> getTodoList(){
        List<TodoListResponseDto> response = new ArrayList<>();

        Map<UserDto, List<TodoResponseDto>> responseDtoMap = todoService.getUserTodoMap();

        responseDtoMap.forEach((key, value) -> response.add(new TodoListResponseDto(key, value)));

        return ResponseEntity.ok().body(response);
    }


    //put 은 전체 수정할때 많이 쓰고
    // 패치는 일부 수정시에 많이 쓰임
    // 일단 put으로
    @PutMapping("/{todoid}")
    public ResponseEntity<CommonResponseDto> putTodo(@PathVariable Long todoid,@RequestBody TodoRequestDto todoRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        try{
            TodoResponseDto responseDto = todoService.updateTodo(todoid,todoRequestDto, userDetails.getUser());
            return ResponseEntity.ok().body(responseDto);
        }catch (RejectedExecutionException | IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(new CommonResponseDto(ex.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PatchMapping("/{todoid}/complete")
    public ResponseEntity<CommonResponseDto> patchTodo(@PathVariable Long todoid, @AuthenticationPrincipal UserDetailsImpl userDetails){
        try{
            TodoResponseDto responseDto = todoService.completeTodo(todoid, userDetails.getUser());
            return ResponseEntity.ok().body(responseDto);
        }catch (RejectedExecutionException | IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(new CommonResponseDto(ex.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
    }




}
