package br.com.webapp.model.fb.coletorcontagem;

import java.util.Date;
import java.util.List;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.usuario.UsuarioFB;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.DAOFactoryFirebird;
import br.com.webapp.web.util.Funcoes;
import br.com.webapp.web.util.RNException;
import br.com.webapp.web.util.UtilData;

public class ColetorInvContagemFBRN {

	private ColetorInvContagemFBDAO coletorInvContagemFBDAO;
	
	public ColetorInvContagemFBRN() {
		this.coletorInvContagemFBDAO = DAOFactoryFirebird.criarColetorInvContagemFB();
	}
	
	public ColetorInvContagemFB carregar(Integer id) {
		return this.coletorInvContagemFBDAO.carregar(id);
	}
	
	public static String gerarChaveContagem(
            Integer coletorInvId,
            Integer usuarioId,
            Integer produtoId,
            String codBarra) {

        String rawData = coletorInvId + "|"
                + usuarioId + "|"
                + produtoId + "|"
                + codBarra + "|"
                + UtilData.formatarData(new Date(), UtilData.FORMATO_DATA_HORA);

        String md5Result = Funcoes.gerarMD5Hex(rawData).toUpperCase();

        return coletorInvId + "|" + usuarioId + "|" + md5Result;
    }
	
	public List<ColetorInvContagemFB> listar(){
		return this.coletorInvContagemFBDAO.listar();
	}
	
	public ColetorInvContagemFB salvarNovo(ColetorInvContagemFB inventario) throws DAOException, RNException {
		
		return salvar(inventario);
	}

	private ColetorInvContagemFB salvar(ColetorInvContagemFB inventario) throws DAOException {
		
		if(inventario.getId()==null) {
			inventario = new ColetorInvContagemFBRN().carregar(this.coletorInvContagemFBDAO.insert(inventario));
		}else {
			this.coletorInvContagemFBDAO.update(inventario);
			inventario = new ColetorInvContagemFBRN().carregar(inventario.getId());
		}
		
		return inventario;
		
	}

	public ColetorInvContagemFB novo(UsuarioFB usuarioLogado) {
		ColetorInvContagemFB inventario = new ColetorInvContagemFB();
		inventario.setUsuarioId(usuarioLogado.getId());
		return inventario;
	}

	public ColetorInvContagemFB editar(Integer inventarioId) {
		ColetorInvContagemFB inventario = this.carregar(inventarioId);
		return inventario;
	}

	public void finalizar(ColetorInvContagemFB selecionada) {
		// TODO Auto-generated method stub
		
	}

	public void excluir(Integer inventarioId) throws DAOException {
		this.coletorInvContagemFBDAO.excluir(inventarioId);
	}
	
	public void excluir(Integer inventarioId, Integer produtoId, String codBarra, String agrupadoPorFilter) throws DAOException {
		this.coletorInvContagemFBDAO.excluir(inventarioId, produtoId, codBarra, agrupadoPorFilter);
	}
	

	public List<ColetorInvContagemFB> listar(EmpresaFB empresaFilter, Date data1Filter, Date data2Filter, boolean concluidoFilter) {
		data1Filter = Funcoes.dataFilter1(data1Filter);
		data2Filter = Funcoes.dataFilter2(data2Filter);

		return this.coletorInvContagemFBDAO.listar(empresaFilter, data1Filter, data2Filter, concluidoFilter);
	}

	public List<ColetorInvContagemFBDTO> listarProdutoEmbalagem(Integer coletorInvId, String produtoFilter, String usuarioFilter, boolean divergenciaFilter, String agrupadoPorFilter) {
		return this.coletorInvContagemFBDAO.listarProdutoEmbalagem(coletorInvId, produtoFilter, usuarioFilter, divergenciaFilter, agrupadoPorFilter);
	}

	public List<ColetorInvContagemFB> listar(Integer coletorInvId, ColetorInvContagemFBDTO leitura, String descEmbFechVenda, String agrupadoPorFilter) {
		return this.coletorInvContagemFBDAO.listar(coletorInvId, leitura, descEmbFechVenda, agrupadoPorFilter);
	}

	public void atualizarProduto(Integer coletorInvId, Integer produtoId, String codBarra) throws DAOException {
		this.coletorInvContagemFBDAO.atualizarProduto(coletorInvId, produtoId, codBarra);
	}

	public List<ColetorInvContagemFBDTO> verificarDivergencias(Integer coletorInvId) {
		return this.coletorInvContagemFBDAO.verificarDivergencias(coletorInvId);		
	}

}
