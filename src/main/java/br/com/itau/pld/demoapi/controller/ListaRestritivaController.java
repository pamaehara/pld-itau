package br.com.itau.pld.demoapi.controller;

import br.com.itau.pld.demoapi.exception.ResourceNotFoundException;
import br.com.itau.pld.demoapi.model.ListaRestritiva;
import br.com.itau.pld.demoapi.service.ListaRestritaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/listarestritiva")
public class ListaRestritivaController {

    @Autowired
    private ListaRestritaService listaRestritaService;

    @PostMapping("/insere")
    public ListaRestritiva insere(@Valid @RequestBody ListaRestritiva listaRestritiva) {
	return listaRestritaService.save(listaRestritiva);
    }

    @GetMapping("/buscaPorDoc/{doc}")
    public ResponseEntity<ListaRestritiva> buscaPorDoc(@PathVariable Long doc)
                    throws ResourceNotFoundException {
        ListaRestritiva listaRestritiva = listaRestritaService.findById(doc)
		        .orElseThrow(() -> new ResourceNotFoundException("Documento não encontrado para doc :: " + doc));
        return ResponseEntity.ok().body(listaRestritiva);
    }

    @GetMapping("/buscaPorDocEProduto/{doc}/{produto}")
    public ResponseEntity<ListaRestritiva> buscaPorDocEProduto(@PathVariable Long doc, @PathVariable String produto)
                    throws ResourceNotFoundException {
        ListaRestritiva listaRestritiva = listaRestritaService.buscaPorDocEProduto(doc, produto)
                        .orElseThrow(() -> new ResourceNotFoundException("Documento não encontrado para doc :: " + doc));
        return ResponseEntity.ok().body(listaRestritiva);
    }

    @PutMapping("/altera/{doc}")
    public ResponseEntity<ListaRestritiva> altera(@PathVariable Long doc,
                                                  @Valid @RequestBody ListaRestritiva listaRestritiva)
                    throws ResourceNotFoundException {
        ListaRestritiva listaRestritivaDB = listaRestritaService.findById(doc)
		        .orElseThrow(() -> new ResourceNotFoundException("Documento não encontrado para doc :: " + doc));

        listaRestritivaDB.setNome(listaRestritiva.getNome());
        final ListaRestritiva listaRestritivaAlterada = listaRestritaService.save(listaRestritivaDB);
        return ResponseEntity.ok(listaRestritivaAlterada);

    }

    @DeleteMapping("/apaga/{doc}")
    public Map<String, Boolean> apaga(@PathVariable Long doc)
                    throws ResourceNotFoundException {
        ListaRestritiva listaRestritiva = listaRestritaService.findById(doc)
		        .orElseThrow(() -> new ResourceNotFoundException("Documento não encontrado para doc :: " + doc));

        listaRestritaService.delete(listaRestritiva);
        Map<String, Boolean> response = new HashMap<>();
        response.put("apagado", Boolean.TRUE);
        return response;
    }

}
