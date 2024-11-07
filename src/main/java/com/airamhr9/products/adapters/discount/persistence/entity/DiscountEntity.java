package com.airamhr9.products.adapters.discount.persistence.entity;

import com.airamhr9.products.adapters.category.persistence.entity.CategoryEntity;
import com.airamhr9.products.adapters.product.persistence.entity.ProductEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "discounts")
public class DiscountEntity {
  @Id
  private String id;

  private String name;

  private Integer percent;

  @ManyToMany(mappedBy = "categoryDiscounts")
  private Set<CategoryEntity> categories = new LinkedHashSet<>();

  @ManyToOne
  @JoinColumn(name = "sku")
  private ProductEntity product;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DiscountEntity that = (DiscountEntity) o;
    return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(percent, that.percent);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, percent);
  }
}
