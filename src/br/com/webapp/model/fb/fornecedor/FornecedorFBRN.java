package br.com.webapp.model.fb.fornecedor;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.webapp.model.fb.vendedor.VendedorFB;
import br.com.webapp.web.util.DAOFactoryFirebird;
import br.com.webapp.web.util.DAOFactoryPostGres;


public class FornecedorFBRN {

	
	private FornecedorFBDAO fornecedorFirebirdDAO;
	
	public FornecedorFBRN(){
		this.fornecedorFirebirdDAO = DAOFactoryFirebird.criarFornecedorDao();
	}
	
	public FornecedorFB carregar(Integer fornecedorId) {
		return this.fornecedorFirebirdDAO.carregar(fornecedorId);
	}
	
	public List<FornecedorFB> listarParaPlanilhaCega(Integer empresaId, String descricaoFilter){
		return this.fornecedorFirebirdDAO.listarParaPlanilhaCega(empresaId, descricaoFilter);
	}

	public List<FornecedorFB> listar(String descricaoFilter, Integer empresaId) {
		return this.fornecedorFirebirdDAO.listar(descricaoFilter, empresaId);
	}

	public List<FornecedorFB> listar(String descricao) {
		return this.fornecedorFirebirdDAO.listar(descricao);
	}

	public List<SelectItem> montaDadosSelect(List<FornecedorFB> fornecedores, String descricao) {
		List<SelectItem> select = new ArrayList<SelectItem>();
		SelectItem item = null;
		if (fornecedores != null) {
			for (FornecedorFB rs : fornecedores) {
				item = new SelectItem(rs, rs.getNomeFantasia());
				item.setEscape(false);
				select.add(item);
			}
		}
		
		return select;
	}
	
}
