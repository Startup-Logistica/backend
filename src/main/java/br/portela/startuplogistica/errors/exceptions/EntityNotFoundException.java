package br.portela.startuplogistica.errors.exceptions;

import br.portela.startuplogistica.errors.ExceptionCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class EntityNotFoundException extends RuntimeException {
    private final Class<?> entityClass;

    public EntityNotFoundException(Class<?> entityClass) {
        super(ExceptionCode.ENTITY_NOT_FOUND.toString());
        this.entityClass = entityClass;
    }
}
