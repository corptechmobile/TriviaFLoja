package br.com.webapp.model.fb.empresa;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.webapp.model.fb.usuario.UsuarioFB;
import br.com.webapp.web.util.DAOFactoryFirebird;

public class EmpresaFBRN {

	private EmpresaFBDAO empresaFBDAO;

	public EmpresaFBRN() {
		this.empresaFBDAO = DAOFactoryFirebird.criarEmpresaFBDAO();
	}

	public EmpresaFB carregar(Integer empresaId) {
		return this.empresaFBDAO.carregar(empresaId);
	}

	public List<EmpresaFB> listar(UsuarioFB usuarioFB) {
		return this.empresaFBDAO.listar(usuarioFB);
	}

	public List<SelectItem> montaDadosSelect(List<EmpresaFB> empresas, String string) {

		List<SelectItem> select = new ArrayList<SelectItem>();
		SelectItem item = null;
		if (empresas != null) {
			for (EmpresaFB empresa : empresas) {
				item = new SelectItem(empresa, empresa.getNomeFantasia());
				item.setEscape(false);
				select.add(item);
			}
		}

		return select;
	}

	public List<EmpresaFB> listar() {
		return this.empresaFBDAO.listar();
	}

}
