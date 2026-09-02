package br.com.webapp.model.fb.produtocb;

import java.util.ArrayList;
import java.util.List;

import br.com.coletor.espelho.EspelhoProdutoCB;
import br.com.webapp.model.fb.produto.ProdutoFB;
import br.com.webapp.model.fb.produto.ProdutoFBRN;
import br.com.webapp.model.fb.produtolinha.ProdutoLinhaFB;
import br.com.webapp.model.fb.usuario.UsuarioFB;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.DAOFactoryFirebird;
import br.com.webapp.web.util.RNException;
import br.com.webapp.web.util.UtilMessage;


public class ProdutoCBFBRN {
	
	private ProdutoCBFBDAO produtoCBFBDAO;
	
	public ProdutoCBFBRN(){
		this.produtoCBFBDAO = DAOFactoryFirebird.criarProdutoCBFBDAO();
	}
	
	public ProdutoCBFB carregar(String codigobarras) {
		return this.produtoCBFBDAO.carregar(codigobarras);
	}
	
	public ProdutoCBFB carregar(Integer produtoId, String codigobarras) throws RNException {
		return this.produtoCBFBDAO.carregar(produtoId, codigobarras);
	}
	
	private ProdutoCBFB carregar(Integer produtoId, Double qtd) {
		return this.produtoCBFBDAO.carregar(produtoId, qtd);
	}
	
	public ProdutoCBFB novo(UsuarioFB usuario, String codigoBarras){
		ProdutoCBFB produtoCB = new ProdutoCBFB();
		
		ProdutoCBFBId produtoCBFBId = new ProdutoCBFBId();
		produtoCBFBId.setProdutoId(null);
		produtoCBFBId.setCodigoBarras(codigoBarras);
		
		produtoCB.setId(produtoCBFBId);
		produtoCB.setQtd(1d);
		
		if(usuario != null) {
			produtoCB.setUsuarioCreateId(usuario.getId());
			produtoCB.setUsuarioUpdateId(usuario.getId());
		}
		
		produtoCB.setExcluido(false);
		return produtoCB;
	}
	
	public ProdutoCBFB novo(UsuarioFB usuario, Integer produtoId, String codigoBarras){
		ProdutoCBFB produtoCB = this.novo(usuario, codigoBarras);
		produtoCB.getId().setProdutoId(produtoId);
		return produtoCB;
	}
	
	public void validar(ProdutoCBFB produtoCB) throws RNException{
		
		if(produtoCB.getId().getCodigoBarras() == null || "".equals(produtoCB.getId().getCodigoBarras())){
			throw new RNException(UtilMessage.mensagem("msg.verificar_codigo_vazio.produtocb"));
		}
		
		if(produtoCB.getId().getProdutoId() == null){
			throw new RNException(UtilMessage.mensagem("msg.verificar_produto_vazio.produtocb"));
		}
		
		// Verificar se o Produto ja tem codigo de barras com a quantidade informada
		ProdutoCBFB verProdutoCB = this.carregar(produtoCB.getId().getProdutoId(), produtoCB.getQtd());
		ProdutoFB produtoFB = new ProdutoFBRN().carregar(produtoCB.getId().getProdutoId());
		if(verProdutoCB != null && produtoCB.getId().getCodigoBarras().equals(verProdutoCB.getId().getCodigoBarras()) == false) {
			throw new RNException(String.format(UtilMessage.mensagem("msg.verificar_produto_qtd_cadastrada.produtocb"), produtoCB.getId().getCodigoBarras(), produtoFB.getCodInterno(), produtoFB.getDescricao(), verProdutoCB.getId().getCodigoBarras(), verProdutoCB.getQtd().toString()));
		}
		
	}
	
	public void excluir(ProdutoCBFB produtoCB) throws DAOException{
		this.produtoCBFBDAO.excluir(produtoCB);
	}
	
	public List<ProdutoCBFB> listar(){
		return this.produtoCBFBDAO.listar();
	}

	public List<ProdutoCBFB> listar(ProdutoLinhaFB produtoLinhaFilter, String produtoFilter, String codigoBarraFilter) {
		return this.produtoCBFBDAO.listar(produtoLinhaFilter, produtoFilter, codigoBarraFilter);
	}

	public void update(ProdutoCBFB produtoCBFB) throws DAOException, RNException {
		this.validar(produtoCBFB);
		this.produtoCBFBDAO.update(produtoCBFB);
		
	}

	public void insert(ProdutoCBFB produtoCBFB) throws DAOException, RNException {
		this.validar(produtoCBFB);
		this.produtoCBFBDAO.insert(produtoCBFB);
	}
	
	public ProdutoCBFB salvar(ProdutoCBFB selecionada) throws RNException {
		try {
			return this.produtoCBFBDAO.salvar(selecionada);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RNException(UtilMessage.mensagem("msg.erro.salvar.produtocb"));
		}
	}
	
	public List<EspelhoProdutoCB> listarToSincColetor(){
		List<EspelhoProdutoCB> result = new ArrayList<>();
		for(ProdutoCBFB rs : this.produtoCBFBDAO.listar()) {
			result.add(new EspelhoProdutoCB(rs));
		}
		return result;
	}
	
}
