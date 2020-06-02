package tads.senac.projetoweb.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tads.senac.projetoweb.entidades.PapeisEntidade;
import tads.senac.projetoweb.entidades.UsuarioEntidade;
import tads.senac.projetoweb.repository.PapeisRepository;
import tads.senac.projetoweb.repository.UsuarioRepository;

//Entrada de dados
@Controller
@RequestMapping("/projetoweb")
public class AgendaController {
	
	@Autowired
	private UsuarioRepository usuariorepositorio;
	
	@Autowired
	private PapeisRepository papeisrepositorio;
	
	//Consulta dos dados cadastrados
	@GetMapping
	public ModelAndView listardados() {
		
		List<UsuarioEntidade> listadeusuarios = usuariorepositorio.findAll();
		ModelAndView mv1 = new ModelAndView("lista-template");
		mv1.addObject("itens", listadeusuarios);
		return mv1;	
	}
	
	// 	Referencia para a pagina de abertura de um novo 
	// cadastro de um usuario no sistema
	@GetMapping("/incluirusuario")
	public ModelAndView incluirusuario() {
		
		ModelAndView mv2 = new ModelAndView("cadastro-template");
		UsuarioEntidade novocontato = new UsuarioEntidade();
		//novocontato.setPapeis(new HashSet<PapeisEntidade>(Arrays.asList(new PapeisEntidade())));
		mv2.addObject("item", novocontato );
		return mv2;
		
	}
	
	
	@PostMapping("/salvarusuario")
	@Transactional
	public String salvarusuario (@ModelAttribute("item") @Valid UsuarioEntidade usuarioEnviado, BindingResult bd ,RedirectAttributes redattr) {
		
		
		if(bd.hasErrors()) {
			// Se tiver erro ao preencher o formulario
			// Vai recarregar a página apresentando os erros encontrados
			return  "cadastro-template";
		}
		
		if (usuarioEnviado.getId() != null) 
		
		{
				Optional<UsuarioEntidade> optuser = usuariorepositorio.findById(usuarioEnviado.getId());

					if (optuser.isPresent()) 
					{
							// Atualiza informação
							UsuarioEntidade ue = optuser.get();
							ue.setUsuario(usuarioEnviado.getUsuario());
							ue.setNomecompleto(usuarioEnviado.getNomecompleto());
							ue.setSenha(usuarioEnviado.getSenha());
							ue.setStatus(usuarioEnviado.getStatus());
							ue = usuariorepositorio.save(ue);
							Set<PapeisEntidade> papeis = ue.getPapeis();

								if (papeis != null && !papeis.isEmpty())
									{
										for (PapeisEntidade pe : papeis)
											{
												pe.setPapel(usuarioEnviado.getPapelTemp());
												pe = papeisrepositorio.save(pe);
											}
									}

					} 
					
					else 
					{
						redattr.addFlashAttribute("msgErro", "O usuario informado não existe");
						return "redirect:/crud";
					}
		} 
		
		
			else 
				{
					// Incluir novo contato
					usuarioEnviado.setDataehoraCadastro(LocalDateTime.now());
					usuarioEnviado = usuariorepositorio.save(usuarioEnviado);
					PapeisEntidade pe = new PapeisEntidade();
			
			
					pe.setPapel(usuarioEnviado.getPapelTemp());
					pe.setPapel("Papel Teste");
			
					pe.setUsuariorelacao(usuarioEnviado);
					pe = papeisrepositorio.save(pe);
					usuarioEnviado.setPapeis(new HashSet<>(Arrays.asList(pe)));

				}

				redattr.addAttribute("msgSucesso", "Usuário cadastrado com sucesso");
				return "redirect:/crud";		
				
		
		// Chave Final
	}
		
	
	
	//Link que vai abrir o formulario para a edicao de um usuario
	@GetMapping("/editar/{id}")
	public ModelAndView editar(@PathVariable ("id") Integer id) {
		
		Optional<UsuarioEntidade>  optnovocontato = usuariorepositorio.findById(id);
		
		if (optnovocontato.isPresent()) {
			ModelAndView mv3 = new ModelAndView ("cadastro-template");
			UsuarioEntidade user = optnovocontato.get();
			Set<PapeisEntidade> papeis = user.getPapeis();
			
			for (PapeisEntidade pe : papeis) {
				
				user.setPapelTemp(pe.getPapel());
				
			}  
			mv3.addObject("item", user);
			return mv3;
		}
		else {
			// Usuario não existe
			ModelAndView mv3 = new ModelAndView("lista-template");
			mv3.addObject("msgErro", "O contato não está cadastrado");
			return mv3;
			
		}
	 	
	}
	
	
	
	// Excluir o usuario do sistema
	@PostMapping("/removeruser/{id}")
	@Transactional
	public String remover(@PathVariable("id") Integer id, RedirectAttributes redattr ) {
		
		Optional<UsuarioEntidade> optuser = usuariorepositorio.findById(id);
		if( !optuser.isPresent()) {
			// O contato não foi encontrado
			redattr.addFlashAttribute("msgErro", "O contato não foi encontrado");
			return "redirect:/projetoweb";
		}
		
		UsuarioEntidade ue = optuser.get();
		papeisrepositorio.deleteAll(ue.getPapeis());
		usuariorepositorio.delete(ue);
		
		redattr.addFlashAttribute("msgSucesso", "O contato foi removido com sucesso");
		return "redirect/agenda";
		
		
	}
	
	
	// Listar os papeis cadastrados no banco de dados quando a pagina de cadastro abrir
	@ModelAttribute ("listarPapeis")
	public List<PapeisEntidade> listarPapeis(){
		return papeisrepositorio.findAll();
	}
	
	
	// Chave final
}



 