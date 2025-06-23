package dev.ryanlioy.bookloger.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String username;
    @OneToMany
    List<BookEntity> currentlyReading;
    @OneToMany
    List<BookEntity> finished;
    @OneToMany
    List<BookEntity> favorites;
    @OneToMany
    List<BookEntity> readList;

    public UserEntity() {
        currentlyReading = new ArrayList<>();
        finished = new ArrayList<>();
        favorites = new ArrayList<>();
        readList = new ArrayList<>();
    }
}
