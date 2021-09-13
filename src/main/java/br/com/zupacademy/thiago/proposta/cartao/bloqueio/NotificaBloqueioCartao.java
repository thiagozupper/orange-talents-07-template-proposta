package br.com.zupacademy.thiago.proposta.cartao.bloqueio;

import br.com.zupacademy.thiago.proposta.cartao.Cartao;
import br.com.zupacademy.thiago.proposta.cartao.CartaoRepository;
import br.com.zupacademy.thiago.proposta.cartao.StatusCartao;
import br.com.zupacademy.thiago.proposta.feing.contas.ContasClient;
import feign.FeignException;
import io.opentracing.Tracer;
import org.jboss.logging.Logger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Component
@EnableScheduling
public class NotificaBloqueioCartao {

    Logger logger = Logger.getLogger(NotificaBloqueioCartao.class);

    private final long SEGUNDO = 1000;

    private final TransactionTemplate tx;
    private final CartaoRepository cartaoRepository;
    private final ContasClient contasClient;
    private final Tracer tracer;

    public NotificaBloqueioCartao(TransactionTemplate tx, CartaoRepository cartaoRepository,
                                  ContasClient contasClient, Tracer tracer) {
        this.tx = tx;
        this.cartaoRepository = cartaoRepository;
        this.contasClient = contasClient;
        this.tracer = tracer;
    }

    @Scheduled(fixedDelay = 60 * SEGUNDO)
    public void notificaBloqueioCartao() {

        List<Cartao> cartoes = cartaoRepository
                .findTop5ByStatusEquals(StatusCartao.BLOQUEIO_SOLICITADO.getCodigo());

        for (Cartao cartao : cartoes) {
            try {
                logarTentativaOpenTracing(cartao.getId());
                contasClient.notificaBloqueioCartao(new BloqueioCartaoRequest("Propostas"),cartao.getId());
                tx.executeWithoutResult(status -> {
                    cartao.bloquear();
                    cartaoRepository.save(cartao);
                });
            } catch(FeignException ex) {
                logger.info("Falha ao informar bloqueio do cartão de id: " + cartao.getId());
            }
        }
    }

    private void logarTentativaOpenTracing(String idProposta) {
        tracer.activeSpan().log("Tentativa bloqueio cartao de numero " + idProposta);
    }

}
