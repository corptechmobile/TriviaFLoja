package br.com.webapp.model.fb.reserva;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.webapp.model.fb.pedvenda.PedVendaFB;
import br.com.webapp.model.fb.pedvenda.PedVendaItemFB;
import br.com.webapp.model.fb.produto.ProdutoEstoqueFB;
import br.com.webapp.model.fb.produto.ProdutoFB;
import br.com.webapp.model.fb.reservalote.ReservaLoteFB;
import br.com.webapp.model.fb.reservalote.ReservaLoteFBRN;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.DAOFactoryFirebird;

public class ReservaFBRN {

	private ReservaFBDAO reservaFBDAO;
	
	public ReservaFBRN() {
		this.reservaFBDAO = DAOFactoryFirebird.criarReservaFBDAO();
	}
	
	public void salvar(Integer pedVendaFBId, Integer pedVendaItemFBId, List<ProdutoEstoqueFB> estoques, boolean isControlaLote) throws DAOException {
		
		this.excluir(pedVendaItemFBId);
		
		if(isControlaLote) {
			
			Set<ReservaFB> reservas = new HashSet<ReservaFB>();
			
			ReservaLoteFBRN reservaLoteFBRN = new ReservaLoteFBRN();
			for(ProdutoEstoqueFB rs : estoques) {
				if(rs.getQtdReservar().doubleValue() > 0.0) {
					reservas.add(this.novo(pedVendaFBId, pedVendaItemFBId, rs));
				}
			}
			
			for(ReservaFB rs : reservas) {
				rs.setQuantidade(this.qtdReservadaLote(estoques, rs));
				rs.setId(this.insert(rs));
			}
			
			for(ProdutoEstoqueFB rs : estoques) {
				if(rs.getQtdReservar().doubleValue() > 0.0) {
					ReservaLoteFB reservaLoteFB = reservaLoteFBRN.novo(this.findIdReservaFB(reservas, rs), rs);
					reservaLoteFBRN.insert(reservaLoteFB);
				}
			}
			
		}else{
			for(ProdutoEstoqueFB rs : estoques) {
				if(rs.getQtdReservar().doubleValue() > 0.0) {
					this.insert(pedVendaFBId, pedVendaItemFBId, rs);
				}
			}
		}
		
	}
	
	private Double qtdReservadaLote(List<ProdutoEstoqueFB> estoques, ReservaFB reserva) {
		Double qtdReservada = 0.0;
		for(ProdutoEstoqueFB rs : estoques) {
			if(rs.getQtdReservar().doubleValue() > 0.0 && reserva.getLocalidadeId().equals(rs.getLocalidadeId()) && reserva.getProdutoId().equals(rs.getProdutoId())) {
				qtdReservada += rs.getQtdReservar();
			}
		}
		return qtdReservada;
	}
	
	private Integer findIdReservaFB(Set<ReservaFB> reservas, ProdutoEstoqueFB estoque) {
		Integer id = null;
		
		for(ReservaFB rs : reservas) {
			if(estoque.getLocalidadeId().equals(rs.getLocalidadeId()) && estoque.getProdutoId().equals(rs.getProdutoId())) {
				id = rs.getId();
			}
		}
		
		return id;
		
		
		
		
	}
	
	public ReservaFB novo(Integer pedVendaFBId, Integer pedVendaItemId, ProdutoEstoqueFB produtoEstoqueFB) throws DAOException {

		ReservaFB reservaFB = new ReservaFB();
		reservaFB.setPedVendaId(pedVendaFBId);
		reservaFB.setPedVendaItemId(pedVendaItemId);
		reservaFB.setProdutoId(produtoEstoqueFB.getProdutoId());
		reservaFB.setLocalidadeId(produtoEstoqueFB.getLocalidadeId());
		reservaFB.setTipoId(ReservaFB.TIPOID);
		reservaFB.setQuantidade(produtoEstoqueFB.getQtdReservar());
		reservaFB.setOrdemRetirada(ReservaFB.ORDEMRETIRADA);
		
		return reservaFB;
		
	}

	public Integer insert(Integer pedVendaFBId, Integer pedVendaItemId, ProdutoEstoqueFB produtoEstoqueFB) throws DAOException {
		
		ReservaFB reservaFB = this.novo(pedVendaFBId, pedVendaItemId, produtoEstoqueFB);
		return this.reservaFBDAO.insert(reservaFB);
		
	}

	public Integer insert(ReservaFB reservaFB) throws DAOException {
		return this.reservaFBDAO.insert(reservaFB);
	}
	
	public void update(ReservaFB reservaFB) throws DAOException {
		this.reservaFBDAO.update(reservaFB);
	}
	
	public void excluir(Integer pedVendaItemFBId) throws DAOException {
		this.reservaFBDAO.excluir(pedVendaItemFBId);
		
	}

	public ReservaFB carregar(Integer reservaId) {
		return this.reservaFBDAO.carregar(reservaId);
	}
	
	public List<ReservaFB> listar(PedVendaFB pedVendaFB){
		return this.reservaFBDAO.listar(pedVendaFB);
	}
	
	public List<ReservaFB> listar(PedVendaItemFB pedVendaItemFB){
		return this.reservaFBDAO.listar(pedVendaItemFB);
	}

	public Double qtdReservada(PedVendaItemFB pedVendaItemFB, Integer localidadeId, Integer produtoLoteId, Integer controlaLote) {
		Double result = 0.0;
		if(pedVendaItemFB.getId()!=null) {
			if(controlaLote == ProdutoFB.PRODUTO_CONTROLA_LOTE) {
				result = this.reservaFBDAO.qtdReservadaControlaLote(pedVendaItemFB.getId(), localidadeId, produtoLoteId);
			}else {
				result = this.reservaFBDAO.qtdReservadaNaoControlaLote(pedVendaItemFB.getId(), localidadeId);
			}
		}
		
		return result;
	}

}
