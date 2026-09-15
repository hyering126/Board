package com.example.demo.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public enum BoardType {
    Board("게시판");

    private String value;

}
