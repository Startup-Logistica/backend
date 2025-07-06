package br.portela.startuplogistica.usecases.auth;

import br.portela.startuplogistica.config.annotations.UseCase;
import br.portela.startuplogistica.dtos.auth.input.LoginInputDTO;
import br.portela.startuplogistica.dtos.auth.output.LoginOutputDTO;
import br.portela.startuplogistica.errors.ExceptionCode;
import br.portela.startuplogistica.errors.exceptions.UnauthorizedException;
import br.portela.startuplogistica.security.dto.UserDetailsDTO;
import br.portela.startuplogistica.security.services.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@UseCase
@RequiredArgsConstructor
public class LoginUseCase {
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;

    public LoginOutputDTO execute(LoginInputDTO input){
        var usernamePassword = new UsernamePasswordAuthenticationToken(input.getEmail().trim(), input.getPassword());
        var auth = authenticationManager.authenticate(usernamePassword);
        var userDetails = (UserDetailsDTO) auth.getPrincipal();

        if (!userDetails.getUser().isActive())
            throw new UnauthorizedException(ExceptionCode.BAD_CREDENTIALS);

        var token = jwtTokenService.generateToken(userDetails);
        return new LoginOutputDTO(token);
    }
}
