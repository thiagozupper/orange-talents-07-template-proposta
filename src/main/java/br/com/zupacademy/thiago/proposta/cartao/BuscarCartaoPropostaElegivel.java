package br.com.zupacademy.thiago.proposta.cartao;

import br.com.zupacademy.thiago.proposta.feing.contas.ContasClient;
import br.com.zupacademy.thiago.proposta.proposta.Proposta;
import br.com.zupacademy.thiago.proposta.proposta.PropostaRepository;
import br.com.zupacademy.thiago.proposta.proposta.StatusProposta;
import feign.FeignException;
import org.jboss.logging.Logger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Component
@EnableScheduling
public class BuscarCartaoPropostaElegivel {

    Logger logger = Logger.getLogger(BuscarCartaoPropostaElegivel.class);

    private final long SEGUNDO = 1000;

    private final TransactionTemplate tx;
    private final PropostaRepository propostaRepository;
    private final CartaoRepository cartaoRepository;
    private final ContasClient contasClient;

    public BuscarCartaoPropostaElegivel(TransactionTemplate tx, PropostaRepository propostaRepository,
                                        CartaoRepository cartaoRepository, ContasClient contasClient) {
        this.tx = tx;
        this.propostaRepository = propostaRepository;
        this.cartaoRepository = cartaoRepository;
        this.contasClient = contasClient;
    }

    @Scheduled(fixedDelay = 30 * SEGUNDO)
    public void associarCartaoPropostaElegivel() {

        List<Proposta> propostas = propostaRepository
                .findTop5ByNumeroCartaoIsNullAndStatusEquals(StatusProposta.ELEGIVEL.getCodigo());

        for (Proposta proposta : propostas) {
            try {
                CartaoResponse cartaoResponse = contasClient.findCartaoByIdProposta(proposta.getId().toString());
                proposta.associarNumeroCartao(cartaoResponse);
                Cartao cartao = cartaoResponse.toCartao();
                tx.executeWithoutResult(status -> {
                    propostaRepository.save(proposta);
                    cartaoRepository.save(cartao);
                });
            } catch(FeignException ex) {
                logger.info("Falha ao buscar cartão da proposta de id " + proposta.getId());
            }
        }
    }

}
