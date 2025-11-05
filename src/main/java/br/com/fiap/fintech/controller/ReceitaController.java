package br.com.fiap.fintech.controller;

import br.com.fiap.fintech.model.Receita;
import br.com.fiap.fintech.service.ReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/receitas")
public class ReceitaController {

    @Autowired
    private ReceitaService receitaService;


    @GetMapping
    public ResponseEntity<List<Receita>> listarTodasReceitas(
            @RequestParam(required = false) Integer categoria,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String ordenacao,
            @RequestParam(required = false) String ordem) {
        
        try {
            List<Receita> receitas;

            if (categoria != null || dataInicio != null || dataFim != null || status != null || 
                ordenacao != null || ordem != null) {
                receitas = receitaService.listarReceitasComFiltros(categoria, dataInicio, dataFim, status, ordenacao, ordem);
            } else {
                receitas = receitaService.listarTodasReceitas();
            }
            
            return ResponseEntity.ok(receitas);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Receita> buscarReceitaPorId(@PathVariable Integer id) {
        try {
            Receita receita = receitaService.buscarReceitaPorId(id);

            return ResponseEntity.ok(receita);
        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<Receita> criarReceita(@RequestBody Receita receita) {
        Receita novaReceita = receitaService.criarReceita(receita);



        return ResponseEntity.status(HttpStatus.CREATED).body(novaReceita);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Receita> atualizarReceita(@PathVariable Integer id, @RequestBody Receita receitaDetails) {
        try {
            Receita receitaAtualizada = receitaService.atualizarReceita(id, receitaDetails);

            return ResponseEntity.ok(receitaAtualizada);
        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarReceita(@PathVariable Integer id) {
        try {
            receitaService.deletarReceita(id);

            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();
        }
    }
}