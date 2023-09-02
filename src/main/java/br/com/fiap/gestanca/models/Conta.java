package br.com.fiap.gestanca.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Conta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    private String nome;

    private BigDecimal saldoInicial;

    private String icone;

    // @OneToMany(mappedBy = "conta")
    // private List<Despesa> despesas = new ArrayList<>();


}
