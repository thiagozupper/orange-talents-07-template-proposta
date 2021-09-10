package br.com.zupacademy.thiago.proposta.cartao;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartaoRepository extends CrudRepository<Cartao, String> {
    List<Cartao> findTop5ByStatusEquals(int codigo);
}
