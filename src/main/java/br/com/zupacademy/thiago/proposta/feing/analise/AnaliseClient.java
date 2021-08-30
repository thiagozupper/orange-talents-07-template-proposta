package br.com.zupacademy.thiago.proposta.feing.analise;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "analise", url = "${analise.url.base}")
public interface AnaliseClient {

    @RequestMapping(method = RequestMethod.POST, path = "/api/solicitacao")
    public AnaliseResponse solicitarAnalise(AnaliseRequest analiseRequest);
}
