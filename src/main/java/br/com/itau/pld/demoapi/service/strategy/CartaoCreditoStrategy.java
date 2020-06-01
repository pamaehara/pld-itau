package br.com.itau.pld.demoapi.service.strategy;

import br.com.itau.pld.demoapi.service.cache.ListaRestritaRedisCacheComponent;
import org.springframework.stereotype.Component;

@Component
public class CartaoCreditoStrategy extends ListaRestritaAbstractStrategy {

    public CartaoCreditoStrategy(ListaRestritaRedisCacheComponent cacheComponent) {
	super(cacheComponent);
    }
}
