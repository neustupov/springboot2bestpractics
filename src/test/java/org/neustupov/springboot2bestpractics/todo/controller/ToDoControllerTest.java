package org.neustupov.springboot2bestpractics.todo.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.ArgumentMatchers.any;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.neustupov.springboot2bestpractics.Springboot2BestPracticsApplication;
import org.neustupov.springboot2bestpractics.todo.domain.ToDo;
import org.neustupov.springboot2bestpractics.todo.domain.ToDoBuilder;
import org.neustupov.springboot2bestpractics.todo.repository.CommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ToDoControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private CommonRepository<ToDo> repository;

  @Test
  @DisplayName("GET /api/todo")
  void getToDos() throws Exception {

    // Setup our mocked service
    ToDo todo = ToDoBuilder.create().withDescription("First ToDo").build();
    doReturn(Lists.newArrayList(todo)).when(repository).findAll();

    mvc.perform(get("/api/todo")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].description", is("First ToDo")));
  }

  @Test
  @DisplayName("GET /api/todo/{id}")
  void getToDoById() throws Exception {
    // Setup our mocked service
    ToDo todo = ToDoBuilder.create().withId("11-22-33").build();
    doReturn(Optional.of(todo)).when(repository).findById("11-22-33");

    mvc.perform(get("/api/todo/{id}", "11-22-33")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is("11-22-33")));
  }

  @Test
  @DisplayName("PATCH /api/todo/{id}")
  void setCompleted() throws Exception {
    // Setup our mocked service
    ToDo todo = ToDoBuilder.create().withId("11-22-33").build();
    doReturn(todo).when(repository).findById("11-22-33");

    mvc.perform(patch("/api/todo/{id}", "11-22-33")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/api/todo/11-22-33"));

  }

  @Test
  @DisplayName("POST /api/todo")
  void createToDoPost() throws Exception {
    // Setup our mocked service
    ToDo todoToPost = ToDoBuilder.create().withId("11-22-33").withDescription("POST Todo").build();
    cleanDateFields(todoToPost);
    ToDo todoToReturn = ToDoBuilder.create().withId("11-22-33").build();
    doReturn(todoToReturn).when(repository).save((ToDo) any());

    mvc.perform(post("/api/todo")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(todoToPost)))
        .andExpect(status().isCreated())
        .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/api/todo/11-22-33"));
  }

  @Test
  @DisplayName("PUT /api/todo")
  void createToDoPut() throws Exception {
    // Setup our mocked service
    ToDo todoToPost = ToDoBuilder.create().withId("11-22-33").withDescription("PUT Todo").build();
    cleanDateFields(todoToPost);
    ToDo todoToReturn = ToDoBuilder.create().withId("11-22-33").build();
    doReturn(todoToReturn).when(repository).save((ToDo) any());

    mvc.perform(put("/api/todo")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(todoToPost)))
        .andExpect(status().isCreated())
        .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/api/todo/11-22-33"));
  }

  @Test
  @DisplayName("POST /api/todo with not valid data")
  void createToDoPostNotValidData() throws Exception {
    // Setup our mocked service
    ToDo todoToPost = ToDoBuilder.create().withDescription("POST Todo").build();
    todoToPost.setId(null);
    cleanDateFields(todoToPost);

    mvc.perform(post("/api/todo")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(todoToPost)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors.[0]", is("Id is not null")));
  }

  @Test
  @DisplayName("DELETE /api/todo/{id}")
  void deleteToDo() throws Exception {
    mvc.perform(delete("/api/todo/{id}", "11-22-33")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  @DisplayName("DELETE /api/todo")
  void deleteToDo1() throws Exception {
    // Setup our mocked service
    ToDo todo = ToDoBuilder.create().withId("11-22-33").build();
    cleanDateFields(todo);

    mvc.perform(delete("/api/todo")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(todo)))
        .andExpect(status().isNoContent());
  }

  private static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void cleanDateFields(ToDo todo){
    todo.setCreated(null);
    todo.setModified(null);
  }
}