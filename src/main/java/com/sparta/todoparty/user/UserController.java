package com.sparta.todoparty.user;

import com.sparta.todoparty.CommonResponseDto;
import com.sparta.todoparty.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

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


    //로그인
    @PostMapping("/login")
    public ResponseEntity<CommonResponseDto> login(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response){
        try {
            userService.login(userRequestDto);
        }catch (IllegalArgumentException e ){
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }

        response.setHeader(JwtUtil.AUTHORIZATION_HEADER,jwtUtil.createToken(userRequestDto.getUsername()));

        return ResponseEntity.ok().body(new CommonResponseDto("로그인 성공",HttpStatus.OK.value()));
    }

    //회원수정
    @PutMapping("/userupdate")
    public ResponseEntity<CommonResponseDto> userUpdate(@RequestBody UserRequestDto userRequestDto,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {userService.userUpdate(userRequestDto,userDetails.getUser());
        }catch (IllegalArgumentException e ){
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok().body(new CommonResponseDto("회원정보 수정 성공",HttpStatus.OK.value()));
    }


}
