package br.com.exames.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({
    "logId",
    "registrosConsultadas",
    "registrosGravadas",
    "registrosExistentes",
    "registrosComFalha",
    "registros",
})
public class CargaRegistros {

    private static final long serialVersionUID = 1L;

    @JsonProperty("logId")
    private long logId;

    @JsonProperty("registros")
    List<RegistroCarga> registros = new ArrayList<>();

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @JsonPropertyOrder({"idZoop", "situacaoCarga"})
    public static class RegistroCarga implements Serializable {

        private static final long serialVersionUID = 1L;

        @JsonProperty("idZoop")
        private String idZoop;

        @JsonProperty("situacaoCarga")
        private String situacaoCarga;
    }
}