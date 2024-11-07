package com.airamhr9.products.application.discount.model;

import com.airamhr9.products.application.category.model.Category;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Discount {
  private String id;
  private String name;
  private Integer percent;
  private String sku;
}
