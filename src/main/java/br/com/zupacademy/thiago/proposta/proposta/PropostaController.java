package br.com.zupacademy.thiago.proposta.proposta;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/propostas")
public class PropostaController {

    private final PropostaRepository propostaRepository;

    public PropostaController(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> novaProposta(@RequestBody @Valid NovaPropostaRequest dto,
                                          UriComponentsBuilder uriBuilder) {

        Optional<Proposta> optional = propostaRepository.findByDocumento(dto.getDocumento());
        if (optional.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(Map.of("codigo","422","descricao","Já existe proposta com esse documento"));
        }

        Proposta proposta = dto.toProposta();
        propostaRepository.save(proposta);
        URI uri = uriBuilder.path("/api/propostas/{id}")
                    .buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.ok().location(uri).build();
    }
}
