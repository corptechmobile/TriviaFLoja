package br.com.webapp.model.fb.orcamentogrupo;

import java.util.List;

import br.com.webapp.web.util.DAOException;

public interface OrcamentoGrupoFBDAO {

	public OrcamentoGrupoFB carregar(Integer Id);
	public List<OrcamentoGrupoFB> listar(String descricao);
	public List<OrcamentoGrupoFB> listar();
	public void excluir(Integer Id) throws DAOException;
	public void alterar(OrcamentoGrupoFB selecionada) throws DAOException;
	public Integer insert(OrcamentoGrupoFB selecionada) throws DAOException;
}
