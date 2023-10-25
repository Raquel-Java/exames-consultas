package br.com.exames.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExameDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
//	@JsonProperty("id")
//	@EqualsAndHashCode.Include
//	private String id;

	@JsonProperty("nome")
	private String nome;

	@JsonProperty("cod_exame")
	@EqualsAndHashCode.Include
	private String codExame;

	@JsonProperty("dt_realizacao_exame")
	private LocalDateTime dtRealizacaoExame;

	@JsonProperty("crm_medico_examinador")
	private String crmMedicoExaminador;

	@JsonProperty("parte_corpo")
	private String parteCorpo;

	@JsonProperty("indicacao")
	private String indicacao;

	@JsonProperty("cpf_paciente")
	private String cpfPaciente;

	@JsonProperty("dt_criacao_registro")
	private LocalDateTime dtCriacaoRegistro;

	@JsonProperty("dt_alteracao_registro")
	private LocalDateTime dtAlteracaoRegistro;

	@JsonProperty("nome_usuario")
	private String nmUsuario;
	
	@JsonProperty("url")
	private String url;
	
	@JsonProperty("nome_hash")
	private String nomeHash;

}
