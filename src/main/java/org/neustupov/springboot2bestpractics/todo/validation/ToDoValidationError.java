package org.neustupov.springboot2bestpractics.todo.validation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class ToDoValidationError {

  @Getter
  @JsonInclude(Include.NON_EMPTY)
  private List<String> errors = new ArrayList<>();

  @Getter
  private final String errorMessage;

  public ToDoValidationError(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public void addValidationError(String error){
    errors.add(error);
  }
}
