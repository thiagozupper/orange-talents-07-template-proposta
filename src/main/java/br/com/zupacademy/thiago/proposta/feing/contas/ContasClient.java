package br.com.zupacademy.thiago.proposta.feing.contas;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "contas", url = "${contas.url.base}")
public interface ContasClient {

    @RequestMapping(method = RequestMethod.GET, path = "/api/cartoes")
    public CartaoResponse findCartaoByIdProposta(@RequestParam("idProposta") String idProposta);
}
