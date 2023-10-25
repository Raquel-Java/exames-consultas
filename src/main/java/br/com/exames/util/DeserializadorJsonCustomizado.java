package br.com.exames.util;

import br.com.exames.infrastructure.exception.ApiException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DeserializadorJsonCustomizado {

    /* ------------------------------------------------------------------------------------------------------*/

    public static class LocalDateTimeDeserializador extends JsonDeserializer<LocalDateTime> {
        private static final DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            String dateTimeString = p.getText();
            String nomeCampo = p.getCurrentName();
            try {
                return LocalDateTime.parse(dateTimeString, formatter);
            } catch (Exception e) {
                throw new ApiException(
                        String.format(
                                "Campo '%s' inválido, insira uma data/hora no padrão 'yyyy-MM-dd HH:mm:ss'",
                                p.getCurrentName()));
            }
        }
    }

    /* ------------------------------------------------------------------------------------------------------*/

    public static class LocalDateDeserializador extends JsonDeserializer<LocalDate> {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        @Override
        public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            String dateTimeString = p.getText();
            String nomeCampo = p.getCurrentName();
            try {
                return LocalDate.parse(dateTimeString, formatter);
            } catch (Exception e) {
                throw new ApiException(
                        String.format(
                                "Campo '%s' inválido, insira uma data no padrão 'yyyy-MM-dd'", p.getCurrentName()));
            }
        }
    }

    /* ------------------------------------------------------------------------------------------------------*/

}
