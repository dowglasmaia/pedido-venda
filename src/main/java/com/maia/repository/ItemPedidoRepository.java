package com.maia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.maia.domain.ItemPedido;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {	
	
	@Query("SELECT p FROM ItemPedido p WHERE p.id.pedido.id = ?1")
	List<ItemPedido> findAllByIdPedido(Long idPedido);

}
