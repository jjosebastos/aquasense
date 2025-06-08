package br.com.fiap.aquasense.model;

import br.com.fiap.aquasense.model.enums.TipoRisco;

import java.time.LocalDateTime;

public record AreaRiscoFilter (
    TipoRisco tipoRisco,
    LocalDateTime dataAtualizacaoInicial,
    LocalDateTime dataAtualizacaoFinal
){
}
