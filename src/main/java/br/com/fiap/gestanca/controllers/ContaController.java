package br.com.fiap.gestanca.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import br.com.fiap.gestanca.exception.NaoAutorizadoException;
import br.com.fiap.gestanca.models.Conta;
import br.com.fiap.gestanca.models.Despesa;
import br.com.fiap.gestanca.repositorys.ContaRepository;
import br.com.fiap.gestanca.repositorys.DespesaRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/contas")
public class ContaController {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired 
    ContaRepository repository;

    @GetMapping
    public List<Conta> index(){
        log.info("Buscar todas as contas");
        return repository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Conta> show(@PathVariable Long id){
        log.info("buscar conta com id " + id);
        return ResponseEntity.ok(getConta(id));
    }

    @PostMapping
    public ResponseEntity<Conta> create(@RequestBody @Valid Conta conta){
        log.info("cadastrar conta: " + conta);
        repository.save(conta);
        return ResponseEntity.status(HttpStatus.CREATED).body(conta);
    }   

    @DeleteMapping("{id}")
    public ResponseEntity<Conta> destroy(@PathVariable Long id){
        log.info("apagar conta com id " + id);
        repository.delete(getConta(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Conta> update(@PathVariable Long id, @RequestBody @Valid Conta conta){
        log.info("alterar conta com id " + id);
        getConta(id);
        conta.setId(id);
        repository.save(conta);
        return ResponseEntity.ok(conta);
    }

    private Conta getConta(Long id) {
        return repository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "conta não encontrada")
        );
    }
    
}
