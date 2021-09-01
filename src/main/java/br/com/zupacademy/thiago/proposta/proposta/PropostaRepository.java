package br.com.zupacademy.thiago.proposta.proposta;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PropostaRepository extends CrudRepository<Proposta, Long> {

    boolean existsByDocumento(String documento);
    List<Proposta> findTop5ByNumeroCartaoIsNullAndStatusEquals(int status);
}
