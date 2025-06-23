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
    List<BookEntity> currentlyReading = new ArrayList<>();
    @OneToMany
    List<BookEntity> finished = new ArrayList<>();
    @OneToMany
    List<BookEntity> favorites = new ArrayList<>();
    @OneToMany
    List<BookEntity> readList = new ArrayList<>();
}
