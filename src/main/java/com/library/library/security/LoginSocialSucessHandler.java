package com.library.library.security;

import com.library.library.model.Usuario;
import com.library.library.service.UsuarioService;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoginSocialSucessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    public static final String SENHA_PADRAO = "djfuihnNFDJINuhnfd4329435t4fdfgvrhg4w4";
    private final UsuarioService usuarioService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = oAuth2AuthenticationToken.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        Usuario usuario = usuarioService.obterPorEmail(email);

        if (usuario == null) {
            usuario = cadastrarUsuario(email);

        }

        authentication = new CustomAuthentication(usuario);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        super.onAuthenticationSuccess(request, response, authentication);
    }

    private Usuario cadastrarUsuario(String email) {
        Usuario usuario;
        usuario = Usuario
                .builder()
                .email(email)
                .login(obterLoginByEmail(email))
                .senha(SENHA_PADRAO)
                .roles(List.of("OPERADOR"))
                .build();
        usuarioService.salvar(usuario);
        return usuario;
    }

    private  String obterLoginByEmail(String email) {
        return email.substring(0, email.indexOf("@"));
    }
}
