package com.airamhr9.products.application.product.ports.out;

import com.airamhr9.products.application.common.search.PageRequest;
import com.airamhr9.products.application.common.search.SortDirection;
import com.airamhr9.products.application.product.model.Product;
import org.springframework.data.web.PagedModel;

import java.util.List;

public interface ProductRepository {
  List<Product> findAll(SortDirection sortDirection, String sortField);

  List<Product> findAll(List<String> categoryIds, SortDirection sortDirection, String sortField);

  PagedModel<Product> findPaged(List<String> categoryIds, PageRequest pageRequest);

  PagedModel<Product> findPaged(PageRequest pageRequest);
}
