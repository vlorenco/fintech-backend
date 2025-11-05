package br.com.fiap.fintech.repository;

import br.com.fiap.fintech.model.Receita;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository // Opcional, mas boa prática para indicar que é um repositório
public interface ReceitaRepository extends JpaRepository<Receita, Integer> {
    
    // Filtros individuais
    List<Receita> findByIdCategoria(Integer categoria);
    List<Receita> findByStatus(Receita.StatusTransacao status);
    List<Receita> findByDataMovBetween(LocalDate dataInicio, LocalDate dataFim);
    
    // Filtros combinados
    List<Receita> findByIdCategoriaAndStatus(Integer categoria, Receita.StatusTransacao status);
    List<Receita> findByIdCategoriaAndDataMovBetween(Integer categoria, LocalDate dataInicio, LocalDate dataFim);
    List<Receita> findByStatusAndDataMovBetween(Receita.StatusTransacao status, LocalDate dataInicio, LocalDate dataFim);
    List<Receita> findByIdCategoriaAndStatusAndDataMovBetween(Integer categoria, Receita.StatusTransacao status, LocalDate dataInicio, LocalDate dataFim);
    
    // Com ordenação
    List<Receita> findByIdCategoria(Integer categoria, Sort sort);
    List<Receita> findByStatus(Receita.StatusTransacao status, Sort sort);
    List<Receita> findByDataMovBetween(LocalDate dataInicio, LocalDate dataFim, Sort sort);
    List<Receita> findByIdCategoriaAndStatus(Integer categoria, Receita.StatusTransacao status, Sort sort);
    List<Receita> findByIdCategoriaAndDataMovBetween(Integer categoria, LocalDate dataInicio, LocalDate dataFim, Sort sort);
    List<Receita> findByStatusAndDataMovBetween(Receita.StatusTransacao status, LocalDate dataInicio, LocalDate dataFim, Sort sort);
    List<Receita> findByIdCategoriaAndStatusAndDataMovBetween(Integer categoria, Receita.StatusTransacao status, LocalDate dataInicio, LocalDate dataFim, Sort sort);
    
    // Encontrar todas com ordenação
    @Override
    List<Receita> findAll(Sort sort);
}