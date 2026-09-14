package com.example.demo.domain;

import lombok.*;

import jakarta.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table

public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String password;


    @Builder
    public Board(Long id, String title, String content, String writer, String password) {
        this.id = id;
        this.title =  title;
        this.content = content;
        this.writer = writer;
        this.password = password;
    }

    public void update(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
}
