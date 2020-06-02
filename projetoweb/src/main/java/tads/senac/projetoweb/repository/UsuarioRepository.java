package tads.senac.projetoweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tads.senac.projetoweb.entidades.UsuarioEntidade;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntidade, Integer> {

}
