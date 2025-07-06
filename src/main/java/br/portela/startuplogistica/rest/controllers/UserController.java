package br.portela.startuplogistica.rest.controllers;

import br.portela.startuplogistica.dtos.commons.pagination.output.PaginationOutputDTO;
import br.portela.startuplogistica.dtos.user.input.ChangePasswordInputDTO;
import br.portela.startuplogistica.dtos.user.input.CreateUserInputDTO;
import br.portela.startuplogistica.dtos.user.input.FindUsersByFilters;
import br.portela.startuplogistica.dtos.user.input.UpdateUserInputDTO;
import br.portela.startuplogistica.dtos.user.output.UserDetailedOutputDTO;
import br.portela.startuplogistica.dtos.user.output.UserMinimalOutputDTO;
import br.portela.startuplogistica.rest.specs.UserControllerSpecs;
import br.portela.startuplogistica.usecases.user.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController implements UserControllerSpecs {
    private final CreateUserUseCase createUserUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;
    private final FindAllUsersUseCase findAllUsersUseCase;
    private final ValidateEmailUseCase validateEmailUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;
    private final FindLoggedUserUseCase findLoggedUserUseCase;
    private final DeleteUserInformationUseCase deleteUserInformationUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    @PostMapping("/users")
    public void create(@RequestBody @Valid CreateUserInputDTO request) {
        createUserUseCase.execute(request);
    }

    @GetMapping("/users")
    public PaginationOutputDTO<UserMinimalOutputDTO> list(@ParameterObject @ModelAttribute @Valid FindUsersByFilters request) {
        return findAllUsersUseCase.execute(request);
    }

    @GetMapping("/users/{id}")
    public UserDetailedOutputDTO findById(@PathVariable UUID id, @RequestParam(defaultValue = "true") Boolean allowInactive) {
        return findUserByIdUseCase.execute(id, allowInactive);
    }

    @GetMapping(value = "/user")
    public UserDetailedOutputDTO get() {
        return findLoggedUserUseCase.execute();
    }

    @PatchMapping("/users/change-password")
    public void changePassword(@RequestBody @Valid ChangePasswordInputDTO request) {
        changePasswordUseCase.execute(request);
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<Object> update(@PathVariable UUID id, @RequestBody @Valid UpdateUserInputDTO request) throws ChangeSetPersister.NotFoundException {
        updateUserUseCase.execute(id, request);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/users/{id}/validate-email")
    public String validateEmail(@PathVariable UUID id) {
        return validateEmailUseCase.execute(id);
    }

    @DeleteMapping("/user")
    public void delete() {
        deleteUserInformationUseCase.execute();
    }

    @DeleteMapping("/users/{id}")
    public void deleteById(@PathVariable UUID id) throws ChangeSetPersister.NotFoundException {
        deleteUserInformationUseCase.executeId(id);
    }

}

