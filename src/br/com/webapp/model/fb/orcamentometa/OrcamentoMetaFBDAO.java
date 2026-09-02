package br.com.webapp.model.fb.orcamentometa;

import java.util.List;

import br.com.webapp.web.util.DAOException;

public interface OrcamentoMetaFBDAO {

	public void alterar(OrcamentoMetaFB orcamentoMetaFB) throws DAOException;
	public Integer insert(OrcamentoMetaFB orcamentoMetaFB) throws DAOException;
	public void rollback();
	public OrcamentoMetaFB salvar(OrcamentoMetaFB orcamentoMetaFB);
	public OrcamentoMetaFB validarFaixa(OrcamentoMetaFB orcamentoMetaFB);
	void excluir(OrcamentoMetaFB metaGastoFinanceiroFB) throws DAOException;
	public OrcamentoMetaFB carregar(String anomes, Integer idPessoaEmp);
	public OrcamentoMetaFB carregar(Integer id);
	public List<OrcamentoMetaFB> listar(String ano);
	public List<OrcamentoMetaFB> listar(String anoMes, Integer empresaId);
	
}
