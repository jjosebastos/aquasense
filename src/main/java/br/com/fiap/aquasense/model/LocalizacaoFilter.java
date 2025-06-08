package br.com.fiap.aquasense.model;

import br.com.fiap.aquasense.model.enums.StatusLocalizacao;

public record LocalizacaoFilter (
        String nomeLocal,
        StatusLocalizacao statusLocalizacao
){
}
