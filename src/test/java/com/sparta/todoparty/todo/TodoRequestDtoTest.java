package com.sparta.todoparty.todo;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TodoRequestDtoTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeEach
    public void setUp(){
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("TodoRequestDto 테스트")
    void TodoRequestTest() {
        TodoRequestDto todoRequestDto = new TodoRequestDto("test title","test content");
        Set<ConstraintViolation<TodoRequestDto>> viloations = validator.validate(todoRequestDto);
    }


}