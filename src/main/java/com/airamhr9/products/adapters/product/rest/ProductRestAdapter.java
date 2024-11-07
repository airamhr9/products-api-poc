package com.airamhr9.products.adapters.product.rest;

import com.airamhr9.products.adapters.common.rest.constants.RestConstants;
import com.airamhr9.products.application.common.search.SortDirection;
import com.airamhr9.products.application.common.search.ValidSortFields;
import com.airamhr9.products.application.common.validation.ValidValues;
import com.airamhr9.products.application.product.model.Product;
import com.airamhr9.products.application.product.ports.in.ListProductsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Product API")
@RequestMapping(RestConstants.API_V1 + "/products")
public class ProductRestAdapter {
  private final ListProductsUseCase listProductsUseCase;

  @GetMapping
  @Operation(summary = "Get a list of all products")
  public ResponseEntity<List<Product>> findAll(@RequestParam(required = false, defaultValue = "") final List<String> categoryIds,
                                               @ValidValues(values = {"sku", "originalPrice", "description", "category.name"})
                                               @Parameter(allowEmptyValue = true, description = "Field to sort by. Available values: \"sku\", \"originalPrice\", \"description\", \"category.name\"")
                                               @RequestParam(required = false, defaultValue = "sku") final String sortField,
                                               @RequestParam(required = false, defaultValue = "ASC") final SortDirection sortDirection) {
    final ListProductsUseCase.ProductListRequest listRequest =
            new ListProductsUseCase.ProductListRequest(categoryIds, sortDirection, sortField);
    return ResponseEntity.ok(listProductsUseCase.findAll(listRequest));
  }

  @PostMapping("/query")
  @Operation(summary = "Get products in a page format")
  public ResponseEntity<PagedModel<Product>> findPaged(@Valid @RequestBody final ListProductsUseCase.ProductPageRequest request) {
    return ResponseEntity.ok(listProductsUseCase.findPaged(request));
  }
}
