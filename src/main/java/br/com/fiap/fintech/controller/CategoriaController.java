package br.com.fiap.fintech.controller;

import br.com.fiap.fintech.model.Categoria;
import br.com.fiap.fintech.service.CategoriaService; // <--- MUDOU
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<Categoria>> listarTodasCategorias() {
        List<Categoria> categorias = categoriaService.listarTodasCategorias();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarCategoriaPorId(@PathVariable Integer id) {
        try {
            Categoria categoria = categoriaService.buscarCategoriaPorId(id);
            return ResponseEntity.ok(categoria);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Categoria> criarCategoria(@RequestBody Categoria categoria) {
        Categoria novaCategoria = categoriaService.criarCategoria(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> atualizarCategoria(@PathVariable Integer id, @RequestBody Categoria categoriaDetails) {
        try {
            Categoria categoriaAtualizada = categoriaService.atualizarCategoria(id, categoriaDetails);
            return ResponseEntity.ok(categoriaAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable Integer id) {
        try {
            categoriaService.deletarCategoria(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();
        }
    }
}