package com.airamhr9.products.adapters.category.persistence.mapper;

import com.airamhr9.products.adapters.category.persistence.entity.CategoryEntity;
import com.airamhr9.products.application.category.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryEntityMapper {
  Category toDto(CategoryEntity entity);
}
