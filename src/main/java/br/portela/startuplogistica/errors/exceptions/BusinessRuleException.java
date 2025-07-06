package br.portela.startuplogistica.errors.exceptions;

import br.portela.startuplogistica.errors.ExceptionCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessRuleException extends RuntimeException {
    private final ExceptionCode code;

    public BusinessRuleException(ExceptionCode code) {
        super(code.toString());
        this.code = code;
    }
}

