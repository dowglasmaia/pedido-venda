package com.maia.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Dowglas Maia Skype: live:dowglasmaia E-mail:dowglasmaia@live.com
 *         Linkedin: www.linkedin.com/in/dowglasmaia
 */

@Entity
public class Pedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = true)
	@NotNull(message = " O campo Valor Total da Compra do Pedido é obrigatório")
	private Double totalDaCompra;

	@NotNull(message = " O campo Data da Compra do Pedido é obrigatório")
	private LocalDateTime dataDaCompra;

	
	@Valid
	@ManyToOne
	private Cliente cliente;

	@OneToMany(mappedBy = "id.pedido")
	private Set<ItemPedido> produtos = new HashSet<>();

	public Pedido() {

	}

	public Pedido(Long id, Double totalDaCompra, LocalDateTime dataDaCompra, Cliente cliente) {
		this.id = id;
		this.totalDaCompra = totalDaCompra;
		this.dataDaCompra = dataDaCompra;
		this.cliente = cliente;

	}
	
	
	public Double getValorTotalDoPedido() {
		double soma= 0.0;
		for(ItemPedido itp : produtos) {
			soma = soma + (itp.getProduto().getPreco() * itp.getQuantidade());
		}
		return soma;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getTotalDaCompra() {
		return totalDaCompra;
	}

	public void setTotalDaCompra(Double totalDaCompra) {
		this.totalDaCompra = getValorTotalDoPedido();
	}

	public LocalDateTime getDataDaCompra() {
		return dataDaCompra;
	}

	public void setDataDaCompra(LocalDateTime dataDaCompra) {
		this.dataDaCompra = dataDaCompra;
	}

	@JsonIgnore
	public Cliente getCliente() {
		return cliente;
	}

	@JsonProperty
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Set<ItemPedido> getProdutos() {
		return produtos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
