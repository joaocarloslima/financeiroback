package br.com.fiap.gestanca.models;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty(message = "O nome da conta é obrigatório")
    private String nome;

    @Min(value= 0, message = "O saldo inicial deve ser maior ou igual a 0")
    private BigDecimal saldoInicial;

    private String icone;

    // @OneToMany(mappedBy = "conta")
    // private List<Despesa> despesas = new ArrayList<>();


}
