package com.maia.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maia.domain.ItemPedido;
import com.maia.domain.Pedido;
import com.maia.repository.ItemPedidoRepository;
import com.maia.repository.PedidoRepository;
import com.maia.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepo;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private ProdutoService produtoService;

	@Transactional(readOnly = true)
	public Pedido retornaPedidoPorId(Long id) {
		Optional<Pedido> pedido = repository.findById(id);
		return pedido.orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado para o Código: " + id));
	}

	@Transactional
	public Pedido inserirNovoPedido(Pedido pedido , Long idCliente) {
		try {
			pedido.setId(null);
			pedido.setDataDaCompra(LocalDateTime.now());
			pedido.setCliente(clienteService.retornaClientePorId(idCliente));
			
			pedido = repository.save(pedido);
			
			for (ItemPedido iten : pedido.getProdutos()) {
				iten.setPreco(iten.getProduto().getPreco());
				iten.setProduto(produtoService.retornaProdutoPorId(iten.getProduto().getId()));
				iten.setPedido(pedido);
				iten.setQuantidade(iten.getQuantidade());				
			}
			itemPedidoRepo.saveAll(pedido.getProdutos());			
			return repository.save(pedido);			
		} catch (Exception e) {
			throw new RuntimeException("Falha na requisição de salvar um novo Pedido ");
		}
	}

	@Transactional
	public Pedido editPedido(Pedido pedido) {
		Pedido newPedido = retornaPedidoPorId(pedido.getId());
		try {
			updateData(newPedido, pedido);
			return repository.save(newPedido);			
		} catch (Exception e) {
			throw new RuntimeException("Falha ao tentar editar o Pedido de Código: "+ pedido.getId());
		}
	}

	@Transactional(readOnly = true)
	public List<Pedido> retornaListaDePedidos() {
		try {
			return repository.findAll();			
		} catch (Exception e) {
			throw new RuntimeException("Falha ao tentar listar Pedidos");
		}
	}
	
	@Transactional
	public void excluirPedido(Long id) {
		retornaPedidoPorId(id);
		try {
			repository.deleteById(id);
		} catch (Exception e) {
			throw new RuntimeException("Falha ao Excluir Pedido de Código:" +id);
		}
	}
	
	public void removerPedidoPorIdCliente(Long IdCliente) {
		try {
			repository.deleteByIdCliente(IdCliente);
		} catch (Exception e) {
			throw new RuntimeException("Falha ao tentar excluir Pedido vinculado ao Cliente de código:" +IdCliente);
		}
	}
	
	

	/* ##### METODOS PRIVADOS ###### */
	private void updateData(Pedido newPedido, Pedido pedido) {
		newPedido.setTotalDaCompra(pedido.getTotalDaCompra());	
	}

}
