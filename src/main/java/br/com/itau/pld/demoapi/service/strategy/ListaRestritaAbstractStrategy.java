package br.com.itau.pld.demoapi.service.strategy;

import br.com.itau.pld.demoapi.model.ListaRestritiva;
import br.com.itau.pld.demoapi.service.cache.ListaRestritaRedisCacheComponent;

import java.util.Optional;

public abstract class ListaRestritaAbstractStrategy implements ListaRestritaStrategy {

    private ListaRestritaRedisCacheComponent cacheComponent;

    public ListaRestritaAbstractStrategy(ListaRestritaRedisCacheComponent cacheComponent) {
        this.cacheComponent = cacheComponent;
    }

    @Override
    public Optional<ListaRestritiva> buscaPorDocEProduto(Long doc) {
	return cacheComponent.findById(doc);
    }
}
