package br.portela.startuplogistica.errors.exceptions;

import br.portela.startuplogistica.errors.ExceptionCode;
import lombok.Getter;

@Getter
public class ForbiddenException extends RuntimeException {
    public ForbiddenException() {
        super(ExceptionCode.FORBIDDEN.toString());
    }
}
