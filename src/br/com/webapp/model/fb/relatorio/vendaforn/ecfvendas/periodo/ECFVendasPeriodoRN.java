package br.com.webapp.model.fb.relatorio.vendaforn.ecfvendas.periodo;

import java.util.Date;
import java.util.List;

import br.com.webapp.web.util.DAOFactoryFirebird;

public class ECFVendasPeriodoRN {

	private ECFVendasPeriodoDAO ecfVendasPeriodoDAO;
	
	public ECFVendasPeriodoRN() {
		ecfVendasPeriodoDAO = DAOFactoryFirebird.criarECFVendasPeriodoDAO();
	}
	
	public List<ECFVendasPeriodo> listar(Integer empresaId, Date dataFilter1, Date dataFilter2){
		return ecfVendasPeriodoDAO.listar(empresaId, dataFilter1, dataFilter2);
	}
}
