package tads.senac.projetoweb.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PapeisEntidade {
	
	
	@Id
	@GeneratedValue ( strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
	@Column
	private String papel;
	
	// Um usuario pode ter varios papeis
	@ManyToOne
	@JoinColumn (name = "UsuarioEntidade_id")
	private UsuarioEntidade usuariorelacao;
	
	
	
	public UsuarioEntidade getUsuariorelacao() {
		return usuariorelacao;
	}

	public void setUsuariorelacao(UsuarioEntidade usuariorelacao) {
		this.usuariorelacao = usuariorelacao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPapel() {
		return papel;
	}

	public void setPapel(String papel) {
		this.papel = papel;
	}
	
	
	
	
}
