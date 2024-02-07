package com.sparta.todoparty;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto> signup(@Valid @RequestBody UserRequestDto userRequestDto){
        try{
            userService.signup(userRequestDto);
        }catch (IllegalArgumentException exception){
            return ResponseEntity.badRequest().body(new CommonResponseDto("중복된 username 입니다",HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.status(201).body(new CommonResponseDto("회원가입 성공",201));
        // return ResponseEntity.status(HttpStatus.CREATED.value())
        // .body(new CommonResponseDto("회원가입 성공", HttpStatus.CREATED.value()));
        // 이렇게 해도 됨.
    }


}
