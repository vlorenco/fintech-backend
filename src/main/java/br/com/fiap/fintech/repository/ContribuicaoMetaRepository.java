package br.com.fiap.fintech.repository;

import br.com.fiap.fintech.model.ContribuicaoMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContribuicaoMetaRepository extends JpaRepository<ContribuicaoMeta, Integer> {

    @Query("SELECT c FROM ContribuicaoMeta c WHERE c.meta.id = :metaId ORDER BY c.dataContribuicao DESC")
    List<ContribuicaoMeta> findByMetaIdOrderByDataContribuicaoDesc(@Param("metaId") Integer metaId);
}