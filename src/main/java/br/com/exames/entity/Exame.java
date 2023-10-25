package br.com.exames.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

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
@Entity(name = "exames")
public class Exame {
	
	    @Id
	    @Column(name = "id")
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	    @Column(name = "nome_exame")
	    private String nome;
	    
	    @Column(name = "nome_hash")
	    private String nomeHash;
	    
	    @Column(name = "cod_exame")
	    private String codExame;
	    
	    @Column(name = "dt_realizacao_exame")
		private LocalDateTime dtRealizacaoExame;
	    
	    @Column(name = "crm_medico_examinador")
	    private String crmMedicoExaminador;
	    
	    @Column(name = "parte_corpo")
	    private String parteCorpo;
	    
	    @Column(name = "indicacao")
	    private String indicacao;
	    
	    @Column(name = "cpf_paciente")
	    private String cpfPaciente;
	    
	    @Column(nullable = false, updatable = false,name = "dt_criacao_registro")
 		@CreationTimestamp
		private LocalDateTime dtCriacaoRegistro;

	    @Column(nullable = false, updatable = false,name = "dt_alteracao_registro")
	    @CreationTimestamp
		private LocalDateTime dtAlteracaoRegistro;
	    
	    @Column(name = "nome_usuario")
	    private String nmUsuario;
	    
	    @Column(name = "url")
	    private String url;
	    
  
}