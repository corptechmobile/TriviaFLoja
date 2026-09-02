package br.com.webapp.model.fb.orcamentometaitem;

import java.util.List;

import br.com.webapp.web.util.DAOException;

public interface OrcamentoMetaItemFBDAO {

	public void alterar(OrcamentoMetaItemFB comissaoFaixaDescFB) throws DAOException;
	public Integer insert(OrcamentoMetaItemFB comissaoFaixaDescFB) throws DAOException;
	public void rollback();
	public OrcamentoMetaItemFB salvar(OrcamentoMetaItemFB comissaoFaixaDescFB);
	void excluir(OrcamentoMetaItemFB metaGastoFinanceiroFB) throws DAOException;
	public OrcamentoMetaItemFB carregar(Integer id);
	public List<OrcamentoMetaItemFB> listar(String anomes, Integer id_pessoa_emp);
	public List<OrcamentoMetaItemFB> listar(String anomes, String anomesref, Integer idPessoaEmp);
	public List<OrcamentoMetaItemFBDTO> listarAno(String ano, Integer idPessoaEmp, Integer idOrcamentoGrupo);

	
}
