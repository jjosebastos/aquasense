package br.com.fiap.aquasense.dto.response;

import br.com.fiap.aquasense.model.enums.StatusLocalizacao;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


public record LocalizacaoResponse (
        Long idLocalizacao,
        String nome,
        Double latitude,
        Double longitude,
        LocalDateTime dataUltimaAtualizacao,
        StatusLocalizacao status,
        Long idUsuario

){ }
