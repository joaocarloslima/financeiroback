package br.com.fiap.gestanca.repositorys;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fiap.gestanca.models.Despesa;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    Page<Despesa> findByDescricaoContaining(String busca, Pageable pageable);

    // @Query("SELECT d from Despesa d ORDER BY d.id LIMIT ?1 OFFSET ?2") //JPQL
    // List<Despesa> findTop(int tamanho, int offset);


    
}
