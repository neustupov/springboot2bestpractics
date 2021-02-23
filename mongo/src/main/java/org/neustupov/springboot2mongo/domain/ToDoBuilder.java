package org.neustupov.springboot2mongo.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ToDoBuilder {

  static ToDoBuilder instance = new ToDoBuilder();
  String id = null;
  String description = "";

  public static ToDoBuilder create() {
    return instance;
  }

  public ToDoBuilder withDescription(String description) {
    this.description = description;
    return instance;
  }

  public ToDoBuilder withId(String id) {
    this.id = id;
    return instance;
  }

  public ToDo build() {
    ToDo result = new ToDo(this.description);
    if (id != null) {
      result.setId(id);
    }
    return result;
  }

}
