package com.github.rooneyandshadows.commons.mapping;

import com.github.rooneyandshadows.commons.mapping.exception.MappingException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassMapper {
    public static <TSource, TTarget> TTarget mapPrivate(TSource source, TTarget target) throws MappingException {
        return mapPrivate(source, target, true);
    }

    @SuppressWarnings("unchecked")
    public static <TSource, TTarget> TTarget mapPrivate(TSource source, TTarget target, boolean strict) throws MappingException {
        Class sourceClass = source.getClass();
        Class targetClass = target.getClass();
        Field[] fields = targetClass.getDeclaredFields();
        for (Field field :
                fields) {
            String fieldName = field.getName();
            String upperFieldName = field.getName().replace(String.valueOf(
                    fieldName.charAt(0)),
                    String.valueOf(fieldName.charAt(0)).toUpperCase());
            String getterMethodName = "get" + upperFieldName;
            String isMethodName = "is" + upperFieldName;
            String setterMethodName = "set" + upperFieldName;

            try {
                Method getter;
                try {
                    getter = sourceClass.getMethod(getterMethodName);
                } catch (NoSuchMethodException ex) {
                    getter = sourceClass.getMethod(isMethodName);
                }
                if (getter == null)
                    if (!strict)
                        continue;
                    else
                        throw new MappingException("Could not map properties! Getter method is not configured as expected!");
                Class returnType = getter.getReturnType();
                Method setter = targetClass.getMethod(setterMethodName, returnType);
                if (setter == null)
                    if (!strict)
                        continue;
                    else
                        throw new MappingException("Could not map properties! Setter method is not configured as expected!");
                setter.invoke(target, getter.invoke(source));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
                if (strict)
                    throw new MappingException("Could not map properties", ex);
            }
        }
        return target;
    }
}
