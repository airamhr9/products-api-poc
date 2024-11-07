package com.airamhr9.products.adapters.product.persistence.mapper;

import com.airamhr9.products.adapters.category.persistence.mapper.CategoryEntityMapper;
import com.airamhr9.products.adapters.discount.persistence.mapper.DiscountEntityMapper;
import com.airamhr9.products.adapters.product.persistence.entity.ProductEntity;
import com.airamhr9.products.application.product.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        uses = {DiscountEntityMapper.class, CategoryEntityMapper.class})
public interface ProductEntityMapper {
  Product toDto(ProductEntity entity);
}
