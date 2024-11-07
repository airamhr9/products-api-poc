package com.airamhr9.products.application.common.search;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageRequest {
  @NotNull
  private Integer number;
  @NotNull
  private Integer size;
  private SortDirection sortDirection = SortDirection.ASC;
  private String sortField;
}
