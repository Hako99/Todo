package com.sparta.todoparty.user;


import com.sparta.todoparty.todo.Todo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    public User (String username,String password){
        this.username = username;
        this.password = password;
    }

    public void userUpdate(UserRequestDto userRequestDto, PasswordEncoder passwordEncoder) {
        if (userRequestDto.getPassword() != null) {
            this.password = passwordEncoder.encode(userRequestDto.getPassword());
        }
        if (userRequestDto.getUsername() != null) {
            this.username = userRequestDto.getUsername();
        }
    }
}
