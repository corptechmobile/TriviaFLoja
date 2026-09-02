package br.com.webapp.model.fb.comissaofaixadesc;

import java.util.List;

import br.com.webapp.web.util.DAOException;

public interface ComissaoFaixaDescFBDAO {

	public ComissaoFaixaDescFB carregar(Integer Id);
	public List<ComissaoFaixaDescFB> listar(String descLinhaProd, Double descfaixa1, Double descfaixa2);
	public void alterar(ComissaoFaixaDescFB comissaoFaixaDescFB) throws DAOException;
	public Integer insert(ComissaoFaixaDescFB comissaoFaixaDescFB) throws DAOException;
	public void rollback();
	public ComissaoFaixaDescFB salvar(ComissaoFaixaDescFB comissaoFaixaDescFB);
	public void excluir(Integer Id) throws DAOException;
	public ComissaoFaixaDescFB validarFaixa(ComissaoFaixaDescFB comissaoFaixaDescFB);
}
