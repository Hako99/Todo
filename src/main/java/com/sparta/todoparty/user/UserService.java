package com.sparta.todoparty.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;

    public void signup(UserRequestDto userRequestDto){
        String username = userRequestDto.getUsername();
        String password = passwordEncoder.encode(userRequestDto.getPassword());
//        String password = userRequestDto.getPassword();

        //db 에 존재하는지 확인하기
        if(userRepository.findByUsername(username).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 유저 입니다.");
        }

        User user = new User(username,password);
        userRepository.save(user);
    }

    public void login(UserRequestDto userRequestDto) {
        String username = userRequestDto.getUsername();
        String password = userRequestDto.getPassword();

        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new IllegalArgumentException("등록된 사용자가 없습니다."));

        if(!passwordEncoder.matches(password,user.getPassword())){
                    // 암호화 안된게 앞, 된게 뒤에 들어가야함.
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
    @Transactional
    public void userUpdate(UserRequestDto userRequestDto,User user) {
        if(user.getId() == null ){
            throw new IllegalArgumentException("로그인 유저 정보가 없음");
        }
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 유저 입니다.");
        }

        User userRepo = userRepository
                .findById(user.getId())
                .orElseThrow(()->new RuntimeException("로그인 유저 정보가 없음"));

        user.userUpdate(userRequestDto,passwordEncoder);
        userRepository.save(userRepo);
    }
}
