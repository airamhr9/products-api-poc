package com.airamhr9.products.application.product.service;

import com.airamhr9.products.application.product.model.Product;
import com.airamhr9.products.application.product.ports.in.ListProductsUseCase;
import com.airamhr9.products.application.product.ports.out.ProductRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
class ProductService implements ListProductsUseCase {
  private final ProductRepository repository;

  @Override
  public List<Product> findAll(@NonNull final ProductListRequest request) {
    return request.getCategoryIds() == null || request.getCategoryIds().isEmpty()
            ? repository.findAll(request.getSortDirection(), request.getSortField())
            : repository.findAll(request.getCategoryIds(), request.getSortDirection(), request.getSortField());
  }

  @Override
  public PagedModel<Product> findPaged(@NonNull final ProductPageRequest request) {
    return request.getCategoryIds() == null ||  request.getCategoryIds().isEmpty()
            ? repository.findPaged(request.getPage())
            : repository.findPaged(request.getCategoryIds(), request.getPage());
  }
}
