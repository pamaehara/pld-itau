package br.com.itau.pld.demoapi.service.cache;

import br.com.itau.pld.demoapi.model.ListaRestritiva;
import br.com.itau.pld.demoapi.repository.ListaRestritaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ListaRestritaRedisCacheComponent {

    @Autowired
    private ListaRestritaRepository repository;

    @Cacheable(cacheNames = "ListaRestritaRedisCacheComponent::findById", cacheManager = "redisCacheManager")
    public Optional<ListaRestritiva> findById(Long doc) {
	return repository.findById(doc);
    }

}
