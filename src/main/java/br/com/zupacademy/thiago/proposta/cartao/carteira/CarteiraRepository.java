package br.com.zupacademy.thiago.proposta.cartao.carteira;

import br.com.zupacademy.thiago.proposta.cartao.Cartao;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CarteiraRepository extends CrudRepository<Carteira, Long> {

    Optional<Carteira> findByCartaoAndCarteiraEquals(Cartao cartao, int codigo);
}
