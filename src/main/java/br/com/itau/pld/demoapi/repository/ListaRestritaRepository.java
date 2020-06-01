package br.com.itau.pld.demoapi.repository;

import br.com.itau.pld.demoapi.model.ListaRestritiva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListaRestritaRepository extends JpaRepository<ListaRestritiva, Long> {

}
