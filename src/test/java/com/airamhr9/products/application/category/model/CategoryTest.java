package com.airamhr9.products.application.category.model;

import com.airamhr9.products.application.discount.model.Discount;
import com.airamhr9.products.application.product.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CategoryTest {
  private Category category;

  @BeforeEach
  void setUp() {
    category = new Category("category", "Test Category", new ArrayList<>());
  }

  @Test
  void given_nullCategoryDiscounts_when_gettingCategoryDiscounts_then_emptyListIsReturned() {
    category.setCategoryDiscounts(null);
    final List<Discount> discounts = category.getCategoryDiscounts();

    assertTrue(discounts != null && discounts.isEmpty());
  }

  @Test
  void given_nonNullCategoryDiscounts_when_gettingCategoryDiscounts_then_correctListIsReturned() {
    final List<Discount> discounts = List.of(
            new Discount("specific1", "Specific Discount 30%", 30, null),
            new Discount("specific1", "Specific Discount 40%", 40, null)
    );
    category.setCategoryDiscounts(discounts);

    assertEquals(discounts, category.getCategoryDiscounts());
  }
}