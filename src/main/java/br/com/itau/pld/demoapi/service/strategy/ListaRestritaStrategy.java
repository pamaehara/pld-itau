package br.com.itau.pld.demoapi.service.strategy;

import br.com.itau.pld.demoapi.model.ListaRestritiva;

import java.util.Optional;

public interface ListaRestritaStrategy {

    Optional<ListaRestritiva> buscaPorDocEProduto(Long doc);

}
