package br.com.fiap.aquasense.dto.response;

import lombok.Data;

@Data
public class TemperaturaApiResponse {
    private Current current;

    @Data
    public static class Current {
        private double temp;
        private int humidity;
        private int pressure;
        private long dt;
    }
}