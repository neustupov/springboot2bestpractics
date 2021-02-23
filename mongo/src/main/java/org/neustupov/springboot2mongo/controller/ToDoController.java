package org.neustupov.springboot2mongo.controller;

import java.net.URI;
import java.util.Optional;
import javax.validation.Valid;
import org.neustupov.springboot2mongo.domain.ToDo;
import org.neustupov.springboot2mongo.domain.ToDoBuilder;
import org.neustupov.springboot2mongo.repository.MongoRepo;
import org.neustupov.springboot2mongo.validation.ToDoValidationError;
import org.neustupov.springboot2mongo.validation.ToDoValidationErrorBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class ToDoController {

  private MongoRepo repository;

  public ToDoController(MongoRepo repository) {
    this.repository = repository;
  }

  @GetMapping("/todo")
  public ResponseEntity<Iterable<ToDo>> getToDos() {
    return ResponseEntity.ok(repository.findAll());
  }

  @GetMapping("/todo/{id}")
  public ResponseEntity<ToDo> getToDoById(@PathVariable String id) {
    Optional<ToDo> todo = repository.findById(id);
    if(todo.isPresent()){
      return ResponseEntity.ok(todo.get());
    }
    return ResponseEntity.notFound().build();
  }

  @PatchMapping("/todo/{id}")
  public ResponseEntity<ToDo> setCompleted(@PathVariable String id) {
    Optional<ToDo> todo = repository.findById(id);
    if(!todo.isPresent()){
      return ResponseEntity.notFound().build();
    }
    ToDo result = todo.get();
    result.setCompleted(true);
    repository.save(result);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(result.getId())
        .toUri();
    return ResponseEntity.ok().header("Location", location.toString()).build();
  }

  @RequestMapping(value = "/todo", method = {RequestMethod.POST, RequestMethod.PUT})
  public ResponseEntity<?> createToDo(@Valid @RequestBody ToDo todo, Errors errors) {
    if (errors.hasErrors()) {
      return ResponseEntity.badRequest()
          .body(ToDoValidationErrorBuilder.fromBindingsErrors(errors));
    }
    ToDo result = repository.save(todo);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(result.getId()).toUri();
    return ResponseEntity.created(location).build();
  }

  @DeleteMapping("/todo/{id}")
  public ResponseEntity<ToDo> deleteToDo(@PathVariable String id) {
    repository.delete(ToDoBuilder.create().withId(id).build());
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/todo")
  public ResponseEntity<ToDo> deleteToDo(@RequestBody ToDo todo) {
    repository.delete(todo);
    return ResponseEntity.noContent().build();
  }

  @ExceptionHandler
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ToDoValidationError handleException(Exception exception) {
    return new ToDoValidationError(exception.getMessage());
  }
}
