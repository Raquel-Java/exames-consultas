package br.com.exames.infrastructure.exception;

import java.io.Serializable;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(value = Include.NON_EMPTY)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiErroModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private HttpStatus status;

    private String descricao;

    private String timestamp;

    private String erro;

    private List<String> erros;

    private transient Object retorno;
}
