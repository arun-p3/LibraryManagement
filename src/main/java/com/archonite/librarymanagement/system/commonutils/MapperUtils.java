package com.archonite.librarymanagement.system.commonutils;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MapperUtils {

    private static final ModelMapper mapper = new ModelMapper();

    public static <S, T> T map(S source, Class<T> targetClass) {
        return mapper.map(source, targetClass);
    }
}
