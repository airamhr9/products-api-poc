package com.airamhr9.products.application.product.model;

import com.airamhr9.products.application.category.model.Category;
import com.airamhr9.products.application.discount.model.Discount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
  private String sku;
  private String description;
  private BigDecimal originalPrice;
  private Category category;
  private List<Discount> specificDiscounts;

  public Discount getCurrentDiscount() {
    Discount currentDiscount = null;
    int maxPercent = Integer.MIN_VALUE;

    for (Discount categoryDiscount : category.getCategoryDiscounts()) {
      if (categoryDiscount.getPercent() > maxPercent) {
        currentDiscount = categoryDiscount;
        maxPercent = categoryDiscount.getPercent();
      }
    }
    for (Discount specificDiscount : getSpecificDiscounts()) {
      if (specificDiscount.getPercent() > maxPercent) {
        currentDiscount = specificDiscount;
        maxPercent = specificDiscount.getPercent();
      }
    }

    return currentDiscount;
  }

  public BigDecimal getCurrentPrice() {
    final Discount currentDiscount = getCurrentDiscount();
    if (currentDiscount == null) {
      return originalPrice;
    }

    BigDecimal currentPrice = originalPrice.subtract(originalPrice.multiply(BigDecimal.valueOf(currentDiscount.getPercent()/100.0)));
    return currentPrice.setScale(2, RoundingMode.HALF_UP);
  }

  public List<Discount> getSpecificDiscounts() {
    if (specificDiscounts == null) {
      specificDiscounts = new ArrayList<>();
    }
    return specificDiscounts;
  }
}
