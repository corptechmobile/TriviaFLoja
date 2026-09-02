package br.com.webapp.model.usuariogrupo;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.webapp.model.usuario.UsuarioRN;
import br.com.webapp.web.util.DAOFactoryPostGres;
import br.com.webapp.web.util.RNException;

public class UsuarioGrupoRN {
	
	private UsuarioGrupoDAO usuarioGrupoDAO;
	
	public UsuarioGrupoRN(){
		this.usuarioGrupoDAO = DAOFactoryPostGres.criarUsuarioGrupoDAO();
	}
	public UsuarioGrupo salvar(UsuarioGrupo usuarioGrupo){
		return this.usuarioGrupoDAO.salvar(usuarioGrupo);
	}
	public void excluir(UsuarioGrupo usuarioGrupo) throws RNException{
		try {
			UsuarioRN usuarioRN = new UsuarioRN();
			usuarioRN.excluir(usuarioGrupo);
			this.usuarioGrupoDAO.excluir(usuarioGrupo);
		}catch (Exception e) {
			usuarioGrupoDAO.rollback();
			e.printStackTrace();
			throw new RNException(e.getMessage());
		}
	}
	public UsuarioGrupo carregar(Integer usuarioGrupo){
		return this.usuarioGrupoDAO.carregar(usuarioGrupo);
	}
	public List<UsuarioGrupo> listar(){
		return this.usuarioGrupoDAO.listar();
	}
	public List<UsuarioGrupo> listar(String descricao){
		return this.usuarioGrupoDAO.listar(descricao);
	}
	
	public List<SelectItem> montaDadosSelect(List<UsuarioGrupo> usuarioGrupos, String prefixo) {
		List<SelectItem> select = new ArrayList<SelectItem>();
		SelectItem item = null;
		if (usuarioGrupos != null) {
			for (UsuarioGrupo usuarioGrupo : usuarioGrupos) {
				item = new SelectItem(usuarioGrupo, usuarioGrupo.getDescricao());
				item.setEscape(false);
				select.add(item);
			}
		}
		return select;
	}

}
