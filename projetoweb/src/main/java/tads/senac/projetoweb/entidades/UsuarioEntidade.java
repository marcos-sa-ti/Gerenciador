package tads.senac.projetoweb.entidades;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;



@Entity
public class UsuarioEntidade {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
	@Column
	@NotBlank
	private String nomecompleto;
	
	@Column
	@NotBlank
	private String usuario;
	
	@Column
	@NotBlank
	private String senha;
	
	
	@Column
	private String status;
	
	
	@Column
	private LocalDateTime dataehoraCadastro;
	
	@Transient
	private transient String papelTemp;
	
	
	// Um usuario tem varios papeis
	@OneToMany(mappedBy = "usuariorelacao")
	private Set<PapeisEntidade> papeis;
	
	public String getPapelTemp() {
		return papelTemp;
	}

	public void setPapelTemp(String papelTemp) {
		this.papelTemp = papelTemp;
	}

	public Set<PapeisEntidade> getPapeis() {
		return papeis;
	}

	public void setPapeis(Set<PapeisEntidade> papeis) {
		this.papeis = papeis;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomecompleto() {
		return nomecompleto;
	}

	public void setNomecompleto(String nomecompleto) {
		this.nomecompleto = nomecompleto;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getDataehoraCadastro() {
		return dataehoraCadastro;
	}

	public void setDataehoraCadastro(LocalDateTime dataehoraCadastro) {
		this.dataehoraCadastro = dataehoraCadastro;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	



}
