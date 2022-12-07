package com.podzirei.onlineshop.dao.repository.jdbcTemplate;

import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;

public class PreparedStatementSetter {

    @SneakyThrows
    public static PreparedStatement setPreparedStatement(PreparedStatement statement, Object[] args) {
        Method[] methods = PreparedStatement.class.getMethods();
        int parameterIndex = 1;
        for (Object arg : args) {
            Method setMethod = findSetterName(arg, methods);

            if ("setTimestamp".equals(setMethod.getName())) {
                arg = Timestamp.valueOf((LocalDateTime) arg);
            }

            Object[] parameters = {parameterIndex, arg};
            setMethod.invoke(statement, parameters);
            parameterIndex++;
        }
        return statement;
    }

    @SneakyThrows
    private static Method findSetterName(Object arg, Method[] methods) {
        String className = arg.getClass().getSimpleName();
        if ("Integer".equals(className)) {
            className = "Int";
        } else if ("LocalDateTime".equals(className)) {
            className = "Timestamp";
        }
        String methodName = "set" + className;

        Method methodClass = Arrays.stream(methods)
                .filter(method -> method.getName().equals(methodName))
                .filter(method -> method.getParameterTypes().length == 2)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        return methodClass;
    }
}
