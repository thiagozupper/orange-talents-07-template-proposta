package br.com.zupacademy.thiago.proposta.proposta;

import br.com.zupacademy.thiago.proposta.feing.analise.AnaliseClient;
import br.com.zupacademy.thiago.proposta.feing.analise.AnaliseResponse;
import br.com.zupacademy.thiago.proposta.feing.analise.AnaliseRequest;
import br.com.zupacademy.thiago.proposta.feing.analise.StatusAnalise;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/propostas")
public class PropostaController {

    private final PropostaRepository propostaRepository;
    private final AnaliseClient analiseClient;
    private final TransactionTemplate tx;

    public PropostaController(PropostaRepository propostaRepository, AnaliseClient analiseClient,
                              TransactionTemplate tx) {
        this.propostaRepository = propostaRepository;
        this.analiseClient = analiseClient;
        this.tx = tx;
    }

    @PostMapping
    public ResponseEntity<?> novaProposta(@RequestBody @Valid NovaPropostaRequest dto,
                                          UriComponentsBuilder uriBuilder) {

        if (propostaRepository.existsByDocumento(dto.getDocumento())) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        Proposta proposta = dto.toProposta(StatusProposta.PENDENTE_DE_ANALISE);
        tx.executeWithoutResult(status -> propostaRepository.save(proposta));

        AnaliseRequest analiseRequest = proposta.toAnaliseRequest();
        try {
            AnaliseResponse analiseResponse = analiseClient.solicitarAnalise(analiseRequest);
            proposta.setStatus(StatusProposta.ELEGIVEL);
            tx.executeWithoutResult(status -> propostaRepository.save(proposta));
        } catch(FeignException.UnprocessableEntity ex) {
            proposta.setStatus(StatusProposta.NAO_ELEGIVEL);
            tx.executeWithoutResult(status -> propostaRepository.save(proposta));
        }

        URI uri = uriBuilder.path("/api/propostas/{id}")
                    .buildAndExpand(proposta.getId()).toUri();

        return ResponseEntity.ok().location(uri).build();
    }
}
