package com.airamhr9.products.adapters.product.persistence.repository;

import com.airamhr9.products.adapters.product.persistence.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface ProductDataSource extends JpaRepository<ProductEntity, String> {
  List<ProductEntity> findByCategoryIdIn(List<String> categoryIds, Sort sort);

  Page<ProductEntity> findByCategoryIdIn(List<String> categoryIds, Pageable pageable);
}
