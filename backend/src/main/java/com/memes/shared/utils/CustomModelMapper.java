package com.memes.shared.utils;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class CustomModelMapper extends ModelMapper {
  public <S, T> List<T> mapList(List<S> source, Class<T> target) {
    return source.stream().map(item -> this.map(item, target)).collect(Collectors.toList());
  }
}
