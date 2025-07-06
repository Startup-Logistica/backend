package br.portela.startuplogistica.rest.specs;

import br.portela.startuplogistica.dtos.commons.pagination.output.PaginationOutputDTO;
import br.portela.startuplogistica.dtos.user.input.ChangePasswordInputDTO;
import br.portela.startuplogistica.dtos.user.input.CreateUserInputDTO;
import br.portela.startuplogistica.dtos.user.input.FindUsersByFilters;
import br.portela.startuplogistica.dtos.user.output.UserDetailedOutputDTO;
import br.portela.startuplogistica.dtos.user.output.UserMinimalOutputDTO;
import br.portela.startuplogistica.rest.specs.commons.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@ApiResponseInternalServerError
@Tag(name = "2. User", description = "User operations")
public interface UserControllerSpecs {

    abstract class UserMinimalOutputPaginationDTO extends PaginationOutputDTO<UserMinimalOutputDTO> { }

    @Operation(summary = "Find all users", description = "Required roles: `ADMIN`")
    @ApiResponseForbidden
    @ApiResponseBadRequest
    @ApiResponse(responseCode = "200", description = "Ok", content = {
            @Content(
                    array = @ArraySchema(schema = @Schema(implementation = UserMinimalOutputPaginationDTO.class)),
                    mediaType = MediaType.APPLICATION_JSON_VALUE
            )}
    )
    @SecurityRequirement(name = "jwt")
    @ResponseStatus(HttpStatus.OK)
    PaginationOutputDTO<UserMinimalOutputDTO> list(@ParameterObject @ModelAttribute FindUsersByFilters request);

    @Operation(summary = "Create user")
    @ApiResponseBadRequest
    @ApiResponseDuplicatedResource
    @ApiResponse(responseCode = "201", description = "Created")
    @ResponseStatus(HttpStatus.CREATED)
    void create(@RequestBody CreateUserInputDTO request);

    @Operation(summary = "Find user by id", description = "Required roles: `ADMIN`")
    @ApiResponseNotFound
    @ApiResponseForbidden
    @ApiResponseBadRequest
    @ApiResponse(responseCode = "200", description = "Ok", content = {
            @Content(
                    schema = @Schema(implementation = UserDetailedOutputDTO.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE
            )}
    )
    @SecurityRequirement(name = "jwt")
    @ResponseStatus(HttpStatus.OK)
    UserDetailedOutputDTO findById(@PathVariable UUID id, @RequestParam(name = "allow_inactive") Boolean allowInactive);

    @Operation(summary = "Get current logged in user")
    @ApiResponseForbidden
    @ApiResponse(responseCode = "200", description = "Ok", content = {
            @Content(
                    schema = @Schema(implementation = UserDetailedOutputDTO.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE
            )}
    )
    @SecurityRequirement(name = "jwt")
    @ResponseStatus(HttpStatus.OK)
    UserDetailedOutputDTO get();

    @Operation(summary = "Change password")
    @ApiResponseNotFound
    @ApiResponseBadRequest
    @ApiResponseBusinessRuleException
    @ApiResponse(responseCode = "204", description = "No Content")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void changePassword(@RequestBody ChangePasswordInputDTO request);

    @Operation(summary = "Validate e-mail")
    @ApiResponseNotFound
    @ApiResponseBadRequest
    @ApiResponse(responseCode = "200", description = "Ok")
    @ResponseStatus(HttpStatus.OK)
    String validateEmail(@PathVariable UUID id);

    @Operation(summary = "Delete current logged in user")
    @ApiResponseForbidden
    @ApiResponse(responseCode = "204", description = "No Content")
    @SecurityRequirement(name = "jwt")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete();
}
