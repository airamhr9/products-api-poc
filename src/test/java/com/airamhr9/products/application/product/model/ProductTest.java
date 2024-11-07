package com.airamhr9.products.application.product.model;

import com.airamhr9.products.application.category.model.Category;
import com.airamhr9.products.application.discount.model.Discount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductTest {
  private Product product;

  @BeforeEach
  void setUp() {
    product = new Product("SKU", "Product Name", BigDecimal.valueOf(100.00), new Category(), new ArrayList<>());
  }

  @Test
  void given_noDiscounts_when_gettingCurrentPrice_then_currentPriceIsOriginalPrice() {
    assertEquals(product.getOriginalPrice(), product.getCurrentPrice(), "Price should remain the original price.");
  }

  @Test
  void given_singleCategoryDiscount_when_gettingCurrentPrice_then_categoryDiscountIsApplied() {
    product.getCategory().getCategoryDiscounts().add(new Discount("category1", "Category Discount 10%", 10, null));

    Discount currentDiscount = product.getCurrentDiscount();
    assertEquals(10, currentDiscount.getPercent(), "Discount should be 10%");
    assertEquals(BigDecimal.valueOf(90.00).setScale(2, RoundingMode.HALF_UP), product.getCurrentPrice(), "Price should be reduced by 10%");

    product.getCategory().getCategoryDiscounts().clear();
  }

  @Test
  void given_singleCategoryDiscountAndSingleSpecificDiscount_when_gettingCurrentPrice_then_largestDiscountIsApplied() {
    product.getCategory().getCategoryDiscounts().add(new Discount("category1", "Category Discount 10%", 10, null));
    product.getSpecificDiscounts().add(new Discount("specific1", "Specific Discount 20%", 20, product.getSku()));

    Discount currentDiscount = product.getCurrentDiscount();
    assertEquals(20, currentDiscount.getPercent(), "Discount should be 20%");
    assertEquals(BigDecimal.valueOf(80.00).setScale(2, RoundingMode.HALF_UP), product.getCurrentPrice(), "Price should be reduced by 20%");

    product.getCategory().getCategoryDiscounts().clear();
    product.getSpecificDiscounts().clear();
  }

  @Test
  void given_CategoryDiscountsAndSpecificDiscounts_when_gettingCurrentPrice_then_largestDiscountIsApplied() {
    product.getCategory().getCategoryDiscounts().add(new Discount("category1", "Category Discount 10%", 10, null));
    product.getCategory().getCategoryDiscounts().add(new Discount("category2", "Category Discount 15%", 15, null));
    product.getSpecificDiscounts().add(new Discount("specific1", "Specific Discount 30%", 30, product.getSku()));
    product.getSpecificDiscounts().add(new Discount("specific1", "Specific Discount 40%", 40, product.getSku()));

    Discount currentDiscount = product.getCurrentDiscount();
    assertEquals(40, currentDiscount.getPercent(), "Discount should be 40%");
    assertEquals(BigDecimal.valueOf(60.00).setScale(2, RoundingMode.HALF_UP), product.getCurrentPrice(), "Price should be reduced by 40%");

    product.getCategory().getCategoryDiscounts().clear();
    product.getSpecificDiscounts().clear();
  }

  @Test
  void given_nullSpecificDiscounts_when_gettingSpecificDiscounts_then_emptyListIsReturned() {
    product.setSpecificDiscounts(null);
    final List<Discount> discounts = product.getSpecificDiscounts();

    assertTrue(discounts != null && discounts.isEmpty());
  }

  @Test
  void given_nonNullSpecificDiscounts_when_gettingSpecificDiscounts_then_correctListIsReturned() {
    final List<Discount> discounts = List.of(
            new Discount("specific1", "Specific Discount 30%", 30, product.getSku()),
            new Discount("specific1", "Specific Discount 40%", 40, product.getSku())
    );
    product.setSpecificDiscounts(discounts);

    assertEquals(discounts, product.getSpecificDiscounts());
  }
}