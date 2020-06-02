package br.com.itau.pld.demoapi.controller;

import br.com.itau.pld.demoapi.model.ListaRestritiva;
import br.com.itau.pld.demoapi.service.ListaRestritaService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebAppConfiguration
@RunWith(MockitoJUnitRunner.class)
public class ListaRestritivaControllerTest {

    @Mock
    private ListaRestritaService service;

    @InjectMocks
    private ListaRestritivaController resource;

    private MockMvc mockMvc;
    private Long    docValid = 31710241870L;
    private Long    docInvalid = 12345678900L;

    @Before
    public void before() {
        Mockito.when(this.service.findById(docInvalid)).thenReturn(Optional.empty());
        Mockito.when(this.service.findById(docValid)).thenReturn(mockListaRestritiva());
	this.mockMvc = MockMvcBuilders.standaloneSetup(resource).build();
    }

    @Test
    public void buscaPorDocValid() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/api/v1/listarestritiva/buscaPorDoc/{doc}", docValid))
		        .andExpect(MockMvcResultMatchers.status().isOk())
		        .andReturn();

        Assert.assertNotNull(result);
        Assert.assertEquals("{\"doc\":31710241870,\"nome\":\"Junit Teste\"}", result.getResponse().getContentAsString());
    }

    @Test
    public void buscaPorDocInvalid() throws Exception {
	MvcResult result = this.mockMvc.perform(get("/api/v1/listarestritiva/buscaPorDoc/{doc}", docInvalid))
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andReturn();

        Assert.assertNotNull(result.getResolvedException());
        Assert.assertEquals("Documento não encontrado para doc :: " + docInvalid, result.getResolvedException().getMessage());

    }

    private Optional<ListaRestritiva> mockListaRestritiva() {
        ListaRestritiva listaRestritiva = new ListaRestritiva();
        listaRestritiva.setDoc(docValid);
        listaRestritiva.setNome("Junit Teste");
        return Optional.of(listaRestritiva);
    }

}
