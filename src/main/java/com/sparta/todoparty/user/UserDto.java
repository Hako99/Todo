package com.sparta.todoparty.user;


import lombok.Getter;

@Getter
public class UserDto {
    private String username;
    public UserDto(User user){
        this.username = user.getUsername();
    }
}
