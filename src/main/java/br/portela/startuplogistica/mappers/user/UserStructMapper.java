package br.portela.startuplogistica.mappers.user;


import br.portela.startuplogistica.dtos.user.input.CreateUserInputDTO;
import br.portela.startuplogistica.dtos.user.output.UserDetailedOutputDTO;
import br.portela.startuplogistica.dtos.user.output.UserMinimalOutputDTO;
import br.portela.startuplogistica.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserStructMapper {
    UserDetailedOutputDTO toDetailedOutputDTO(User entity);
    UserMinimalOutputDTO toMinimalOutputDTO(User entity);
    User toEntity(CreateUserInputDTO dto);
}
