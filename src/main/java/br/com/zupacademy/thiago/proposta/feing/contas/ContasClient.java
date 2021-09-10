package br.com.zupacademy.thiago.proposta.feing.contas;

import br.com.zupacademy.thiago.proposta.cartao.BloqueioCartaoRequest;
import br.com.zupacademy.thiago.proposta.cartao.BloqueioCartaoResponse;
import br.com.zupacademy.thiago.proposta.cartao.CartaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "contas", url = "${contas.url.base}")
public interface ContasClient {

    @RequestMapping(method = RequestMethod.GET, path = "/api/cartoes")
    public CartaoResponse findCartaoByIdProposta(@RequestParam("idProposta") String idProposta);

    @RequestMapping(method = RequestMethod.POST, path ="/api/cartoes/{id}/bloqueios")
    public BloqueioCartaoResponse notificaBloqueioCartao(@RequestBody BloqueioCartaoRequest dto,
                                                         @PathVariable("id") String idCartao);
}
