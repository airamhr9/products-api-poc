package com.airamhr9.products.adapters.product.persistence.entity;

import com.airamhr9.products.adapters.category.persistence.entity.CategoryEntity;
import com.airamhr9.products.adapters.discount.persistence.entity.DiscountEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class ProductEntity {
  @Id
  private String sku;

  private String description;

  @Column(precision = 19, scale = 2)
  private BigDecimal originalPrice;

  @OneToMany(mappedBy = "product")
  private Set<DiscountEntity> specificDiscounts = new HashSet<>();

  @ManyToOne
  @JoinColumn(name = "category_id")
  private CategoryEntity category;
}
