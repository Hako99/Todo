package com.sparta.todoparty.comment;

import com.sparta.todoparty.todo.Todo;
import com.sparta.todoparty.todo.TodoService;
import com.sparta.todoparty.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TodoService todoService;

    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, User user){
        //todo 에 댓글이 달리니 todo 가 있는지 체크
        Todo todo = todoService.getTodo(commentRequestDto.getTodoId());


        Comment Comment = new Comment(commentRequestDto);
        Comment.setUser(user);
        Comment.setTodo(todo);

        commentRepository.save(Comment);

        return new CommentResponseDto(Comment);
    }

    // 댓글 수정
    @Transactional  // 내부에 save 가 없어서 내부에서 변경점이있으면 db에 반영해줌
    public CommentResponseDto updateComment(Long commentid,CommentRequestDto commentRequestDto, User user) {
        Comment Comment = getUserComment(commentid, user);
        Comment.setText(commentRequestDto.getText());

        return new CommentResponseDto(Comment);
    }

    //삭제하기
    public void deleteComment(Long commentId, User user){
        Comment comment = getUserComment(commentId,user);

        commentRepository.delete(comment);
    }

    private Comment getUserComment(Long commentId, User user) {
        Comment Comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글 ID 입니다"));

        if(!user.getId().equals(Comment.getUser().getId())){
            throw new RejectedExecutionException("작성자만 수정할 수 있습니다.");
        }
        return Comment;
    }
}
