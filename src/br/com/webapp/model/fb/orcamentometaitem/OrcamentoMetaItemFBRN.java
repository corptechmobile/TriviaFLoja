package br.com.webapp.model.fb.orcamentometaitem;

import java.util.List;

import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.DAOFactoryFirebird;
import br.com.webapp.web.util.RNException;

public class OrcamentoMetaItemFBRN {

	private OrcamentoMetaItemFBDAO orcamentoMetaItemFBDAO;

	public OrcamentoMetaItemFBRN() {
		this.orcamentoMetaItemFBDAO = DAOFactoryFirebird.criarOrcamentoMetaItemFBDAO();
	}

	public List<OrcamentoMetaItemFB> listar(String anomes, Integer id_pessoa_emp) {
		return this.orcamentoMetaItemFBDAO.listar(anomes, id_pessoa_emp);
	}
 
	public List<OrcamentoMetaItemFB> listar(String anomes, String anomesref, Integer id_pessoa_emp) {
		return this.orcamentoMetaItemFBDAO.listar(anomes, anomesref, id_pessoa_emp);
	}
	
	public List<OrcamentoMetaItemFBDTO> listarAno(String ano, Integer id_pessoa_emp, Integer idOrcamentoGrupo) {
		return this.orcamentoMetaItemFBDAO.listarAno(ano, id_pessoa_emp, idOrcamentoGrupo);
	}
	
	public Integer inserir(OrcamentoMetaItemFB orcamentoMetaItemFB) throws DAOException, RNException {
			return this.orcamentoMetaItemFBDAO.insert(orcamentoMetaItemFB);
	}

	public void alterar(OrcamentoMetaItemFB orcamentoMetaItemFB) throws DAOException, RNException {
			this.orcamentoMetaItemFBDAO.alterar(orcamentoMetaItemFB);

	}

	public void excluir(OrcamentoMetaItemFB orcamentoMetaItemFB) throws DAOException {
		this.orcamentoMetaItemFBDAO.excluir(orcamentoMetaItemFB);
	}

}
