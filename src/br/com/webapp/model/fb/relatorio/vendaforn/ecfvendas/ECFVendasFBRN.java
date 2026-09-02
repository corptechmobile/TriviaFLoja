package br.com.webapp.model.fb.relatorio.vendaforn.ecfvendas;

import java.util.Date;
import java.util.List;

import br.com.webapp.model.fb.tipovendedor.TipoVendedorFB;
import br.com.webapp.web.util.DAOFactoryFirebird;

public class ECFVendasFBRN {

	private ECFVendasFBDAO ecfVendasFBDAO;
	
	public ECFVendasFBRN() {
		ecfVendasFBDAO = DAOFactoryFirebird.criarECFVendasFBDAO();
	}
	
	public List<ECFVendasFB> listarACancelar(Integer empresaId, Integer vendedorId, Integer tipoVendedorId, Date dataFilter1, Date dataFilter2){
		return this.ecfVendasFBDAO.listarACancelar(empresaId, vendedorId, tipoVendedorId, dataFilter1, dataFilter2);
	}
}
