package br.com.zupacademy.thiago.proposta.cartao;

import br.com.zupacademy.thiago.proposta.cartao.avisoviagem.AvisoViagem;
import br.com.zupacademy.thiago.proposta.cartao.avisoviagem.AvisoViagemRepository;
import br.com.zupacademy.thiago.proposta.cartao.avisoviagem.AvisoViagemRequest;
import br.com.zupacademy.thiago.proposta.cartao.avisoviagem.NotificaAvisoViagemRequest;
import br.com.zupacademy.thiago.proposta.cartao.bloqueio.Bloqueio;
import br.com.zupacademy.thiago.proposta.cartao.bloqueio.BloqueioRepository;
import br.com.zupacademy.thiago.proposta.cartao.carteira.AssociaNovaCarteiraRequest;
import br.com.zupacademy.thiago.proposta.cartao.carteira.Carteira;
import br.com.zupacademy.thiago.proposta.cartao.carteira.CarteiraRepository;
import br.com.zupacademy.thiago.proposta.feing.contas.ContasClient;
import feign.FeignException;
import org.jboss.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/cartoes")
public class CartaoController {

    Logger logger = Logger.getLogger(CartaoController.class);

    private final TransactionTemplate tx;
    private final BloqueioRepository bloqueioRepository;
    private final CartaoRepository cartaoRepository;
    private final AvisoViagemRepository avisoViagemRepository;
    private final CarteiraRepository carteiraRepository;
    private final ContasClient contasClient;

    public CartaoController(TransactionTemplate tx, BloqueioRepository bloqueioRepository,
                            CartaoRepository cartaoRepository, AvisoViagemRepository avisoViagemRepository,
                            CarteiraRepository carteiraRepository, ContasClient contasClient) {
        this.tx = tx;
        this.bloqueioRepository = bloqueioRepository;
        this.cartaoRepository = cartaoRepository;
        this.avisoViagemRepository = avisoViagemRepository;
        this.carteiraRepository = carteiraRepository;
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
            contasClient.notificaAvisoViagem(new NotificaAvisoViagemRequest(avisoViagemRequest), idCartao);
            AvisoViagem avisoViagem = avisoViagemRequest.toAvisoViagem(ipCliente, userAgent, cartao);
            tx.executeWithoutResult(status -> avisoViagemRepository.save(avisoViagem));
        } catch (FeignException ex) {
            logger.info("Falha ao notificar aviso de viagem: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falha ao criar aviso de viagem");
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("{idCartao}/associar-carteira")
    public ResponseEntity<?> associarCarteira(@PathVariable String idCartao,
                     @RequestBody @Valid AssociaNovaCarteiraRequest request, UriComponentsBuilder uriBuilder) {

        Optional<Cartao> optionalCartao = cartaoRepository.findById(idCartao);
        if (optionalCartao.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cartão não encontrado");
        }

        Cartao cartao = optionalCartao.get();
        Optional<Carteira> optionalCarteira = carteiraRepository
                .findByCartaoAndCarteiraEquals(cartao, request.getCarteira().getCodigo());
        if (optionalCarteira.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("Cartão já associado a carteira " + request.getCarteira());
        }

        try {
            contasClient.associaCarteira(request, idCartao);
            Carteira carteira = request.toCarteira(cartao);
            tx.executeWithoutResult(status -> carteiraRepository.save(carteira));
            URI uri = uriBuilder.path("/api/cartoes/carteiras/{id}").buildAndExpand(carteira.getId()).toUri();
            return ResponseEntity.created(uri).build();
        } catch (FeignException ex) {
            logger.info("Falha ao associar carteira: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falha ao associar carteira");
        }

    }

}
