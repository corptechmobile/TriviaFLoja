package br.com.webapp.model.fb.relatorio.vendaforn.resumo;

import java.beans.Transient;
import java.io.Serializable;

import br.com.webapp.model.fb.diasuteis.DiasUteisFB;
import br.com.webapp.web.util.Funcoes;

public class VendaFornResumo implements Serializable{

	private static final long serialVersionUID = -6879203456818824588L;
	
	private Integer numClientes;
	private Double ticketMedio;
	private Double faturamento;
	private Double fatAnt;
	private Double devolucao;
	private Double desconto;
	private Double mediaDiaria;
	private Double margem;
	private Double markup;
	private Double lucro;
	private Double lucroRealizado;
	private Double valorCusto;
	private Double valorCustoDevolvido;
	
	
	public Integer getNumClientes() {
		return numClientes;
	}

	public void setNumClientes(Integer numClientes) {
		this.numClientes = numClientes;
	}

	public Double getTicketMedio() {
		return ticketMedio;
	}
	
	public void setTicketMedio(Double ticketMedio) {
		this.ticketMedio = ticketMedio;
	}
	
	public Double getFaturamento() {
		return faturamento;
	}
	
	public void setFaturamento(Double faturamento) {
		this.faturamento = faturamento;
	}
	
	public Double getFatAnt() {
		return fatAnt;
	}

	public void setFatAnt(Double fatAnt) {
		this.fatAnt = fatAnt;
	}

	public Double getDevolucao() {
		return devolucao;
	}
	
	public void setDevolucao(Double devolucao) {
		this.devolucao = devolucao;
	}
	
	public Double getDesconto() {
		return desconto;
	}
	
	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}
	
	public Double getMediaDiaria() {
		return mediaDiaria;
	}
	
	public void setMediaDiaria(Double mediaDiaria) {
		this.mediaDiaria = mediaDiaria;
	}
	
	public Double getMargem() {
		return margem;
	}
	
	public void setMargem(Double margem) {
		this.margem = margem;
	}
	
	public Double getMarkup() {
		return markup;
	}
	
	public Double getLucro() {
		return lucro;
	}

	public void setLucro(Double lucro) {
		this.lucro = lucro;
	}

	public Double getLucroRealizado() {
		lucroRealizado = ((faturamento-devolucao-valorCusto)+valorCustoDevolvido);
		return lucroRealizado;
	}

	public void setLucroRealizado(Double lucroRealizado) {
		this.lucroRealizado = lucroRealizado;
	}

	public Double getValorCusto() {
		return valorCusto;
	}

	public void setValorCusto(Double valorCusto) {
		this.valorCusto = valorCusto;
	}

	public Double getValorCustoDevolvido() {
		return valorCustoDevolvido;
	}

	public void setValorCustoDevolvido(Double valorCustoDevolvido) {
		this.valorCustoDevolvido = valorCustoDevolvido;
	}

	public void setMarkup(Double markup) {
		this.markup = markup;
	}
	
	public Double getCrescimento(DiasUteisFB diasUteis) {
		try {
			double tendencia = getTendencia(diasUteis);
			if(tendencia==0d) {
				return 0.0;
			}
			return ((((tendencia)-fatAnt)/fatAnt)*100);
		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}
	}
	
	public Double getTendencia(DiasUteisFB diasUteis) {
		try {
			if(diasUteis.getPrazoDecorrido()==0) {
				return 0.0;
			}
			return (((faturamento - devolucao) / diasUteis.getPrazoDecorrido()) * (diasUteis.getDiasUteis() - diasUteis.getPrazoDecorrido())) + (faturamento - devolucao);
		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}
	}
	
	public Double getLucroLiq(DiasUteisFB diasUteis) {
		try {
			if(diasUteis.getPrazoDecorrido()==0) {
				return 0.0;
			}
			
			//return (((lucro-devolucao) / diasUteis.getPrazoDecorrido()) * (diasUteis.getDiasUteis() - diasUteis.getPrazoDecorrido())) + (lucro-devolucao);

			return (Funcoes.arrendondaValor(4,(getTendencia(diasUteis)))*Funcoes.arrendondaValor(4,(margem/100)));
		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}
	}
}
