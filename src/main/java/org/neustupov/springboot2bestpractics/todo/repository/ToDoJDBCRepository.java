package org.neustupov.springboot2bestpractics.todo.repository;

import java.util.Collection;
import org.neustupov.springboot2bestpractics.todo.domain.ToDo;

public class ToDoJDBCRepository implements CommonRepository<ToDo> {

  @Override
  public ToDo save(ToDo domain) {
    return null;
  }

  @Override
  public Iterable<ToDo> save(Collection<ToDo> domains) {
    return null;
  }

  @Override
  public void delete(ToDo domain) {

  }

  @Override
  public ToDo findById(String id) {
    return null;
  }

  @Override
  public Iterable<ToDo> findAll() {
    return null;
  }
}
