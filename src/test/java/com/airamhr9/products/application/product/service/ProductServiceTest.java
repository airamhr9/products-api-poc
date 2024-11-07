package com.airamhr9.products.application.product.service;

import com.airamhr9.products.application.category.model.Category;
import com.airamhr9.products.application.common.search.PageRequest;
import com.airamhr9.products.application.common.search.SortDirection;
import com.airamhr9.products.application.product.model.Product;
import com.airamhr9.products.application.product.ports.in.ListProductsUseCase;
import com.airamhr9.products.application.product.ports.out.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

  @Mock
  private ProductRepository repository;

  @InjectMocks
  private ProductService productService;

  private List<Product> products;

  @BeforeEach
  void setUp() {
    products = new ArrayList<>(List.of(
            new Product("SKU", "Product Name1", BigDecimal.valueOf(100.00),
                    new Category("cat1", "cat1", new ArrayList<>()), new ArrayList<>()),
            new Product("SKU2", "Product Name2", BigDecimal.valueOf(200.00),
                    new Category("cat1", "cat1", new ArrayList<>()), new ArrayList<>()),
            new Product("SKU3", "Product Name3", BigDecimal.valueOf(300.00),
                    new Category("cat2", "cat2", new ArrayList<>()), new ArrayList<>()),
            new Product("SKU4", "Product Name4", BigDecimal.valueOf(400.00),
                    new Category("cat3", "cat3", new ArrayList<>()), new ArrayList<>())
    ));
  }

  @Test
  void given_nullCategoryIdsList_when_callingFindAll_then_callsTheRepositoryMethodWithoutCategories() {
    ListProductsUseCase.ProductListRequest request = new ListProductsUseCase.ProductListRequest();
    request.setCategoryIds(null);
    when(repository.findAll(any(), any())).thenReturn(products);

    List<Product> result = productService.findAll(request);

    assertEquals(products, result);
    verify(repository, times(1)).findAll(any(SortDirection.class), any());
    verify(repository, never()).findAll(any(), any(), any());
  }

  @Test
  void given_emptyCategoryIdsList_when_callingFindAll_then_callsTheRepositoryMethodWithoutCategories() {
    ListProductsUseCase.ProductListRequest request = new ListProductsUseCase.ProductListRequest();
    request.setCategoryIds(new ArrayList<>());
    when(repository.findAll(any(), any())).thenReturn(products);

    List<Product> result = productService.findAll(request);

    assertEquals(products, result);
    verify(repository, times(1)).findAll(any(SortDirection.class), any());
    verify(repository, never()).findAll(any(), any(), any());
  }

  @Test
  void given_categoryIdsListWithElements_when_callingFindAll_then_callsTheRepositoryMethodThatReceivesCategoryIds() {
    ListProductsUseCase.ProductListRequest request = new ListProductsUseCase.ProductListRequest();
    request.setCategoryIds(List.of("cat1", "cat2"));
    when(repository.findAll(eq(request.getCategoryIds()), any(), any())).thenReturn(products);

    List<Product> result = productService.findAll(request);

    assertEquals(products, result);
    verify(repository, times(1)).findAll(eq(request.getCategoryIds()), any(SortDirection.class), any());
    verify(repository, never()).findAll(any(), any());
  }

  @Test
  void given_nullCategoryIdsList_when_callingFindPaged_then_callsTheRepositoryMethodWithoutCategories() {
    ListProductsUseCase.ProductPageRequest request = new ListProductsUseCase.ProductPageRequest();
    request.setCategoryIds(null);
    request.setPage(new PageRequest(0, 10, SortDirection.ASC, "sku"));
    PagedModel<Product> expectedPagedProducts = new PagedModel<>(new PageImpl<>(products,
            org.springframework.data.domain.PageRequest.of(0, 10, Sort.Direction.ASC, "sku"),
            products.size()));
    when(repository.findPaged(request.getPage())).thenReturn(expectedPagedProducts);

    PagedModel<Product> result = productService.findPaged(request);

    assertEquals(expectedPagedProducts, result);
    verify(repository, times(1)).findPaged(request.getPage());
    verify(repository, never()).findPaged(any(), any());
  }

  @Test
  void given_emptyCategoryIdsList_when_callingFindPaged_then_callsTheRepositoryMethodWithoutCategories() {
    ListProductsUseCase.ProductPageRequest request = new ListProductsUseCase.ProductPageRequest();
    request.setCategoryIds(new ArrayList<>());
    request.setPage(new PageRequest(0, 10, SortDirection.ASC, "sku"));
    PagedModel<Product> expectedPagedProducts = new PagedModel<>(new PageImpl<>(products,
            org.springframework.data.domain.PageRequest.of(0, 10, Sort.Direction.ASC, "sku"),
            products.size()));
    when(repository.findPaged(request.getPage())).thenReturn(expectedPagedProducts);

    PagedModel<Product> result = productService.findPaged(request);

    assertEquals(expectedPagedProducts, result);
    verify(repository, times(1)).findPaged(request.getPage());
    verify(repository, never()).findPaged(anyList(), any(PageRequest.class));
  }

  @Test
  void given_nonEmptyCategoryIdsList_when_callingFindPaged_then_callsTheRepositoryMethodThatReceivesCategoryIds() {
    ListProductsUseCase.ProductPageRequest request = new ListProductsUseCase.ProductPageRequest();
    request.setCategoryIds(List.of("cat1", "cat2"));
    request.setPage(new PageRequest(0, 10, SortDirection.ASC, "sku"));
    PagedModel<Product> expectedPagedProducts = new PagedModel<>(new PageImpl<>(products,
            org.springframework.data.domain.PageRequest.of(0, 10, Sort.Direction.ASC, "sku"),
            products.size()));
    when(repository.findPaged(request.getCategoryIds(), request.getPage())).thenReturn(expectedPagedProducts);

    PagedModel<Product> result = productService.findPaged(request);

    assertEquals(expectedPagedProducts, result);
    verify(repository, times(1)).findPaged(request.getCategoryIds(), request.getPage());
    verify(repository, never()).findPaged(any());
  }

}
