package com.sparta.todoparty;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

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
}
