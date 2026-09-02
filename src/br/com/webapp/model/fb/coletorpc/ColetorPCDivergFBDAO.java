package br.com.webapp.model.fb.coletorpc;
import java.util.List;

import br.com.webapp.web.util.DAOException;


public interface ColetorPCDivergFBDAO {

	public ColetorPCDivergFB carregar(Integer coletorId, int status);
	public ColetorPCDivergFB salvar(ColetorPCDivergFB coletorPCDivergFB) throws DAOException;
	public void excluir();
	public void update(ColetorPCDivergFB divergencia) throws DAOException;
	public List<ColetorPCDivergFB> listar(Integer coletorPCId);
	public List<ColetorPCDivergFB> listar();

}
