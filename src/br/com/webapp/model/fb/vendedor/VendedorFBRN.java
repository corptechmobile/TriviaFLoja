package br.com.webapp.model.fb.vendedor;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.webapp.model.fb.usuario.UsuarioFB;
import br.com.webapp.web.util.DAOFactoryFirebird;

public class VendedorFBRN {

	private VendedorFBDAO vendedorFBDAO;
	
	public VendedorFBRN(){
		this.vendedorFBDAO = DAOFactoryFirebird.criarVendedorFBDAO();
	}
	
	public VendedorFB carregar(Integer vendedorId) {
		return this.vendedorFBDAO.carregar(vendedorId);
	}
	
	public VendedorFB carregar(UsuarioFB usuarioFB) {
		return this.vendedorFBDAO.carregar(usuarioFB);
	}

	public VendedorFB carregar(String cnpjCpf) {
		return this.vendedorFBDAO.carregar(cnpjCpf);
	}
	
	public List<VendedorFB> listar(String descricaoFilter) {
		return this.vendedorFBDAO.listar(descricaoFilter);
	}
	
	public List<SelectItem> montaDadosSelect(List<VendedorFB> vendedores, String string) {
		
		List<SelectItem> select = new ArrayList<SelectItem>();
		SelectItem item = null;
		if (vendedores != null) {
			for (VendedorFB rs : vendedores) {
				item = new SelectItem(rs, rs.getNomeFantasia());
				item.setEscape(false);
				select.add(item);
			}
		}
		
		return select;
		
	}

	public VendedorFB verificarAssocTipoVendEmp(Integer vendedorId, Integer empresaId) {
		return this.vendedorFBDAO.verificarAssocTipoVendEmp(vendedorId, empresaId);
	}
	
}
