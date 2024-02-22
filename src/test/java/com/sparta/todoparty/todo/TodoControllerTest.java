package com.sparta.todoparty.todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.todoparty.configuration.WebSecurityConfig;
import com.sparta.todoparty.user.User;
import com.sparta.todoparty.user.UserController;
import com.sparta.todoparty.user.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(
        controllers = {TodoController.class},
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class
                )
        }
)
class TodoControllerTest {


    @InjectMocks
    private TodoController todoController;
    @MockBean
    private TodoService todoService;
    private MockMvc mockMvc;
    private Principal mockPrincipal;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(todoController).build();
    }


    private void mockUserSetup() {
        // Mock 테스트 유져 생성
        String username = "sollertia4351";
        String password = "robbie1234";
        User testUser = new User(username, password);
        UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());
    }


    @Test
    @DisplayName("게시글 작성")
    void postTodoTest() throws Exception {
        //given

        this.mockUserSetup();
        String title = "test title";
        String content = "test content";
        TodoRequestDto todoRequestDto = new TodoRequestDto(
                title,
                content
        );

        String postInfo = objectMapper.writeValueAsString(todoRequestDto);
        //when
        mockMvc.perform(post("/api/todos")
                        .content(postInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

}