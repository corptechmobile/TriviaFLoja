package br.com.webapp.model.fb.relatorio.vendaforn.pedvendadiverg;

import java.util.Date;
import java.util.List;

import br.com.webapp.model.fb.cliente.ClienteFB;
import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.usuario.UsuarioFB;
import br.com.webapp.model.fb.vendedor.VendedorFB;
import br.com.webapp.web.util.DAOFactoryFirebird;
import br.com.webapp.web.util.Funcoes;

public class PedVendaDivergRelRN {

	private PedVendaDivergRelDAO pedVendaDivergRelDAO;
	
	public PedVendaDivergRelRN() {
		pedVendaDivergRelDAO = DAOFactoryFirebird.criarPedVendaDivergRelDAO();
	}
	
	public List<PedVendaDivergRel> listar(VendedorFB vendedorFB, EmpresaFB empresaFB, String produtoFilter, Integer tipoDataFilter, Date dataFilter1, Date dataFilter2, UsuarioFB usuarioFB, Integer tipoDiverg, Integer situacaoDiverg){
		dataFilter1 = Funcoes.dataFilter1(dataFilter1);
		dataFilter2 = Funcoes.dataFilter2(dataFilter2);
		
		String[] splitDescricao = null;
		if (produtoFilter != null && !"".equals(produtoFilter)) {
				splitDescricao = produtoFilter.split(" ");
		}
		
		return pedVendaDivergRelDAO.listar(vendedorFB, empresaFB, produtoFilter, splitDescricao, tipoDataFilter, dataFilter1, dataFilter2, usuarioFB, tipoDiverg, situacaoDiverg);
	}
}
