package br.com.exames.dto;

import java.util.Optional;

public class ResponseObject {
	
	private String mensagem;
    private Long status;
    private Object objeto;
    
    public ResponseObject(String message, Long entityId) {
		this.mensagem = message;
		this.status = entityId;
		this.objeto = new Object();
	}
    
	public ResponseObject(String message, Long entityId, Object obj) {
		this.mensagem = message;
		this.status = entityId;
		this.objeto = obj;
	}

	public ResponseObject() {

	}
	
	public String getSucesso(Object o) {
		this.setEntityId(200L);
		this.setMessage("Exame Salvo com sucesso! ");
		this.setObj(o);
		return mensagem;
	}
	
	public String getErro(Object o) {
		this.setEntityId(422L);
		this.setMessage("Erro no processo !");
		this.setObj(o);
		return mensagem;
	}

	public String getMessage() {
		return mensagem;
	}

	public void setMessage(String message) {
		this.mensagem = message;
	}

	public Long getEntityId() {
		return status;
	}

	public void setEntityId(Long entityId) {
		this.status = entityId;
	}

	public Object getObj() {
		return objeto;
	}

	public void setObj(Object obj) {
		this.objeto = obj;
	}

	public Optional<ResponseObject> map(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
