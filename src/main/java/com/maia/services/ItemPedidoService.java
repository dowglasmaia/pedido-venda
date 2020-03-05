package com.maia.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maia.domain.ItemPedido;
import com.maia.domain.ItemPedidoPK;
import com.maia.domain.Pedido;
import com.maia.repository.ItemPedidoRepository;

@Service
public class ItemPedidoService {

	@Autowired
	private ItemPedidoRepository repository;
	
	@Transactional
	public List<ItemPedido> updateItens(Long idPedido) {
		
		List<ItemPedido>list = repository.findAllByIdPedido(idPedido);
		
		
		for (ItemPedido iten : list) {
		repository.delete(iten);			
		}
		
		return list;
	}


	
}
