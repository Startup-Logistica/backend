package br.portela.startuplogistica.rest.specs;

import br.portela.startuplogistica.dtos.auth.input.LoginInputDTO;
import br.portela.startuplogistica.dtos.auth.input.RequirePasswordRecoveryInputDTO;
import br.portela.startuplogistica.dtos.auth.input.ValidatePasswordRecoveryCodeInputDTO;
import br.portela.startuplogistica.dtos.auth.output.LoginOutputDTO;
import br.portela.startuplogistica.rest.specs.commons.ApiResponseBadRequest;
import br.portela.startuplogistica.rest.specs.commons.ApiResponseBusinessRuleException;
import br.portela.startuplogistica.rest.specs.commons.ApiResponseInternalServerError;
import br.portela.startuplogistica.rest.specs.commons.ApiResponseNotFound;
import br.portela.startuplogistica.rest.specs.commons.ApiResponseUnauthorized;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ApiResponseBadRequest
@ApiResponseInternalServerError
@Tag(name = "1. Auth", description = "Auth operations")
public interface AuthControllerSpecs {

    @Operation(summary = "Login")
    @ApiResponseUnauthorized
    @ApiResponse(responseCode = "200", description = "Ok", content = {
        @Content(
            schema = @Schema(implementation = LoginOutputDTO.class),
            mediaType = MediaType.APPLICATION_JSON_VALUE)
        }
    )
    @ResponseStatus(HttpStatus.OK)
    LoginOutputDTO login(@RequestBody LoginInputDTO request);

    @Operation(summary = "Require Password Recovery")
    @ApiResponseNotFound
    @ApiResponse(responseCode = "204", description = "No Content")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void requirePasswordRecovery(@RequestBody RequirePasswordRecoveryInputDTO request);

    @Operation(summary = "Validate Password Recovery Code")
    @ApiResponseNotFound
    @ApiResponseBusinessRuleException
    @ApiResponse(responseCode = "204", description = "No Content")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void validatePasswordRecoveryCode(@RequestBody ValidatePasswordRecoveryCodeInputDTO request);
}
