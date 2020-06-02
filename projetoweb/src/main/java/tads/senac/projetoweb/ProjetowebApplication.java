package tads.senac.projetoweb;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import tads.senac.projetoweb.entidades.PapeisEntidade;
import tads.senac.projetoweb.entidades.UsuarioEntidade;
import tads.senac.projetoweb.repository.PapeisRepository;
import tads.senac.projetoweb.repository.UsuarioRepository;

@SpringBootApplication
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class ProjetowebApplication {

	@Autowired
	private UsuarioRepository usuariorepository;

	@Autowired
	private PapeisRepository papeisrepository;

@EventListener  
public void tratarIniciouAtuali (ContextRefreshedEvent event ) {

	if (usuariorepository.count() == 0) {
		// Salva os dados setados no banco de dados
		UsuarioEntidade novousuario = new UsuarioEntidade();
		novousuario.setNomecompleto("Fulano da Silva");
		novousuario.setSenha("senhateste");
		novousuario.setUsuario("usuario.fulano");
		novousuario.setStatus("Ativado");
		novousuario.setDataehoraCadastro(LocalDateTime.now());
		
		
		//Salvar o contato e seus respectivos papeis
		novousuario = usuariorepository.save(novousuario);
		
		// Salva os dados de um papel no banco de dados
		PapeisEntidade novopapel = new PapeisEntidade();
		novopapel.setPapel("Menor Aprendiz");
		
	
		novopapel.setUsuariorelacao(novousuario);
		novousuario.setPapeis(new HashSet<>(Arrays.asList(novopapel)));
		
		//Salva os dados de um papel no banco
		novopapel = papeisrepository.save(novopapel);
		
		
		
		
		
		





}

}

	@Autowired
	public static void main(String[] args) {
		SpringApplication.run(ProjetowebApplication.class, args);
	}




}