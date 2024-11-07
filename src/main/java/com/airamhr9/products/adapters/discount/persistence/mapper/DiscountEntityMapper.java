package com.airamhr9.products.adapters.discount.persistence.mapper;

import com.airamhr9.products.adapters.discount.persistence.entity.DiscountEntity;
import com.airamhr9.products.application.discount.model.Discount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DiscountEntityMapper {
  @Mapping(target = "sku", source = "product.sku")
  Discount toDto(DiscountEntity entity);
}
