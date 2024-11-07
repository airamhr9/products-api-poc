package com.airamhr9.products.adapters.product.persistence.repository;

import com.airamhr9.products.adapters.product.persistence.mapper.ProductEntityMapper;
import com.airamhr9.products.application.common.search.PageRequest;
import com.airamhr9.products.application.common.search.SortDirection;
import com.airamhr9.products.application.product.model.Product;
import com.airamhr9.products.application.product.ports.out.ProductRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
class ProductRepositoryAdapter implements ProductRepository {
  private final ProductDataSource dataSource;
  private final ProductEntityMapper mapper;

  @Override
  public List<Product> findAll(final SortDirection sortDirection, final String sortField) {
    return dataSource.findAll(mapProductsSort(sortDirection, sortField)).stream()
            .map(mapper::toDto)
            .toList();
  }

  @Override
  public List<Product> findAll(final List<String> categoryIds, final SortDirection sortDirection, final String sortField) {
    return dataSource.findByCategoryIdIn(categoryIds, mapProductsSort(sortDirection, sortField)).stream()
            .map(mapper::toDto)
            .toList();
  }

  @Override
  public PagedModel<Product> findPaged(final PageRequest pageRequest) {
    final Pageable pageable = org.springframework.data.domain.PageRequest.of(pageRequest.getNumber(), pageRequest.getSize())
            .withSort(mapProductsSort(pageRequest.getSortDirection(), pageRequest.getSortField()));

    return new PagedModel<>(dataSource.findAll(pageable).map(mapper::toDto));
  }

  @Override
  public PagedModel<Product> findPaged(final List<String> categoryIds, final PageRequest pageRequest) {
    final Pageable pageable = org.springframework.data.domain.PageRequest.of(pageRequest.getNumber(), pageRequest.getSize())
            .withSort(mapProductsSort(pageRequest.getSortDirection(), pageRequest.getSortField()));

    return new PagedModel<>(dataSource.findByCategoryIdIn(categoryIds, pageable).map(mapper::toDto));
  }

  private Sort mapProductsSort(final SortDirection sortDirection, final String sortField) {
    return Sort.by(Sort.Direction.valueOf(sortDirection.name()), !Strings.isBlank(sortField) ? sortField : "sku");
  }
}
