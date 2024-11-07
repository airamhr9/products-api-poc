package com.airamhr9.products.application.product.ports.in;

import com.airamhr9.products.application.common.search.PageRequest;
import com.airamhr9.products.application.common.search.SortDirection;
import com.airamhr9.products.application.common.search.ValidSortFields;
import com.airamhr9.products.application.common.validation.ValidValues;
import com.airamhr9.products.application.product.model.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;

import java.util.ArrayList;
import java.util.List;

public interface ListProductsUseCase {
  List<Product> findAll(ProductListRequest request);

  PagedModel<Product> findPaged(ProductPageRequest request);

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  class ProductListRequest {
    private List<String> categoryIds = new ArrayList<>();
    private SortDirection sortDirection = SortDirection.ASC;
    private String sortField;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  class ProductPageRequest {
    private List<String> categoryIds = new ArrayList<>();
    @Valid
    @NotNull
    @ValidSortFields(values = {"sku", "originalPrice", "description", "category.name"})
     private PageRequest page;
  }
}
