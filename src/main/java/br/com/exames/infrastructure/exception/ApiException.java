package br.com.exames.infrastructure.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private HttpStatus status;

    private String artefato;

    /* ------------------------------------------------------------------------------------------------------*/

    public ApiException(String mensagem) {
        super(mensagem);
    }

    public ApiException(String mensagem, HttpStatus status) {
        super(mensagem);
        artefato = null;
        this.status = status;
    }

    /* ------------------------------------------------------------------------------------------------------*/

    public ApiException(Throwable causa) {
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        artefato = causa.getMessage();
        ApiException.resolveCausa(causa);
    }

    /* ------------------------------------------------------------------------------------------------------*/

    public ApiException(String mensagem, String artefato, HttpStatus status) {
        super(mensagem);
        this.artefato = artefato;
        this.status = status;
    }

    /* ------------------------------------------------------------------------------------------------------*/

    public static void resolveCausa(Throwable cause) {

       // UtilException.processarException(cause, true);
    }

    /* ------------------------------------------------------------------------------------------------------*/
}
