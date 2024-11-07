package com.airamhr9.products.adapters.product.rest;

import com.airamhr9.products.adapters.common.rest.constants.RestConstants;
import com.airamhr9.products.application.category.model.Category;
import com.airamhr9.products.application.common.search.PageRequest;
import com.airamhr9.products.application.common.search.SortDirection;
import com.airamhr9.products.application.product.model.Product;
import com.airamhr9.products.application.product.ports.in.ListProductsUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(ProductRestAdapter.class)
class ProductRestAdapterIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ListProductsUseCase listProductsUseCase;

  @Autowired
  private ObjectMapper objectMapper;

  private List<Product> products;

  @BeforeEach
  void setUp() {
    products = new ArrayList<>(List.of(
            new Product("SKU", "Product Name1", BigDecimal.valueOf(400.00).setScale(2, RoundingMode.HALF_UP),
                    new Category("cat1", "cat1", new ArrayList<>()), new ArrayList<>()),
            new Product("SKU2", "Product Name2", BigDecimal.valueOf(300.00).setScale(2, RoundingMode.HALF_UP),
                    new Category("cat1", "cat1", new ArrayList<>()), new ArrayList<>()),
            new Product("SKU3", "Product Name3", BigDecimal.valueOf(200.00).setScale(2, RoundingMode.HALF_UP),
                    new Category("cat2", "cat2", new ArrayList<>()), new ArrayList<>()),
            new Product("SKU4", "Product Name4", BigDecimal.valueOf(100.00).setScale(2, RoundingMode.HALF_UP),
                    new Category("cat3", "cat3", new ArrayList<>()), new ArrayList<>())
    ));}

  @Test
  void given_requestWithoutParams_when_callingFindAllMethod_then_returnsProductListAndUsesDefaultValues() throws Exception {
    when(listProductsUseCase.findAll(any(ListProductsUseCase.ProductListRequest.class))).thenReturn(products);

    mockMvc.perform(get(RestConstants.API_V1 + "/products")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].sku").value("SKU"))
            .andExpect(jsonPath("$[1].sku").value("SKU2"))
            .andExpect(jsonPath("$[2].sku").value("SKU3"))
            .andExpect(jsonPath("$[3].sku").value("SKU4"));

    final ListProductsUseCase.ProductListRequest defaultRequest = new ListProductsUseCase.ProductListRequest();
    defaultRequest.setSortField("sku");
    defaultRequest.setCategoryIds(new ArrayList<>());
    verify(listProductsUseCase, times(1)).findAll(eq(defaultRequest));
  }

  @Test
  void given_requestWithSortField_when_callingFindAllMethod_then_returnsProductListSorted() throws Exception {
    final ListProductsUseCase.ProductListRequest request = new ListProductsUseCase.ProductListRequest();
    request.setSortField("originalPrice");
    products.sort(Comparator.comparing(Product::getOriginalPrice));
    when(listProductsUseCase.findAll(request)).thenReturn(products);

    mockMvc.perform(get(RestConstants.API_V1 + "/products")
                    .param("sortField", "originalPrice")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].originalPrice").value("100.0"))
            .andExpect(jsonPath("$[1].originalPrice").value("200.0"))
            .andExpect(jsonPath("$[2].originalPrice").value("300.0"))
            .andExpect(jsonPath("$[3].originalPrice").value("400.0"));
  }
  @Test
  void given_requestWithSortFieldAndSortDirectionDESC_when_callingFindAllMethod_then_returnsProductListSortedInReverse() throws Exception {
    final ListProductsUseCase.ProductListRequest request = new ListProductsUseCase.ProductListRequest();
    request.setSortField("originalPrice");
    request.setSortDirection(SortDirection.DESC);
    products.sort(Comparator.comparing(Product::getOriginalPrice).reversed());
    when(listProductsUseCase.findAll(request)).thenReturn(products);

    mockMvc.perform(get(RestConstants.API_V1 + "/products")
                    .param("sortField", "originalPrice")
                    .param("sortDirection", "DESC")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].originalPrice").value("400.0"))
            .andExpect(jsonPath("$[1].originalPrice").value("300.0"))
            .andExpect(jsonPath("$[2].originalPrice").value("200.0"))
            .andExpect(jsonPath("$[3].originalPrice").value("100.0"));
  }

  @Test
  void given_requestWithCategoryIds_when_callingFindAllMethodWithCategoryIds_then_returnsFilteredProductList() throws Exception {
    List<Product> filtered = products.stream()
            .filter(p -> p.getCategory().getId().equals("cat1") || p.getCategory().getId().equals("cat2")).toList();
    when(listProductsUseCase.findAll(any(ListProductsUseCase.ProductListRequest.class))).thenReturn(filtered);

    mockMvc.perform(get(RestConstants.API_V1 + "/products")
                    .param("categoryIds", "cat1,cat2")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].category.id").value("cat1"))
            .andExpect(jsonPath("$[1].category.id").value("cat1"))
            .andExpect(jsonPath("$[2].category.id").value("cat2"))
            .andExpect(jsonPath("$[3]").doesNotExist());
  }
  @Test
  void given_invalidSortFieldValue_when_callingFindAll_then_returnsBadRequest() throws Exception {
    when(listProductsUseCase.findAll(any(ListProductsUseCase.ProductListRequest.class))).thenReturn(products);

    mockMvc.perform(get(RestConstants.API_V1 + "/products")
                    .param("sortField", "invalidSortField")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
  }

  @Test
  void given_invalidSortDirectionValue_when_callingFindAll_then_returnsBadRequest() throws Exception {
    when(listProductsUseCase.findAll(any(ListProductsUseCase.ProductListRequest.class))).thenReturn(products);

    mockMvc.perform(get(RestConstants.API_V1 + "/products")
                    .param("sortDirection", "invalidSortDirection")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
  }

    @Test
  void given_requestWithoutCategoryIds_when_callingFindPaged_then_returnsProductPage() throws Exception {
    ListProductsUseCase.ProductPageRequest request = new ListProductsUseCase.ProductPageRequest();
    request.setCategoryIds(new ArrayList<>());
    request.setPage(new PageRequest(0, 2, SortDirection.ASC, "sku"));
    PagedModel<Product> expectedPagedProducts = new PagedModel<>(new PageImpl<>(products.subList(0, 2),
            org.springframework.data.domain.PageRequest.of(0, 2, Sort.Direction.ASC, "sku"),
            products.size()));
    when(listProductsUseCase.findPaged(request)).thenReturn(expectedPagedProducts);

    mockMvc.perform(post(RestConstants.API_V1 + "/products/query")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content[0].sku").value("SKU"))
            .andExpect(jsonPath("$.content[1].sku").value("SKU2"))
            .andExpect(jsonPath("$.content[2]").doesNotExist())
            .andExpect(jsonPath("$.page.size").value(2))
            .andExpect(jsonPath("$.page.totalElements").value(4))
            .andExpect(jsonPath("$.page.number").value(0));
  }

  @Test
  void given_nullPage_when_callingFindPaged_then_returnsBadRequest() throws Exception {
    ListProductsUseCase.ProductPageRequest invalidRequest = new ListProductsUseCase.ProductPageRequest();
    invalidRequest.setPage(null);

    mockMvc.perform(post(RestConstants.API_V1 + "/products/query")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidRequest)))
            .andExpect(status().isBadRequest());
  }

  @Test
  void given_nullPageElements_when_callingFindPaged_then_returnsBadRequest() throws Exception {
    ListProductsUseCase.ProductPageRequest invalidRequest = new ListProductsUseCase.ProductPageRequest();
    invalidRequest.setPage(new PageRequest(null, null , null, null));

    mockMvc.perform(post(RestConstants.API_V1 + "/products/query")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidRequest)))
            .andExpect(status().isBadRequest());
  }
}