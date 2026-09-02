package br.com.webapp.model.fb.produto.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.webapp.model.fb.condpagto.CondPagtoFB;
import br.com.webapp.model.fb.condpagto.CondPagtoFBRN;
import br.com.webapp.model.fb.movfisctipo.MovFiscTipoFB;
import br.com.webapp.model.fb.pedvenda.PedVendaFB;
import br.com.webapp.model.fb.pedvendaitem.dto.PedVendaItemFBDTO;
import br.com.webapp.model.fb.produto.ProdutoFB;
import br.com.webapp.model.fb.produto.ProdutoFBRN;
import br.com.webapp.model.fb.vendedor.VendedorFB;
import br.com.webapp.model.fb.vendedor.VendedorFBRN;
import br.com.webapp.web.util.Funcoes;
import br.com.webapp.web.util.RNException;

public class ProdutoFBLazyDM extends LazyDataModel<ProdutoFB> {
	
	private static final long serialVersionUID = 6241374073244799237L;
	
	private Integer dataSize;
	private List<ProdutoFB> datasource;
	private List<PedVendaItemFBDTO> listaPedVendaItem;
	
	private ProdutoFBRN produtoFBRN;
	private PedVendaFB pedVendaFB;
	private String descProdFilter;
	private String codBarraFilter;
	private String fabricanteFilter;
	private String linhaProdFilter;
	private boolean comEstoqueFilter;
	private boolean semEstoqueFilter;
	
	public ProdutoFBLazyDM(FacesContext context, PedVendaFB pedVendaFB, String descProdFilter, String codBarraFilter, String fabricanteFilter, String linhaProdFilter, boolean comEstoqueFilter, boolean semEstoqueFilter, List<PedVendaItemFBDTO> listaPedVendaItem) {
		this.produtoFBRN = new ProdutoFBRN();
		this.pedVendaFB = pedVendaFB;
		this.descProdFilter = descProdFilter;
		this.codBarraFilter = codBarraFilter;
		this.fabricanteFilter = fabricanteFilter;
		this.linhaProdFilter = linhaProdFilter;
		this.comEstoqueFilter = comEstoqueFilter;
		this.semEstoqueFilter = semEstoqueFilter;
		this.listaPedVendaItem = listaPedVendaItem;

		this.dataSize = 0;
		this.produtoFBRN = new ProdutoFBRN();
		
		if(pedVendaFB.getEncomenda().equals(PedVendaFB.ENCOMENDA)) {
			dataSize = produtoFBRN.countVendaSemEstoqueDisponivel(pedVendaFB.getEmpresaId(), pedVendaFB.getMovFiscTipo().getOpFiscTipoId(), descProdFilter, codBarraFilter, linhaProdFilter, pedVendaFB.getTabPrecoId(), pedVendaFB.getCondPagtoId());
		}else if(pedVendaFB.getEncomenda().equals(PedVendaFB.PEDIDO)) {
			dataSize = produtoFBRN.countListar(descProdFilter, codBarraFilter, linhaProdFilter, pedVendaFB.getEmpresaId(), pedVendaFB.getMovFiscTipo().getOpFiscTipoId(), pedVendaFB.getTabPrecoId(), pedVendaFB.getCondPagtoId(), Funcoes.IS_TRANSFERENCIA, Funcoes.COMPARTILHA_ESTOQUE, Funcoes.EMP_ENCH_EST_COMPART, comEstoqueFilter, semEstoqueFilter);
		}else if(pedVendaFB.getEncomenda().equals(PedVendaFB.PEDIDO_PRODUTO_COMPOSTO)) {
			dataSize = produtoFBRN.countProdutosComposto(pedVendaFB.getEmpresaId(), descProdFilter, fabricanteFilter);
		}
		
		if(dataSize==1) {
			try {
				produtoFBRN.paginacao(0, 10, null, null);
				consultaResultado();
			} catch (Exception e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
			}
		}
		
		this.setRowCount(dataSize);
		 
	 }
	
	
	public ProdutoFBLazyDM(FacesContext context, Integer empresaId, Integer opFiscTipoId, String tabPrecoId, Integer condPagtoId, String descProdFilter, String codBarraFilter, String fabricanteFilter, String linhaProdFilter, boolean comEstoqueFilter, boolean semEstoqueFilter) {
		this.produtoFBRN = new ProdutoFBRN();
		this.descProdFilter = descProdFilter;
		this.codBarraFilter = codBarraFilter;
		this.fabricanteFilter = fabricanteFilter;
		this.linhaProdFilter = linhaProdFilter;
		this.comEstoqueFilter = comEstoqueFilter;
		this.semEstoqueFilter = semEstoqueFilter;
		this.listaPedVendaItem = null;
		this.pedVendaFB = new PedVendaFB();
		this.pedVendaFB.setEncomenda(PedVendaFB.PEDIDO);
		this.pedVendaFB.setEmpresaId(empresaId);
		this.pedVendaFB.setTabPrecoId(tabPrecoId);
		this.pedVendaFB.setCondPagtoId(condPagtoId);
		
		MovFiscTipoFB movFiscTipoFB = new MovFiscTipoFB();
		movFiscTipoFB.setOpFiscTipoId(opFiscTipoId);
		this.pedVendaFB.setMovFiscTipo(movFiscTipoFB);
		

		this.dataSize = 0;
		this.produtoFBRN = new ProdutoFBRN();
		dataSize = produtoFBRN.countListar(descProdFilter, codBarraFilter, linhaProdFilter, pedVendaFB.getEmpresaId(), pedVendaFB.getMovFiscTipo().getOpFiscTipoId(), pedVendaFB.getTabPrecoId(), pedVendaFB.getCondPagtoId(), Funcoes.IS_TRANSFERENCIA, Funcoes.COMPARTILHA_ESTOQUE, Funcoes.EMP_ENCH_EST_COMPART, comEstoqueFilter, semEstoqueFilter);
		
		
		if(dataSize==1) {
			try {
				produtoFBRN.paginacao(0, 10, null, null);
				consultaResultado();
			} catch (Exception e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
			}
		}
		
		this.setRowCount(dataSize);
		 
	 }
    
    @Override
    public ProdutoFB getRowData(String rowKey) {
//        for(ProdutoFB rs : datasource) {
//            if(rs.getId().equals(rowKey))
//                return rs;
//        }
    	
    	if(datasource!=null && datasource.size()>0) {
    		try {
    			Integer position = Integer.parseInt(rowKey);
        		return datasource.get(position);
			} catch (Exception e) {
				return null;
			}
    	}
 
        return null;
    }
 
    @Override
    public Object getRowKey(ProdutoFB produtoFB) {
        return produtoFB.getId();
    }
 
    @Override
    public List<ProdutoFB> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
		try {
			
			this.produtoFBRN = new ProdutoFBRN();
			produtoFBRN.paginacao(first, pageSize, sortField, sortOrder);
			consultaResultado();
			
			return datasource;
			
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
			return new ArrayList<ProdutoFB>();
		}
        
    }
    
    private void consultaResultado() throws RNException {
		String tabPrecoId = pedVendaFB.getTabPrecoId();
		if(pedVendaFB.getCondPagto()!=null) {
			CondPagtoFB condPagtoFB = new CondPagtoFBRN().carregar(pedVendaFB.getCondPagto().getId(), pedVendaFB.getEmpresaId(), pedVendaFB.getClienteId());
			tabPrecoId = condPagtoFB.getTabPrecoId();
		}
    	
    	if(pedVendaFB.getEncomenda().equals(PedVendaFB.ENCOMENDA)) {
			datasource = produtoFBRN.listarVendaSemEstoqueDisponivel(pedVendaFB.getEmpresaId(), pedVendaFB.getMovFiscTipo().getOpFiscTipoId(), descProdFilter, codBarraFilter, linhaProdFilter, pedVendaFB.getCondPagto().getTabPrecoId(), pedVendaFB.getCondPagtoId(), pedVendaFB.getFreteTipoId());
		}else if(pedVendaFB.getEncomenda().equals(PedVendaFB.PEDIDO)) {
			datasource = produtoFBRN.listar(descProdFilter, codBarraFilter, linhaProdFilter, pedVendaFB.getEmpresaId(), pedVendaFB.getMovFiscTipo().getOpFiscTipoId(), tabPrecoId, pedVendaFB.getCondPagtoId(), Funcoes.IS_TRANSFERENCIA, Funcoes.COMPARTILHA_ESTOQUE, Funcoes.EMP_ENCH_EST_COMPART, comEstoqueFilter, semEstoqueFilter, pedVendaFB.getFreteTipoId());
		}else if(pedVendaFB.getEncomenda().equals(PedVendaFB.PEDIDO_PRODUTO_COMPOSTO)) {
			datasource = produtoFBRN.listarProdutosComposto(pedVendaFB.getEmpresaId(), pedVendaFB.getMovFiscTipo().getOpFiscTipoId(), descProdFilter, fabricanteFilter, tabPrecoId, pedVendaFB.getCondPagtoId());
		} 
		
		if(datasource!=null) {
			for(ProdutoFB rs : datasource) {
				rs.setInPedVenda(verProdutoInPedVenda(rs.getId()));
			}
		} 
	}

	private boolean verProdutoInPedVenda(Integer produtoFBId) {
		if(listaPedVendaItem!=null) {
			if(pedVendaFB.getIsPedido() || pedVendaFB.getIsEncomenda()) {
				for(PedVendaItemFBDTO rs : listaPedVendaItem) {
					if(produtoFBId.equals(rs.getProdutoId())) {
						return true;
					}
				}
			}else if(pedVendaFB.getIsProdComposto()){
				for(PedVendaItemFBDTO rs : listaPedVendaItem) {
					if(produtoFBId.equals(rs.getProdCompostoId())) {
						return true;
					}
				}
			}
		}
		return false;
	}

}