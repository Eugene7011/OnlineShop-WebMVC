package com.podzirei.onlineshop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Product {
    private int id;
    private String name;
    private double price;
    private LocalDateTime creationDate;
}

