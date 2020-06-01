package br.com.itau.pld.demoapi.service;

import br.com.itau.pld.demoapi.model.ListaRestritiva;
import br.com.itau.pld.demoapi.repository.ListaRestritaRepository;
import br.com.itau.pld.demoapi.service.cache.ListaRestritaRedisCacheComponent;
import br.com.itau.pld.demoapi.service.strategy.CartaoCreditoStrategy;
import br.com.itau.pld.demoapi.service.strategy.ListaRestritaStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ListaRestritaService {

    @Autowired
    private ListaRestritaRepository repository;

    @Autowired
    private ListaRestritaRedisCacheComponent cacheComponent;

    public ListaRestritiva save(ListaRestritiva listaRestritiva) {
        return repository.save(listaRestritiva);
    }

    @Cacheable(cacheNames = "ListaRestritaService::findById", cacheManager = "ehCacheManager")
    public Optional<ListaRestritiva> findById(Long doc) {
        return cacheComponent.findById(doc);
    }

    public void delete(ListaRestritiva listaRestritiva) {
        repository.delete(listaRestritiva);
    }

    public Optional<ListaRestritiva> buscaPorDocEProduto(Long doc, String produto) {

        final ListaRestritaStrategy strategy = new CartaoCreditoStrategy(cacheComponent);

        return strategy.buscaPorDocEProduto(doc);
    }
}
