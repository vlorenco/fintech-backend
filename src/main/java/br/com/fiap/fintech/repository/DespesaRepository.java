package br.com.fiap.fintech.repository;

import br.com.fiap.fintech.model.Despesa;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Integer> {
    
    // Filtros individuais
    List<Despesa> findByIdCategoria(Integer categoria);
    List<Despesa> findByStatus(Despesa.StatusTransacao status);
    List<Despesa> findByDataMovBetween(LocalDate dataInicio, LocalDate dataFim);
    
    // Filtros combinados
    List<Despesa> findByIdCategoriaAndStatus(Integer categoria, Despesa.StatusTransacao status);
    List<Despesa> findByIdCategoriaAndDataMovBetween(Integer categoria, LocalDate dataInicio, LocalDate dataFim);
    List<Despesa> findByStatusAndDataMovBetween(Despesa.StatusTransacao status, LocalDate dataInicio, LocalDate dataFim);
    List<Despesa> findByIdCategoriaAndStatusAndDataMovBetween(Integer categoria, Despesa.StatusTransacao status, LocalDate dataInicio, LocalDate dataFim);
    
    // Com ordenação
    List<Despesa> findByIdCategoria(Integer categoria, Sort sort);
    List<Despesa> findByStatus(Despesa.StatusTransacao status, Sort sort);
    List<Despesa> findByDataMovBetween(LocalDate dataInicio, LocalDate dataFim, Sort sort);
    List<Despesa> findByIdCategoriaAndStatus(Integer categoria, Despesa.StatusTransacao status, Sort sort);
    List<Despesa> findByIdCategoriaAndDataMovBetween(Integer categoria, LocalDate dataInicio, LocalDate dataFim, Sort sort);
    List<Despesa> findByStatusAndDataMovBetween(Despesa.StatusTransacao status, LocalDate dataInicio, LocalDate dataFim, Sort sort);
    List<Despesa> findByIdCategoriaAndStatusAndDataMovBetween(Integer categoria, Despesa.StatusTransacao status, LocalDate dataInicio, LocalDate dataFim, Sort sort);
    
    // Encontrar todas com ordenação
    @Override
    List<Despesa> findAll(Sort sort);
}