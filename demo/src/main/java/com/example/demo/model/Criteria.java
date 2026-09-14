package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Criteria {

    private int pageNum;
    private int amount;

    public Criteria() {
        this(1, 10); // 기본 1페이지, 한 페이지당 10개씩 조회
    }

    public Criteria(int pageNum, int amount) {
        this.pageNum = pageNum;
        this.amount = amount;
    }
}