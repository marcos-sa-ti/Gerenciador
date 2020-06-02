package tads.senac.projetoweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tads.senac.projetoweb.entidades.PapeisEntidade;

@Repository
public interface PapeisRepository extends JpaRepository<PapeisEntidade, Integer> {

}
