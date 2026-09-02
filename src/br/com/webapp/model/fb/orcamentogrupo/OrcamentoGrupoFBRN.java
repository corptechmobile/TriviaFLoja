package br.com.webapp.model.fb.orcamentogrupo;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.webapp.model.fb.comissaofaixadesc.ComissaoFaixaDescFB;
import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.DAOFactoryFirebird;
import br.com.webapp.web.util.RNException;
import br.com.webapp.web.util.UtilMessage;

public class OrcamentoGrupoFBRN {

	private OrcamentoGrupoFBDAO orcamentoGrupoFBDAO;
	
	public OrcamentoGrupoFBRN() {
		this.orcamentoGrupoFBDAO = DAOFactoryFirebird.criarOrcamentoGrupoFBDAO();
	}
	
	public OrcamentoGrupoFB carregar(Integer orcamentoGrupoId) {
		return this.orcamentoGrupoFBDAO.carregar(orcamentoGrupoId);
	}
	
	public List<OrcamentoGrupoFB> listar(String descricao) {
		return this.orcamentoGrupoFBDAO.listar(descricao);
	}

	public List<OrcamentoGrupoFB> listar() {
		return this.orcamentoGrupoFBDAO.listar();
	}
	
	public List<SelectItem> montaDadosSelect(List<OrcamentoGrupoFB> orcamentoGrupos, String string) {
		List<SelectItem> select = new ArrayList<SelectItem>();
		SelectItem item = null;
		if (orcamentoGrupos != null) {
			for (OrcamentoGrupoFB orcamentoGrupoFB : orcamentoGrupos) {
				item = new SelectItem(orcamentoGrupoFB, orcamentoGrupoFB.getDescricao());
				item.setEscape(false);
				select.add(item);
			}
		}
		 
		return select;
		
	}

	public void excluir(Integer selecionadaId) throws DAOException {
		this.orcamentoGrupoFBDAO.excluir(selecionadaId);
	}

	public void alterar(OrcamentoGrupoFB selecionada) throws DAOException {
		this.orcamentoGrupoFBDAO.alterar(selecionada);
		
	}

	public Integer inserir(OrcamentoGrupoFB selecionada) throws DAOException {
			return this.orcamentoGrupoFBDAO.insert(selecionada);
	}
	

	
}
	

