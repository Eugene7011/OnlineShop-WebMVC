package com.podzirei.onlineshop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
@Getter
@Builder
@NoArgsConstructor
public class Product {
    private int id;
    private String name;
    private double price;
    private LocalDateTime creationDate;
}