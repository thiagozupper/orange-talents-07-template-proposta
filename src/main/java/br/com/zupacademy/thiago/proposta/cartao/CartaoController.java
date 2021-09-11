package br.com.zupacademy.thiago.proposta.cartao;

import br.com.zupacademy.thiago.proposta.feing.contas.ContasClient;
import feign.FeignException;
import org.jboss.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/cartoes")
public class CartaoController {

    Logger logger = Logger.getLogger(CartaoController.class);

    private final TransactionTemplate tx;
    private final BloqueioRepository bloqueioRepository;
    private final CartaoRepository cartaoRepository;
    private final AvisoViagemRepository avisoViagemRepository;
    private final ContasClient contasClient;

    public CartaoController(TransactionTemplate tx, BloqueioRepository bloqueioRepository,
                            CartaoRepository cartaoRepository, AvisoViagemRepository avisoViagemRepository,
                            ContasClient contasClient) {
        this.tx = tx;
        this.bloqueioRepository = bloqueioRepository;
        this.cartaoRepository = cartaoRepository;
        this.avisoViagemRepository = avisoViagemRepository;
        this.contasClient = contasClient;
    }

    @GetMapping("{idCartao}/bloqueio")
    @Transactional
    public ResponseEntity<?> bloquearCartao(
            @PathVariable String idCartao, @RequestHeader("User-Agent") String userAgent,
                                @RequestHeader("X-Forwarded-For") String ipCliente) {

        Optional<Cartao> optionalCartao = cartaoRepository.findById(idCartao);
        if (optionalCartao.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cartão não encontrado");
        }

        Cartao cartao = optionalCartao.get();
        if (cartao.isBloqueadoOuBloqueioSolicitado()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("Cartão já está em situação de bloqueio");
        }

        Bloqueio bloqueio = new Bloqueio(ipCliente, userAgent, cartao);
        bloqueioRepository.save(bloqueio);

        cartao.bloqueioSolicitado();
        cartaoRepository.save(cartao);

        return ResponseEntity.ok().build();
    }

    @PostMapping("{idCartao}/aviso-viagem")
    public ResponseEntity<?> avisoViagem(@PathVariable String idCartao,
            @RequestHeader("User-Agent") String userAgent,
            @RequestHeader("X-Forwarded-For") String ipCliente,
            @RequestBody @Valid AvisoViagemRequest avisoViagemRequest) {

        Optional<Cartao> optionalCartao = cartaoRepository.findById(idCartao);
        if (optionalCartao.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cartão não encontrado");
        }

        Cartao cartao = optionalCartao.get();

        try {
            contasClient.notificaAvisoViagem(new NotificaAvisoViagemRequest(avisoViagemRequest), cartao.getId());
            AvisoViagem avisoViagem = avisoViagemRequest.toAvisoViagem(ipCliente, userAgent, cartao);
            tx.executeWithoutResult(status -> avisoViagemRepository.save(avisoViagem));
        } catch (FeignException ex) {
            logger.info("Falha ao notificar aviso de viagem: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falha ao criar aviso de viagem");
        }

        return ResponseEntity.ok().build();
    }
}
