package com.sparta.todoparty.comment;

import com.sparta.todoparty.CommonResponseDto;
import com.sparta.todoparty.todo.TodoListResponseDto;
import com.sparta.todoparty.todo.TodoRequestDto;
import com.sparta.todoparty.todo.TodoResponseDto;
import com.sparta.todoparty.todo.TodoService;
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
public class CommentController {

    private final CommentService commentService;

    // 작성하기
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        CommentResponseDto responseDto = commentService.createComment(commentRequestDto, userDetails.getUser());
        return ResponseEntity.status(201).body(responseDto);
    }

    // 수정
    @PutMapping("/{commentid}")
    public ResponseEntity<CommonResponseDto> updateComment(@PathVariable Long commentid,@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        try{
            CommentResponseDto responseDto = commentService.updateComment(commentid,commentRequestDto, userDetails.getUser());
            return ResponseEntity.ok().body(responseDto);
        }catch (RejectedExecutionException | IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(new CommonResponseDto(ex.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
    }

    //삭제
    @DeleteMapping("/{commentid}")
    public ResponseEntity<CommonResponseDto> deleteComment(@PathVariable Long commentid, @AuthenticationPrincipal UserDetailsImpl userDetails){
        try{
            commentService.deleteComment(commentid, userDetails.getUser());
            return ResponseEntity.ok().body(new CommonResponseDto("정상적으로 삭제되었습니다.",HttpStatus.OK.value()));
        }catch (RejectedExecutionException | IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(new CommonResponseDto(ex.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
    }




}
