package br.com.webapp.model.fb.relatorio.vendaforn.ecfvendas;

import java.util.Date;
import java.util.List;

import br.com.webapp.model.fb.tipovendedor.TipoVendedorFB;

public interface ECFVendasFBDAO {

	public List<ECFVendasFB> listarACancelar(Integer empresaId, Integer vendedorId, Integer tipoVendedorId, Date dataFilter1, Date dataFilter2);
	
}
