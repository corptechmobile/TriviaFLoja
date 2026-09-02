package br.com.webapp.model.fb.tipovendedor;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.webapp.web.util.DAOFactoryFirebird;

public class TipoVendedorFBRN {

	private TipoVendedorFBDAO TipoVendedorFBDAO;
	
	public TipoVendedorFBRN(){
		this.TipoVendedorFBDAO = DAOFactoryFirebird.criarTipoVendedorFBDAO();
	}
	
	public TipoVendedorFB carregar(Integer vendedorId) {
		return this.TipoVendedorFBDAO.carregar(vendedorId);
	}
	
	public List<TipoVendedorFB> listar(String descricaoFilter) {
		return this.TipoVendedorFBDAO.listar(descricaoFilter);
	}
	
	public List<SelectItem> montaDadosSelect(List<TipoVendedorFB> tipovendedores, String string) {
		
		List<SelectItem> select = new ArrayList<SelectItem>();
		SelectItem item = null;
		if (tipovendedores != null) {
			for (TipoVendedorFB rs : tipovendedores) {
				item = new SelectItem(rs, rs.getDescricao());
				item.setEscape(false);
				select.add(item);
			}
		}
		
		return select;
		
	}
	
}
