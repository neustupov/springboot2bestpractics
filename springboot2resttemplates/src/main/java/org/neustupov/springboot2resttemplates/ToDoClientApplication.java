package org.neustupov.springboot2resttemplates;

import lombok.extern.slf4j.Slf4j;
import org.neustupov.springboot2resttemplates.client.ToDoRestClient;
import org.neustupov.springboot2resttemplates.client.domain.ToDo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class ToDoClientApplication {

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(ToDoClientApplication.class);
    app.setWebApplicationType(WebApplicationType.NONE);
    app.run(args);
  }

  @Bean
  public CommandLineRunner process(ToDoRestClient client) {
    return args -> {

      ToDo newToDo = client.insert(new ToDo("New ToDo"));
      assert newToDo != null;
      log.info("insert -> " + newToDo.toString());

      ToDo toDo = client.findById(newToDo.getId());
      assert toDo != null;
      log.info("find by id -> " + toDo.toString());

      Iterable<ToDo> toDos = client.findAll();
      assert toDos != null;
      toDos.forEach(toDoDo -> log.info("findAll -> " + toDoDo.toString()));

      ToDo completed = client.setCompleted(newToDo.getId());
      assert completed.isCompleted();
      log.info("completed -> " + completed.toString());

      client.delete(newToDo.getId());
      assert client.findById(newToDo.getId()) == null;
    };
  }
}
