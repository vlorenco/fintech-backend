package br.com.fiap.fintech.repository;

import br.com.fiap.fintech.model.Meta;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetaRepository extends JpaRepository<Meta, Integer> {
    
    // Filtros por status
    List<Meta> findByStatus(Meta.StatusMeta status);
    List<Meta> findByStatus(Meta.StatusMeta status, Sort sort);
    
    // Filtros por descrição (como categoria)
    List<Meta> findByDescricaoContainingIgnoreCase(String categoria);
    List<Meta> findByDescricaoContainingIgnoreCase(String categoria, Sort sort);
    
    // Filtros combinados
    List<Meta> findByStatusAndDescricaoContainingIgnoreCase(Meta.StatusMeta status, String categoria);
    List<Meta> findByStatusAndDescricaoContainingIgnoreCase(Meta.StatusMeta status, String categoria, Sort sort);
    
    // Encontrar todas com ordenação
    @Override
    List<Meta> findAll(Sort sort);
    
    // Query customizada para calcular progresso
    @Query("SELECT m FROM Meta m ORDER BY (m.valorAtual * 100.0 / m.valorObjetivo) DESC")
    List<Meta> findAllOrderByProgressoDesc();
    
    @Query("SELECT m FROM Meta m ORDER BY (m.valorAtual * 100.0 / m.valorObjetivo) ASC")
    List<Meta> findAllOrderByProgressoAsc();
}
