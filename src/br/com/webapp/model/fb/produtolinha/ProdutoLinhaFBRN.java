package br.com.webapp.model.fb.produtolinha;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.webapp.web.util.DAOFactoryFirebird;

public class ProdutoLinhaFBRN {

	private ProdutoLinhaFBDAO produtoLinhaFBDAO;
	
	public ProdutoLinhaFBRN() {
		this.produtoLinhaFBDAO = DAOFactoryFirebird.criarProdutoLinhaFBDAO();
	}
	
	public ProdutoLinhaFB carregar(Integer prodLinhaId) {
		return this.produtoLinhaFBDAO.carregar(prodLinhaId);
	}
	
	public List<ProdutoLinhaFB> listar(){
		return this.produtoLinhaFBDAO.listar();
	}
	
	public List<SelectItem> montaDadosSelect(List<ProdutoLinhaFB> linhas, String string) {
		
		List<SelectItem> select = new ArrayList<SelectItem>();
		SelectItem item = null;
		if (linhas != null) {
			for (ProdutoLinhaFB linha : linhas) {
				item = new SelectItem(linha, linha.getDescricao() +" - "+ linha.getCodEDT());
				item.setEscape(false);
				select.add(item);
			}
		}
		
		return select;
		
	}

	public void montaDadosSelect(List<SelectItem> select, List<ProdutoLinhaFB> linhas, String prefixo) {
			SelectItem item = null;
			List<ProdutoLinhaFB> filhos = null;
			if (linhas != null) {
				for (ProdutoLinhaFB linha : linhas) {
					
					if(linha.getProdutoLinhaPaiId()==null){
						item = new SelectItem(linha, prefixo + linha.getDescricao());
						item.setEscape(false);
						select.add(item);
						
						filhos = getChildrens(linha, linhas);
						if(filhos.size() > 0){
							this.montaDadosSelectMain(select, filhos, linhas, prefixo + "  ");
						}
						
					}
					
				} 
			}
	} 

	private void montaDadosSelectMain(List<SelectItem> select, List<ProdutoLinhaFB> filhos, List<ProdutoLinhaFB> linhas, String prefixo){
		SelectItem item = null;
		List<ProdutoLinhaFB> linhaFilhos = null;
		for (ProdutoLinhaFB linha : filhos) {
			
			item = new SelectItem(linha, prefixo + linha.getDescricao());
			item.setEscape(true);
			select.add(item);
			
			linhaFilhos = getChildrens(linha, linhas);
			if(filhos.size() > 0){
				this.montaDadosSelectMain(select, linhaFilhos, linhas, prefixo + "  ");
			}
			
		} 
	}

	private List<ProdutoLinhaFB> getChildrens(ProdutoLinhaFB produtoLinha, List<ProdutoLinhaFB> lista){
		List<ProdutoLinhaFB> result = new ArrayList<ProdutoLinhaFB>();
		for(ProdutoLinhaFB rs : lista){
			if(rs.getProdutoLinhaPaiId()!=null){
	    		if(rs.getProdutoLinhaPaiId().equals(produtoLinha.getProdutoLinhaPaiId())){
	    			result.add(rs);
	    		}
			}
		}
		return result;
}
}
