package br.com.fiap.gestanca.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.gestanca.models.Despesa;
import br.com.fiap.gestanca.repositorys.ContaRepository;
import br.com.fiap.gestanca.repositorys.DespesaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/despesas")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "despesa", description = "Dados das despesas")
public class DespesaController {

    Logger log = LoggerFactory.getLogger(DespesaController.class);

    @Autowired // IoD - IoC
    DespesaRepository despesaRespository;

    @Autowired
    ContaRepository contaRepository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    @GetMapping
    public PagedModel<EntityModel<Object>> index(@ParameterObject @PageableDefault(size = 5) Pageable pageable, @RequestParam(required = false) String busca){
        Page<Despesa> page = (busca == null) ?
            despesaRespository.findAll(pageable) :
            despesaRespository.findByDescricaoContaining(busca, pageable);
        
        return assembler.toModel(page.map(Despesa::toModel));
    }

    @GetMapping("{id}")
    @Operation(
        summary = "Obter os detalhes de uma despesa",
        description = "Mostra os detalhes de uma despesa específica através de um id informado no path"
    )
    public EntityModel<Despesa> show(@PathVariable Long id){
        log.info("buscar despesa com id " + id);
        var despesa = despesaRespository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "despesa não encontrada")
        );

        return despesa.toModel();

    }

    @PostMapping
    @Operation(
        summary = "Cadastrar uma despesa para o usuário autenticado"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Despesa cadastrada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados da despesa são inválidos, a validação falhou")
    })
    
    public ResponseEntity<EntityModel<Despesa>> create(@RequestBody @Valid Despesa despesa, BindingResult result){
        log.info("cadastrar despesa: " + despesa);
        despesaRespository.save(despesa);
        return ResponseEntity
                    .created(despesa.toModel().getRequiredLink("self").toUri())
                    .body(despesa.toModel());
    }   

    @DeleteMapping("{id}")
    public ResponseEntity<Despesa> destroy(@PathVariable Long id){
        log.info("apagar despesa com id " + id);
        Despesa despesa = despesaRespository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao apagar. Despesa não encontrada")
        );
        
        despesaRespository.delete(despesa);
            
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Despesa> update(@PathVariable Long id, @RequestBody @Valid Despesa despesa, BindingResult result){
        log.info("apagar despesa com id " + id);
        var despesaEncontrada = despesaRespository.findById(id);

        if (despesaEncontrada.isEmpty()) return ResponseEntity.notFound().build();

        var novaDespesa = despesaEncontrada.get();
        BeanUtils.copyProperties(despesa, novaDespesa, "id");
        
        despesaRespository.save(novaDespesa);
            
        return ResponseEntity.ok(novaDespesa);
    }
    
}
