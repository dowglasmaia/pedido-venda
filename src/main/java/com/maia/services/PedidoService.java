package com.maia.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maia.domain.Cliente;
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

	@Autowired
	private ItemPedidoService itenPedidoService;

	@Transactional(readOnly = true)
	public Pedido retornaPedidoPorId(Long id) {
		Optional<Pedido> pedido = repository.findById(id);
		return pedido.orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado para o Código: " + id));
	}

	@Transactional
	public Pedido inserirNovoPedido(Pedido pedido, Long idCliente) {
		try {
			pedido.setId(null);
			pedido.setDataDaCompra(LocalDateTime.now());
			pedido.setCliente(clienteService.retornaClientePorId(idCliente));
			pedido.setTotalDaCompra(pedido.obterValorTotalDoPedido());

			pedido = repository.save(pedido);

			for (ItemPedido iten : pedido.getProdutos()) {
				iten.setProduto(produtoService.retornaProdutoPorId(iten.getProduto().getId()));
				iten.setPedido(pedido);
				iten.setQuantidade(iten.getQuantidade());
			}
			itemPedidoRepo.saveAll(pedido.getProdutos());
			return pedido;
		} catch (Exception e) {
			throw new RuntimeException("Falha na requisição de salvar um novo Pedido ");
		}
	}

	@Transactional
	public Pedido editPedido(Pedido pedido , Long idCliente) {
		Pedido newPedido = retornaPedidoPorId(pedido.getId());
		Cliente cliente = clienteService.retornaClientePorId(idCliente);
		try {
			updateData(newPedido, pedido, cliente);
			return repository.save(newPedido);
		} catch (Exception e) {
			throw new RuntimeException("Falha ao tentar editar o Pedido de Código: " + pedido.getId());
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
			throw new RuntimeException("Falha ao Excluir Pedido de Código:" + id);
		}
	}

	/* ##### METODOS PRIVADOS ###### */
	private void updateData(Pedido newPedido, Pedido pedido, Cliente cliente) {
		newPedido.getProdutos().clear();
		itenPedidoService.updateItens(pedido.getId());
		newPedido.setCliente(cliente);
		
		Double totalPedido = 0.0;
		for (ItemPedido iten : pedido.getProdutos()) {
			iten.setId(iten.getId());
			iten.setProduto(iten.getProduto());
			iten.setPedido(pedido);		
			iten.setQuantidade(iten.getQuantidade());
			newPedido.getProdutos().add(iten);
			totalPedido += (iten.getProduto().getPreco() * iten.getQuantidade());
		}
		itemPedidoRepo.saveAll(newPedido.getProdutos());
		newPedido.setTotalDaCompra(totalPedido);
	}

}
