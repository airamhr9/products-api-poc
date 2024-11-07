package com.airamhr9.products.adapters.category.persistence.entity;

import com.airamhr9.products.adapters.discount.persistence.entity.DiscountEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class CategoryEntity {
  @Id
  private String id;

  private String name;

  @ManyToMany
  @JoinTable(name = "categories_discounts", joinColumns = @JoinColumn(name = "category_id"), inverseJoinColumns = @JoinColumn(name = "discount_id"))
  private Set<DiscountEntity> categoryDiscounts = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CategoryEntity that = (CategoryEntity) o;
    return Objects.equals(id, that.id) && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }
}
