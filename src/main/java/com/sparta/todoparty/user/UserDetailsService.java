package com.sparta.todoparty.user;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsService {
    // 실제 회원가입과 관련있는게 아닌 유저 디테일과 관련된것만 모음
    private final UserRepository userRepository;
    public UserDetails getUserDetails(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Not Found" + username));
        return new UserDetailsImpl(user);

    }

}
