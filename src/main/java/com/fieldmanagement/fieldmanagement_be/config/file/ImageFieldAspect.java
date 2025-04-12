package com.fieldmanagement.fieldmanagement_be.config.file;

import com.fieldmanagement.fieldmanagement_be.common.base.dto.ResponseDto;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Aspect
@Component
public class ImageFieldAspect {
    @Value("${images.base-url}")
    private String imageBaseUrl;

    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object wrapImageUrlInResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();

        if (result instanceof ResponseEntity<?> responseEntity) {
            Object body = responseEntity.getBody();

            if (body instanceof ResponseDto<?> responseDto) {
                Object data = responseDto.getData();
                processImageFields(data);
            }

            return responseEntity;
        }

        processImageFields(result);
        return result;
    }

    private void processImageFields(Object obj) {
        if (obj == null) return;

        if (obj instanceof Collection<?> collection) {
            for (Object item : collection) processImageFields(item);
        } else if (obj.getClass().isArray()) {
            if (obj.getClass().getComponentType().isPrimitive()) return;
            for (Object item : (Object[]) obj) processImageFields(item);
        } else if (obj instanceof Map<?, ?> map) {
            map.values().forEach(this::processImageFields);
        } else {
            updateImageFields(obj);
        }
    }

    private void updateImageFields(Object obj) {
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(ImageField.class)) {
                field.setAccessible(true);
                try {
                    Object value = field.get(obj);
                    if (value instanceof String path && !path.startsWith("http")) {
                        String fixedPath = path.replace("\\", "/");
                        field.set(obj, imageBaseUrl + fixedPath);
                    }
                    else if (value instanceof List<?> list) {
                        List<?> newList = list.stream().map(item -> {
                            if (item instanceof String str && !str.startsWith("http")) {
                                return imageBaseUrl + str.replace("\\", "/");
                            }
                            return item;
                        }).toList();
                        field.set(obj, newList);
                    }
                } catch (IllegalAccessException ignored) {
                }
            }
        }
    }
}
