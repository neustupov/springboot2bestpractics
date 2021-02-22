package org.neustupov.springboot2datajpa.todo.repository;

import org.neustupov.springboot2datajpa.todo.domain.ToDo;
import org.springframework.data.repository.CrudRepository;

public interface ToDoRepository extends CrudRepository<ToDo, String> {

}
