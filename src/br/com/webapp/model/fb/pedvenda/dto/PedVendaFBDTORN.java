package br.com.webapp.model.fb.pedvenda.dto;

import java.util.Date;
import java.util.List;

import br.com.webapp.model.fb.cliente.ClienteFB;
import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.pedvendastatus.PedVendaStatusFB;
import br.com.webapp.model.fb.usuario.UsuarioFB;
import br.com.webapp.model.fb.vendedor.VendedorFB;
import br.com.webapp.web.util.DAOFactoryFirebird;
import br.com.webapp.web.util.Funcoes;

public class PedVendaFBDTORN {
	
	private PedVendaFBDTODAO pedVendaFBDTODAO;
	
	public PedVendaFBDTORN() {
		this.pedVendaFBDTODAO =  DAOFactoryFirebird.criarPedVendaFBDTODAO();
	}
	
	public PedVendaFBDTO carregar(Integer id) {
		return this.pedVendaFBDTODAO.carregar(id);
	}
	
	public List<PedVendaFBDTO> listar(String numPedidoFilter, String tipoDataFilter, Date dataFilter1, Date dataFilter2, EmpresaFB empresaFilter, VendedorFB vendedorFilter, ClienteFB clienteFilter, PedVendaStatusFB statusFilter, Boolean carteiraFilter, Integer tipoPedido, UsuarioFB usuario){
		dataFilter1 = Funcoes.dataFilter1(dataFilter1);
		dataFilter2 = Funcoes.dataFilter2(dataFilter2);
		return this.pedVendaFBDTODAO.listar(numPedidoFilter, tipoDataFilter, dataFilter1, dataFilter2, empresaFilter, vendedorFilter, clienteFilter, statusFilter, carteiraFilter, tipoPedido, usuario);
	}
	
	public List<PedVendaFBDTO> listarBloqueados(String descricaoFilter, VendedorFB vendedorFilter, Date dataFilter1, Date dataFilter2, UsuarioFB usuario){
		dataFilter1 = Funcoes.dataFilter1(dataFilter1);
		dataFilter2 = Funcoes.dataFilter2(dataFilter2);
		return this.pedVendaFBDTODAO.listarBloqueados(descricaoFilter, vendedorFilter, dataFilter1, dataFilter2, usuario);
	}

	public List<PedVendaFBDTO> listarEmRecebimento(String descricaoFilter, VendedorFB vendedorFilter, UsuarioFB usuario) {
		return this.pedVendaFBDTODAO.listarEmRecebimento(descricaoFilter, vendedorFilter, usuario);
	}
}
