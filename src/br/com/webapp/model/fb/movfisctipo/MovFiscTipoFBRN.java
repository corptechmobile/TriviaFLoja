package br.com.webapp.model.fb.movfisctipo;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.webapp.model.fb.formapagto.FormaPagtoFB;
import br.com.webapp.web.util.DAOFactoryFirebird;

public class MovFiscTipoFBRN {

	private MovFiscTipoFBDAO movFiscTipoFBDAO;
	
	public MovFiscTipoFBRN() {
		movFiscTipoFBDAO = DAOFactoryFirebird.criarMovFiscTipoFB();
	}
	
	public MovFiscTipoFB carregar(Integer id) {
		return this.movFiscTipoFBDAO.carregar(id);
	}
	
	public MovFiscTipoFB carregarDefault() {
		return this.movFiscTipoFBDAO.carregarDefault();
	}
	
	public List<MovFiscTipoFB> listar(){
		return this.movFiscTipoFBDAO.listar();
	}

	public List<MovFiscTipoFB> listarTransfOutras() {
		return this.movFiscTipoFBDAO.listarTransfOutras();
	}
	
	public List<SelectItem> montaDadosSelect(List<MovFiscTipoFB> movFiscTipos, String string) {
		
		List<SelectItem> select = new ArrayList<SelectItem>();
		SelectItem item = null;
		if (movFiscTipos != null) {
			for (MovFiscTipoFB rs : movFiscTipos) {
				item = new SelectItem(rs, rs.getDescricao());
				item.setEscape(false);
				select.add(item);
			}
		}
		return select;
	}

	public List<MovFiscTipoFB> listarTransfOutras(Integer movFiscTipoId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<MovFiscTipoFB> listarPedVenda() {
		return this.movFiscTipoFBDAO.listarPedVenda();
	}	
}
