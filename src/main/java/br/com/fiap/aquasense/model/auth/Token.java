package br.com.fiap.aquasense.model.auth;

public record Token(
        String token,
        String username
) {
}
