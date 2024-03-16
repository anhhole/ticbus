package com.ticbus.backend.services;

import com.ticbus.backend.exception.EntityType;
import com.ticbus.backend.exception.ExceptionType;
import com.ticbus.backend.exception.TicbusException;

public class BaseService {
    /**
     * Returns a new RuntimeException
     */
    public RuntimeException exception(EntityType entityType, ExceptionType exceptionType,
                                       String... args) {
        return TicbusException.throwException(entityType, exceptionType, args);
    }

    /**
     * Returns a new RuntimeException
     */
    public RuntimeException exceptionWithId(EntityType entityType, ExceptionType exceptionType,
                                             String id, String... args) {
        return TicbusException.throwExceptionWithId(entityType, exceptionType, id, args);
    }
}
