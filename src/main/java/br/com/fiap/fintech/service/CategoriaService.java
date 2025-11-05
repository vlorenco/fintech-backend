package br.com.fiap.fintech.service;

import br.com.fiap.fintech.model.Categoria;
import br.com.fiap.fintech.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    // --- CRIAR ---
    public Categoria criarCategoria(Categoria categoria) {

        if (categoria.getNome() == null || categoria.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome da categoria é obrigatório.");
        }
        
        // Se idUsuario não foi fornecido, usar usuário padrão (ID = 1)
        if (categoria.getIdUsuario() == null) {
            categoria.setIdUsuario(1);
        }
        // --- FIM DA REGRA ---

        return categoriaRepository.save(categoria);
    }

    // --- LER TODOS ---
    public List<Categoria> listarTodasCategorias() {
        return categoriaRepository.findAll();
    }

    // --- LER POR ID ---
    public Categoria buscarCategoriaPorId(Integer id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com o id: " + id));
    }

    // --- ATUALIZAR ---
    public Categoria atualizarCategoria(Integer id, Categoria categoriaDetails) {
        Categoria categoriaExistente = buscarCategoriaPorId(id);

        categoriaExistente.setNome(categoriaDetails.getNome());
        categoriaExistente.setCor(categoriaDetails.getCor());


        return categoriaRepository.save(categoriaExistente);
    }

    // --- DELETAR ---
    public void deletarCategoria(Integer id) {

        Categoria categoriaParaDeletar = buscarCategoriaPorId(id);
        categoriaRepository.delete(categoriaParaDeletar);

        /*
        // O JEITO INTELIGENTE (que você não precisa fazer, mas devia):
        // 1. Verificar se alguma DESPESA ou RECEITA usa essa categoria
        // boolean emUso = !despesaRepository.findByCategoria(id).isEmpty() ||
        //                 !receitaRepository.findByCategoria(id).isEmpty();
        //
        // 2. Se estiver em uso, lançar um erro
        // if (emUso) {
        //    throw new RuntimeException("Não pode deletar categoria que está em uso!");
        // }
        //
        // 3. Se não, deletar
        // categoriaRepository.delete(categoriaParaDeletar);
        */
    }
}