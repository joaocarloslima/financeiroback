package br.com.fiap.gestanca.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.gestanca.models.ContaDto;
import br.com.fiap.gestanca.models.Despesa;
import br.com.fiap.gestanca.repositorys.ContaRepository;
import br.com.fiap.gestanca.repositorys.DespesaRepository;

@Service
public class ContaService {

    @Autowired
    ContaRepository contaRepository;

    @Autowired
    DespesaRepository despesaRepository;

    public List<ContaDto> findAll(){
        //buscar as contas
        var contas = contaRepository.findAll();
        List<ContaDto> dtos = new ArrayList<>();

        //pra cada conta
        contas.forEach(conta -> {
            //pegar todas as depesas da conta atual
            var despesas = despesaRepository.findByContaId(conta.getId());

            //somar todas as despesas
            var total = despesas.stream()
                .map(Despesa::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

            // inicial - despesas
            var saldo = conta.getSaldoInicial().subtract(total);

            var dto = new ContaDto(conta, saldo);

            dtos.add(dto);

        });

        return dtos;
        
    }
    
}
