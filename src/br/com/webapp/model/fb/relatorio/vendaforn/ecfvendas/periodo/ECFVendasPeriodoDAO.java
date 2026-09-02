package br.com.webapp.model.fb.relatorio.vendaforn.ecfvendas.periodo;

import java.util.Date;
import java.util.List;

public interface ECFVendasPeriodoDAO {

	public List<ECFVendasPeriodo> listar(Integer empresaId, Date dataFilter1, Date dataFilter2);
}
