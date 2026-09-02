package br.com.webapp.model.fb.coletor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.usuario.UsuarioFB;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.DAOFactoryFirebird;
import br.com.webapp.web.util.Funcoes;
import br.com.webapp.web.util.RNException;
import br.com.webapp.web.util.UtilMessage;

public class ColetorInvFBRN {

	private ColetorInvFBDAO coletorInvFBDAO;
	
	public ColetorInvFBRN() {
		this.coletorInvFBDAO = DAOFactoryFirebird.criarColetorInvFB();
	}
	
	public ColetorInvFB carregar(Integer id) {
		return this.coletorInvFBDAO.carregar(id);
	}
	
	public List<ColetorInvFB> listar(){
		return this.coletorInvFBDAO.listar();
	}
	
	public List<SelectItem> montaDadosSelect(List<ColetorInvFB> coletoresInv, String string) {
		List<SelectItem> select = new ArrayList<SelectItem>();
		SelectItem item = null;
		if (coletoresInv != null) {
			for (ColetorInvFB rs : coletoresInv) {
				item = new SelectItem(rs, rs.getDescricao());
				item.setEscape(false);
				select.add(item);
			}
		}
		
		return select;
	}

	public ColetorInvFB salvarNovo(ColetorInvFB inventario, boolean editando) throws DAOException, RNException {	
		if(!editando) {
			ColetorInvFB inventarioOld = new ColetorInvFBRN().verificarInvAbertoEmpresa(inventario.getEmpresaId());
			if(inventarioOld!=null) {
				throw new RNException(UtilMessage.mensagem("msg.erro.inventario.aberto.empresa"));
			}
		}	
		
		return salvar(inventario);
	}
	private ColetorInvFB verificarInvAbertoEmpresa(Integer empresaId) {
		return this.coletorInvFBDAO.verificarInvAbertoEmpresa(empresaId);
	}

	private ColetorInvFB salvar(ColetorInvFB inventario) throws DAOException {
		
		if(inventario.getDtCriacao()==null){
			inventario.setDtCriacao(new Date());
		}
		
		if(inventario.getId()==null) {
			inventario = new ColetorInvFBRN().carregar(this.coletorInvFBDAO.insert(inventario));
		}else {
			this.coletorInvFBDAO.update(inventario);
			inventario = new ColetorInvFBRN().carregar(inventario.getId());
		}
		
		return inventario;
		
	}

	public ColetorInvFB novo(UsuarioFB usuarioLogado) {
		ColetorInvFB inventario = new ColetorInvFB();
		inventario.setStatus(ColetorInvFB.STATUS_ABERTO);
		inventario.setUsuarioId(usuarioLogado.getId());
		return inventario;
	}

	public ColetorInvFB editar(Integer inventarioId) {
		ColetorInvFB inventario = this.carregar(inventarioId);
		return inventario;
	}

	public void finalizar(ColetorInvFB selecionada) throws RNException {
		try {
			finalizarInventario(selecionada.getId());
			atualizarEmbFechVenda(selecionada.getId());
			Integer inventarioId = criarInventario(selecionada);

			incluirItensInventario(inventarioId, selecionada.getId());
			inserirPosicaoEstoque(inventarioId, selecionada.getId());
			atualizarProdutoLocalidade(inventarioId);
			inserirContagens(inventarioId);
			
		}catch (Exception e) {
			
			e.printStackTrace();
			rollBack();
			if(e instanceof RNException) {
				throw new RNException(e.getMessage());
			}else {
				throw new RNException(UtilMessage.mensagem("msg.erro.finalizar.inventario"));
			}
		
		}			
	}
	
	
	public String encrypt(String arquivo) {   
	  	   String sign = new Date().getTime() + arquivo; 
	  	  
	  	   try {   
	  	      java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");   
	  	      md.update(sign.getBytes());   
	  	      byte[] hash = md.digest();   
	  	      StringBuffer hexString = new StringBuffer();   
	  	      for (int i = 0; i < hash.length; i++) {   
	  	         if ((0xff & hash[i]) < 0x10)   
	  	            hexString.append("0" + Integer.toHexString((0xFF & hash[i])));   
	  	         else   
	  	            hexString.append(Integer.toHexString(0xFF & hash[i]));   
	  	      }   
	  	      sign = hexString.toString();   
	  	   }catch (Exception nsae) {   
	  	      nsae.printStackTrace();   
	  	   }   
	  	   return sign;   
	}

	private void atualizarEmbFechVenda(Integer coletorInvId) throws DAOException {
		this.coletorInvFBDAO.atualizarEmbFechVenda(coletorInvId);
	}

	private void rollBack() {
		this.coletorInvFBDAO.rollBack();
		
	}

	private void finalizarInventario(Integer coletorInvId) throws DAOException {
		this.coletorInvFBDAO.finalizarInventario(coletorInvId);
	}

	private void inserirContagens(Integer inventarioId) throws DAOException {
		this.coletorInvFBDAO.inserirContagens(inventarioId);
	}

	private void atualizarProdutoLocalidade(Integer inventarioId) throws DAOException {
		this.coletorInvFBDAO.atualizarProdutoLocalidade(inventarioId);
		
	}

	private void inserirPosicaoEstoque(Integer inventarioId, Integer coletorInvId) throws DAOException {
		this.coletorInvFBDAO.inserirPosicaoEstoque(inventarioId, coletorInvId);
		
	}

	private void incluirItensInventario(Integer inventarioId, Integer coletorInvId) throws DAOException {
		this.coletorInvFBDAO.incluirItensInventario(inventarioId, coletorInvId);
		
	}

	private Integer criarInventario(ColetorInvFB coletorInvFB) throws DAOException {
		return this.coletorInvFBDAO.criarInventario(coletorInvFB);
	}


	public void excluir(Integer inventarioId) throws DAOException {
		this.coletorInvFBDAO.excluir(inventarioId);
	}

	public List<ColetorInvFB> listar(EmpresaFB empresaFilter, Date data1Filter, Date data2Filter, boolean concluidoFilter) {
		data1Filter = Funcoes.dataFilter1(data1Filter);
		data2Filter = Funcoes.dataFilter2(data2Filter);

		return this.coletorInvFBDAO.listar(empresaFilter, data1Filter, data2Filter, concluidoFilter);
	}


}
