package br.com.webapp.model.fb.pais;

import java.util.List;

public interface PaisFBDAO {
	public PaisFB carregar(String id);
	public List<PaisFB> listar();
}
