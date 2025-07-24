package com.ninos.dto;

import com.ninos.model.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductDTO {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
