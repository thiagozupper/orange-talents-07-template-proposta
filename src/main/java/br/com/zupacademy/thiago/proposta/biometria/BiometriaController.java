package br.com.zupacademy.thiago.proposta.biometria;

import br.com.zupacademy.thiago.proposta.cartao.Cartao;
import br.com.zupacademy.thiago.proposta.cartao.CartaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/biometrias")
public class BiometriaController {

    private final BiometriaRepository biometriaRepository;
    private final CartaoRepository cartaoRepository;

    public BiometriaController(BiometriaRepository biometriaRepository,
                               CartaoRepository cartaoRepository) {
        this.biometriaRepository = biometriaRepository;
        this.cartaoRepository = cartaoRepository;
    }

    @PostMapping("/{idCartao}")
    @Transactional
    public ResponseEntity<?> novaBiometria(@PathVariable String idCartao,
                  @RequestBody @Valid BiometriaRequest biometriaRequest,
                   UriComponentsBuilder uriBuilder) {

        Optional<Cartao> cartaoOptional = this.cartaoRepository.findById(idCartao);
        if (cartaoOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cartão não encontrado");
        }

        Cartao cartao = cartaoOptional.get();

        Biometria biometria = biometriaRequest.toBiometria(cartao);
        biometriaRepository.save(biometria);

        URI uri = uriBuilder.path("/api/biometrias/{id}")
                .buildAndExpand(biometria.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
