package com.example.uiju.controller;

import com.example.uiju.dto.ResponseDTO;
import com.example.uiju.dto.TodoDTO;
import com.example.uiju.model.TodoEntity;
import com.example.uiju.persistence.TodoRepository;
import com.example.uiju.service.TodoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.BDDAssumptions.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerTest {

    @Autowired
    private TodoController controller;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    private WebApplicationContext ctx;


    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
    }
    @Test
    void createTodo() throws Exception{
        String body = mapper.writeValueAsString(
                TodoDTO.builder().title("CreateText").build()
        );
        mockMvc.perform(post("/todo")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    void getTodos() throws Exception{
        mockMvc.perform(get("/todo")
                        .param("userId", "temporary-user")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

    }

    @Test
    @DisplayName("업데이트 테스트")
    void updateTodo() throws Exception {
        controller.createTodo(new TodoDTO().builder().title("New Text").build());
        ResponseEntity<?> responseEntity = controller.getTodos("temporary-user");
        ResponseDTO responseDTO= (ResponseDTO)responseEntity.getBody();
        List<TodoDTO> data = responseDTO.getData();

        String body = mapper.writeValueAsString(
                TodoDTO.builder().id(data.get(0).getId()).title("ModifiedTitle").build()
        );
        mockMvc.perform(put("/todo")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void deleteTodo() throws Exception{
        controller.createTodo(new TodoDTO().builder().title("New Text2").build());
        ResponseEntity<?> responseEntity = controller.getTodos("temporary-user");
        ResponseDTO responseDTO= (ResponseDTO)responseEntity.getBody();
        List<TodoDTO> data = responseDTO.getData();
        if(data.size() > 0){
            String body = mapper.writeValueAsString(
                    TodoDTO.builder().id(data.get(0).getId()).build()
            );
            mockMvc.perform(delete("/todo")
                            .content(body)
                            .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
                    )
                    .andExpect(status().isOk())
                    .andDo(print());
        }

    }
}