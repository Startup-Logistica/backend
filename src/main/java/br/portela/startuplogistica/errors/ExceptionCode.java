package br.portela.startuplogistica.errors;

public enum ExceptionCode {
    MALFORMED_REQUEST,
    API_FIELDS_INVALID,
    ENTITY_NOT_FOUND,
    DUPLICATED_RESOURCE,
    JSON_MAPPING_ERROR,
    INTERNAL_SERVER_ERROR,
    UNAUTHORIZED,
    FORBIDDEN,
    BAD_CREDENTIALS,
    INVALID_PASSWORD_RECOVERY_CODE,
    EMAIL_NOT_SENT,
    AUTHENTICATION_TOKEN_CREATION_ERROR;

    public String getExceptionIndex() {
        var enumTotalEntries = values().length;
        var leftPadZerosCount = (String.valueOf(enumTotalEntries).length() + 1);
        var template = "%0" + leftPadZerosCount + "d";
        return String.format(template, this.ordinal());
    }
}
