package br.com.zupacademy.thiago.proposta.cartao;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Optional;

@RestController
@RequestMapping("/api/cartoes")
public class CartaoController {

    private final BloqueioRepository bloqueioRepository;
    private final CartaoRepository cartaoRepository;

    public CartaoController(BloqueioRepository bloqueioRepository, CartaoRepository cartaoRepository) {
        this.bloqueioRepository = bloqueioRepository;
        this.cartaoRepository = cartaoRepository;
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
}
