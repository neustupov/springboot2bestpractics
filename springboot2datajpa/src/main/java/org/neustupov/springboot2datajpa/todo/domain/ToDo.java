package org.neustupov.springboot2datajpa.todo.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ToDo {

  @NotNull
  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  String id;
  @NotNull
  @NotBlank
  String description;
  @Column(updatable = false)
  LocalDateTime created;
  LocalDateTime modified;
  boolean completed;

  public ToDo(String description) {
    this();
    this.description = description;
  }

  @PrePersist
  void onCreate() {
    this.setCreated(LocalDateTime.now());
    this.setModified(LocalDateTime.now());
  }

  @PreUpdate
  void onUpdate() {
    this.setModified(LocalDateTime.now());
  }
}
