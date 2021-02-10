package org.neustupov.springboot2bestpractics.todo.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.neustupov.springboot2bestpractics.todo.domain.ToDo;
import org.neustupov.springboot2bestpractics.todo.domain.ToDoBuilder;
import org.neustupov.springboot2bestpractics.todo.repository.CommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ToDoControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private CommonRepository repository;

  @Test
  @DisplayName("GET /api/todo")
  void getToDos() throws Exception {

    // Setup our mocked service
    ToDo todo = ToDoBuilder.create().withDescription("First ToDo").build();
    doReturn(Lists.newArrayList(todo)).when(repository).findAll();

    mvc.perform(get("/api/todo")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        //.andExpect(header().string(HttpHeaders.LOCATION, "/rest/widgets"))
        .andExpect(jsonPath("$", hasSize(1)));
  }

  @Test
  void getToDoById() {
  }

  @Test
  void setCompleted() {
  }

  @Test
  void createToDo() {
  }

  @Test
  void deleteToDo() {
  }

  @Test
  void deleteToDo1() {
  }

  @Test
  void handleException() {
  }
}