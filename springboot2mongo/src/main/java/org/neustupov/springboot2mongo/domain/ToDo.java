package org.neustupov.springboot2mongo.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ToDo {

  @Id
  @NotNull(message = "Id is not null")
  String id;
  @NotNull
  @NotBlank
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
