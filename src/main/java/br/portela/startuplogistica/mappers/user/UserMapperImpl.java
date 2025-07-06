package br.portela.startuplogistica.mappers.user;

import br.portela.startuplogistica.config.annotations.Mapper;
import br.portela.startuplogistica.dtos.commons.pagination.output.PaginationOutputDTO;
import br.portela.startuplogistica.dtos.user.input.CreateUserInputDTO;
import br.portela.startuplogistica.dtos.user.output.UserDetailedOutputDTO;
import br.portela.startuplogistica.dtos.user.output.UserMinimalOutputDTO;
import br.portela.startuplogistica.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
@RequiredArgsConstructor
public class UserMapperImpl {
    private final UserStructMapper mapper;

    public UserDetailedOutputDTO toDetailedOutputDTO(User entity) {
        return mapper.toDetailedOutputDTO(entity);
    }

    public UserMinimalOutputDTO toMinimalOutputDTO(User entity) {
        return mapper.toMinimalOutputDTO(entity);
    }

    public List<UserMinimalOutputDTO> toMinimalOutputDTO(List<User> entities) {
        return entities.stream().map(this::toMinimalOutputDTO).toList();
    }

    public PaginationOutputDTO<UserMinimalOutputDTO> toMinimalOutputDTO(Page<User> page) {
        var entities = this.toMinimalOutputDTO(page.getContent());
        return new PaginationOutputDTO<>(
                entities,
                page.getNumber(),
                page.getNumberOfElements(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }

    public User toEntity(CreateUserInputDTO dto) {
        return mapper.toEntity(dto);
    }
}
