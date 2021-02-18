package org.neustupov.springboot2bestpractics.todo.client.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ToDo {

  String id;
  String description;
  LocalDateTime created;
  LocalDateTime modified;
  boolean completed;

  public ToDo() {
    LocalDateTime date = LocalDateTime.now();
    this.id = UUID.randomUUID().toString();
    this.created = date;
    this.modified = date;
  }

  public ToDo(String description) {
    this();
    this.description = description;
  }
}
