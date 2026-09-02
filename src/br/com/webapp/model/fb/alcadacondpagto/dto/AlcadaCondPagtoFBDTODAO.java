package br.com.webapp.model.fb.alcadacondpagto.dto;

import java.util.List;

import br.com.webapp.model.fb.condpagto.CondPagtoFB;
import br.com.webapp.model.fb.gestaovenda.GestaoVendaFB;
import br.com.webapp.web.util.DAOException;

public interface AlcadaCondPagtoFBDTODAO {

	public AlcadaCondPagtoFBDTO carregar(GestaoVendaFB gestaoVenda, CondPagtoFB condPagtoIdOuDesc);
	public List<AlcadaCondPagtoFBDTO> listar(Integer gestaoVendaId, Integer condPagtoIdOuDesc);
	public void insert(AlcadaCondPagtoFBDTO alcadaCondPagtoFBDTO);
	public void update(AlcadaCondPagtoFBDTO alcadaCondPagtoFBDTO) throws DAOException;
	public void delete(AlcadaCondPagtoFBDTO alcadaCondPagtoFBDTO) throws DAOException;
	public void rollback();
}
