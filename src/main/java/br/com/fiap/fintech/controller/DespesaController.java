package br.com.fiap.fintech.controller;

import br.com.fiap.fintech.model.Despesa;
import br.com.fiap.fintech.service.DespesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/despesas")
public class DespesaController {

    @Autowired
    private DespesaService despesaService;

    // --- GET (Listar todas) ---
    @GetMapping
    public ResponseEntity<List<Despesa>> listarTodasDespesas(
            @RequestParam(required = false) Integer categoria,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String ordenacao,
            @RequestParam(required = false) String ordem) {
        
        try {
            List<Despesa> despesas;
            
            // Se algum filtro foi fornecido, usar método com filtros
            if (categoria != null || dataInicio != null || dataFim != null || status != null || 
                ordenacao != null || ordem != null) {
                despesas = despesaService.listarDespesasComFiltros(categoria, dataInicio, dataFim, status, ordenacao, ordem);
            } else {
                despesas = despesaService.listarTodasDespesas();
            }
            
            return ResponseEntity.ok(despesas);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // --- GET (Buscar por ID) ---
    @GetMapping("/{id}")
    public ResponseEntity<Despesa> buscarDespesaPorId(@PathVariable Integer id) {
        try {
            Despesa despesa = despesaService.buscarDespesaPorId(id);
            return ResponseEntity.ok(despesa);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // --- POST (Criar nova despesa) ---
    @PostMapping
    public ResponseEntity<Despesa> criarDespesa(@RequestBody Despesa despesa) {
        Despesa novaDespesa = despesaService.criarDespesa(despesa);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaDespesa);
    }

    // --- PUT (Atualizar) ---
    @PutMapping("/{id}")
    public ResponseEntity<Despesa> atualizarDespesa(@PathVariable Integer id, @RequestBody Despesa despesaDetails) {
        try {
            Despesa despesaAtualizada = despesaService.atualizarDespesa(id, despesaDetails);
            return ResponseEntity.ok(despesaAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // --- DELETE (Excluir) ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDespesa(@PathVariable Integer id) {
        try {
            despesaService.deletarDespesa(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
