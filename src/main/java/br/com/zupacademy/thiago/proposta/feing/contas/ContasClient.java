package br.com.zupacademy.thiago.proposta.feing.contas;

import br.com.zupacademy.thiago.proposta.cartao.*;
import br.com.zupacademy.thiago.proposta.cartao.avisoviagem.NotificaAvisoViagemRequest;
import br.com.zupacademy.thiago.proposta.cartao.avisoviagem.NotificaAvisoViagemResponse;
import br.com.zupacademy.thiago.proposta.cartao.bloqueio.BloqueioCartaoRequest;
import br.com.zupacademy.thiago.proposta.cartao.bloqueio.BloqueioCartaoResponse;
import br.com.zupacademy.thiago.proposta.cartao.carteira.AssociaNovaCarteiraRequest;
import br.com.zupacademy.thiago.proposta.cartao.carteira.AssociaNovaCarteiraResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "contas", url = "${contas.url.base}")
public interface ContasClient {

    @RequestMapping(method = RequestMethod.GET, path = "/api/cartoes")
    public CartaoResponse findCartaoByIdProposta(@RequestParam("idProposta") String idProposta);

    @RequestMapping(method = RequestMethod.POST, path ="/api/cartoes/{id}/bloqueios")
    public BloqueioCartaoResponse notificaBloqueioCartao(@RequestBody BloqueioCartaoRequest dto,
                                                         @PathVariable("id") String idCartao);

    @RequestMapping(method = RequestMethod.POST, path ="/api/cartoes/{id}/avisos")
    public NotificaAvisoViagemResponse notificaAvisoViagem(@RequestBody NotificaAvisoViagemRequest dto,
                                                           @PathVariable("id") String idCartao);

    @RequestMapping(method = RequestMethod.POST, path ="/api/cartoes/{id}/carteiras")
    public AssociaNovaCarteiraResponse associaCarteira(@RequestBody AssociaNovaCarteiraRequest dto,
                                                       @PathVariable("id") String idCartao);
}
