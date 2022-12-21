package com.example.uiju.controller;

import com.example.uiju.dto.ResponseDTO;
import com.example.uiju.dto.TodoDTO;
import com.example.uiju.model.TodoEntity;
import com.example.uiju.persistence.TodoRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TodoControllerTest {

    private TodoController controller = new TodoController();

    @BeforeAll
    void setUp(){
        String temporaryUserId = "temporary-user";
        TodoDTO dto = new TodoDTO();
        dto.setTitle("뉴 텍스트");
        controller.createTodo(dto);
    }

    @Test
    void createTodo() {
        String title = "뉴 텍스트2";
        TodoDTO dto = new TodoDTO();
        dto.setTitle(title);
        ResponseEntity<?> responseEntity = controller.createTodo(dto);
        ResponseDTO<TodoDTO> dtos = (ResponseDTO<TodoDTO>)responseEntity.getBody();
        System.out.println(dtos);
        List<TodoDTO> dtoList = dtos.getData();
        System.out.println(dtoList);
    }

    @Test
    void getTodos() {

    }

    @Test
    @DisplayName("@Query 외의 어노테이션 없을 경우 오류 발생")
    void updateTodo() {

    }

    @Test
    void deleteTodo() {
    }
}