package br.com.itau.pld.demoapi.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name="lista_restrita")
@Getter @Setter @EqualsAndHashCode @NoArgsConstructor
public class ListaRestritiva implements Serializable {

    @Id
    @NotBlank
    private Long doc;
    @NotBlank
    private String nome;

}
