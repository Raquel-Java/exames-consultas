package br.com.exames.infrastructure.anotation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.exames.infrastructure.anotation.custom.CustomBooleanDeserializer;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = CustomBooleanDeserializer.class)
public class ConvertBoolean {

}
