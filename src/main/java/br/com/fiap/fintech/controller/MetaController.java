package br.com.fiap.fintech.controller;

import br.com.fiap.fintech.model.Meta;
import br.com.fiap.fintech.dto.ContribuicaoHistoricoDTO;
import br.com.fiap.fintech.service.MetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/metas")
public class MetaController {

    @Autowired
    private MetaService metaService;

    @GetMapping
    public ResponseEntity<List<Meta>> listarTodasMetas(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String ordenacao,
            @RequestParam(required = false) String ordem) {
        
        try {
            List<Meta> metas;
            
            // Se algum filtro foi fornecido, usar método com filtros
            if (status != null || categoria != null || ordenacao != null || ordem != null) {
                metas = metaService.listarMetasComFiltros(status, categoria, ordenacao, ordem);
            } else {
                metas = metaService.listarTodasMetas();
            }
            
            return ResponseEntity.ok(metas);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Meta> buscarMetaPorId(@PathVariable Integer id) {
        try {
            Meta meta = metaService.buscarMetaPorId(id);
            return ResponseEntity.ok(meta);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<Meta> criarMeta(@RequestBody Meta meta) {
        Meta novaMeta = metaService.criarMeta(meta);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaMeta);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Meta> atualizarMeta(@PathVariable Integer id, @RequestBody Meta metaDetails) {
        try {
            Meta metaAtualizada = metaService.atualizarMeta(id, metaDetails);
            return ResponseEntity.ok(metaAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMeta(@PathVariable Integer id) {
        try {
            metaService.deletarMeta(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/{id}/contribuir")
    public ResponseEntity<Meta> contribuirParaMeta(@PathVariable Integer id, @RequestBody Map<String, Object> contribuicaoData) {
        try {
            BigDecimal valor = new BigDecimal(contribuicaoData.get("valor").toString());
            String descricao = (String) contribuicaoData.get("descricao");
            
            Meta metaAtualizada = metaService.contribuirParaMeta(id, valor, descricao);
            return ResponseEntity.ok(metaAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/{id}/historico")
    public ResponseEntity<List<ContribuicaoHistoricoDTO>> obterHistoricoContribuicoes(@PathVariable Integer id) {
        try {
            List<ContribuicaoHistoricoDTO> historico = metaService.obterHistoricoContribuicoes(id);
            return ResponseEntity.ok(historico);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // --- PUT (Pausar meta) ---
    @PutMapping("/{id}/pausar")
    public ResponseEntity<Meta> pausarMeta(@PathVariable Integer id) {
        try {
            Meta metaPausada = metaService.pausarMeta(id);
            return ResponseEntity.ok(metaPausada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @PutMapping("/{id}/reativar")
    public ResponseEntity<Meta> reativarMeta(@PathVariable Integer id) {
        try {
            Meta metaReativada = metaService.reativarMeta(id);
            return ResponseEntity.ok(metaReativada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
