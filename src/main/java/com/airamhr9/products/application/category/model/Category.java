package com.airamhr9.products.application.category.model;

import com.airamhr9.products.application.discount.model.Discount;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
  private String id;
  private String name;
  private List<Discount> categoryDiscounts;

  public List<Discount> getCategoryDiscounts() {
    if (categoryDiscounts == null) {
      categoryDiscounts = new ArrayList<>();
    }
    return categoryDiscounts;
  }
}
