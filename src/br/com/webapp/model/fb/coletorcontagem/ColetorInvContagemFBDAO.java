package br.com.webapp.model.fb.coletorcontagem;

import java.util.Date;
import java.util.List;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.web.util.DAOException;

public interface ColetorInvContagemFBDAO {
	public ColetorInvContagemFB carregar(Integer id);
	public List<ColetorInvContagemFB> listar();
	public ColetorInvContagemFB salvar(ColetorInvContagemFB inventario);
	public Integer insert(ColetorInvContagemFB inventario) throws DAOException;
	public void update(ColetorInvContagemFB inventario) throws DAOException;
	public void excluir(Integer inventarioId) throws DAOException;
	public void excluir(Integer inventarioId, Integer produtoId, String codBarra, String agrupadoPorFilter) throws DAOException;
	public List<ColetorInvContagemFB> listar(EmpresaFB empresaFilter, Date data1Filter, Date data2Filter, boolean concluidoFilter);
	public List<ColetorInvContagemFBDTO> listarProdutoEmbalagem(Integer coletorInvId, String produtoFilter, String usuarioFilter, boolean divergenciaFilter, String agrupadoPorFilter);
	public List<ColetorInvContagemFB> listar(Integer coletorInvId, ColetorInvContagemFBDTO leitura, String descEmbFechVenda, String agrupadoPorFilter);
	public void atualizarProduto(Integer coletorInvId, Integer produtoId, String codBarra) throws DAOException;
	public List<ColetorInvContagemFBDTO> verificarDivergencias(Integer coletorInvId);

}
