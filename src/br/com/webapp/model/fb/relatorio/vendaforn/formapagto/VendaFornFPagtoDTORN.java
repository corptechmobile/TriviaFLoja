package br.com.webapp.model.fb.relatorio.vendaforn.formapagto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.tipovendedor.TipoVendedorFB;
import br.com.webapp.model.fb.vendedor.VendedorFB;
import br.com.webapp.web.util.DAOFactoryFirebird;
import br.com.webapp.web.util.Funcoes;

public class VendaFornFPagtoDTORN {

	private VendaFornFPagtoDTODAO vendaFornFPagtoDTODAO;
	
	private VendaFornFPagtoDTO novo(Integer formaPagtoRecId, Integer condPagtoId, String descricao, Double valor,  Double desconto, Integer parcela) {
		VendaFornFPagtoDTO model = new VendaFornFPagtoDTO();
		model.setFormaPagtoRecId(formaPagtoRecId);
		model.setCondPagtoId(condPagtoId);
		model.setDescricao(descricao);
		model.setValor(valor);
		model.setDesconto(desconto);
		model.setParcela(parcela);
		model.setFilhos(new ArrayList<VendaFornFPagtoDTO>());
		return model;
	}

	public VendaFornFPagtoDTORN() {
		vendaFornFPagtoDTODAO = DAOFactoryFirebird.criarVendaFornFPagtoDTODAO();
	}
	/* TODO Nao utilizar
	public List<VendaFornFPagtoDTO> listar(EmpresaFB empresaFB, Date dataFilter1, Date dataFilter2){
		
		dataFilter1 = Funcoes.dataFilter1(dataFilter1);
		dataFilter2 = Funcoes.dataFilter2(dataFilter2);
		
		List<VendaFornFPagtoDTO> resultQuery = vendaFornFPagtoDTODAO.listar(empresaFB, dataFilter1, dataFilter2);
		List<VendaFornFPagtoDTO> result =  new ArrayList<VendaFornFPagtoDTO>();
		String varParcela = "-1";
		VendaFornFPagtoDTO formaPagto = null;
		if(resultQuery!=null) {
			for(VendaFornFPagtoDTO rs : resultQuery) {
				if(!varParcela.equals("" + (rs.getParcela()!=null ? rs.getParcela() : rs.getDescricao()))) {
					varParcela = ""+rs.getParcela();
					if(rs.getParcela()!=null) {
						formaPagto = this.novo(rs.getFormaPagtoRecId(), rs.getCondPagtoId(), "CARTÃO ("+rs.getParcela()+"x)", null, null, rs.getParcela());
					}else {
						formaPagto = this.novo(rs.getFormaPagtoRecId(), rs.getCondPagtoId(), rs.getDescricao(), rs.getValor(), rs.getDesconto(), null);
					}
					result.add(formaPagto);
				}
				
				if(rs.getParcela()!=null) {
					formaPagto.getFilhos().add(rs);
				}else {
					formaPagto.setFilhos(null);
				}
			}
		}
		
		return result;
		
	}
	*/
	
	public List<VendaFornFPagtoDTO> listar(EmpresaFB empresaFB, VendedorFB vendedorFilter, TipoVendedorFB tipoVendedorFilter, Date dataFilter1, Date dataFilter2){
		dataFilter1 = Funcoes.dataFilter1(dataFilter1);
		dataFilter2 = Funcoes.dataFilter2(dataFilter2);
		
		List<VendaFornFPagtoDTO> result =  new ArrayList<VendaFornFPagtoDTO>();
		result.addAll(vendaFornFPagtoDTODAO.listarCondPagto(empresaFB, vendedorFilter, tipoVendedorFilter, dataFilter1, dataFilter2));
		result.addAll(vendaFornFPagtoDTODAO.listarOutros(empresaFB, vendedorFilter, tipoVendedorFilter, dataFilter1, dataFilter2));
		result.addAll(vendaFornFPagtoDTODAO.listarPedidosAFaturar(empresaFB, vendedorFilter, tipoVendedorFilter, dataFilter1, dataFilter2));
		
		List<VendaFornFPagtoDTO> cartoes = vendaFornFPagtoDTODAO.listarCartoesGroupByParcela(empresaFB, vendedorFilter, tipoVendedorFilter, dataFilter1, dataFilter2);
//		for(VendaFornFPagtoDTO rs : cartoes) {
//			rs.setFilhos(new VendaFornFPagtoDTORN().listarCartoes(empresaFB, vendedorFilter, tipoVendedorFilter, dataFilter1, dataFilter2, rs.getParcela()));
//		}
		
		result.addAll(cartoes);
		
		return result;
	}
	
	public List<VendaFornFPagtoDTO> listarCartoes(EmpresaFB empresaFB, VendedorFB vendedorFilter, TipoVendedorFB tipoVendedorFilter, Date dataFilter1, Date dataFilter2, Integer parcelas){
		dataFilter1 = Funcoes.dataFilter1(dataFilter1);
		dataFilter2 = Funcoes.dataFilter2(dataFilter2);
		return vendaFornFPagtoDTODAO.listarCartoes(empresaFB, vendedorFilter, tipoVendedorFilter, dataFilter1, dataFilter2, parcelas);
	}
	
	
	
}
