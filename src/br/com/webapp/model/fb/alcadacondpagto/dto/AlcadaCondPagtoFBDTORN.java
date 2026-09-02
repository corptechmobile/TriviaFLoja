package br.com.webapp.model.fb.alcadacondpagto.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.webapp.model.fb.condpagto.CondPagtoFB;
import br.com.webapp.model.fb.condpagto.CondPagtoFBRN;
import br.com.webapp.model.fb.gestaovenda.GestaoVendaFB;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.DAOFactoryFirebird;
import br.com.webapp.web.util.RNException;

public class AlcadaCondPagtoFBDTORN {

	private AlcadaCondPagtoFBDTODAO alcadaCondPagtoFBDTODAO;
	
	public AlcadaCondPagtoFBDTORN() {
		alcadaCondPagtoFBDTODAO = DAOFactoryFirebird.criarAlcadaCondPagtoFBDTODAO();
	}
	
	public AlcadaCondPagtoFBDTO carregar(GestaoVendaFB gestaoVenda, CondPagtoFB condPagtoIdOuDesc) {
		return this.alcadaCondPagtoFBDTODAO.carregar(gestaoVenda, condPagtoIdOuDesc);
	}
	
	public void insert(AlcadaCondPagtoFBDTO alcadaCondPagtoFBDTO) {
		this.alcadaCondPagtoFBDTODAO.insert(alcadaCondPagtoFBDTO);
	}
	
	public void update(AlcadaCondPagtoFBDTO alcadaCondPagtoFBDTO) throws DAOException {
		this.alcadaCondPagtoFBDTODAO.update(alcadaCondPagtoFBDTO);
	}
	
	public void salvar(List<AlcadaCondPagtoFBDTO> listaMultiAlcadas) throws RNException {
		try {
			for (AlcadaCondPagtoFBDTO rs : listaMultiAlcadas) {
				this.update(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
			alcadaCondPagtoFBDTODAO.rollback();
			throw new RNException(e.getMessage());
		}
	}
	
	public List<AlcadaCondPagtoFBDTO> listar(Integer gestaoId, Integer condPagtoId){
		return this.alcadaCondPagtoFBDTODAO.listar(gestaoId, condPagtoId);
	}

	public List<AlcadaCondPagtoFBDTO> gerarMultiAlcadas(GestaoVendaFB gestaoFilter, Double alcadaMulti) {
		List<AlcadaCondPagtoFBDTO> multiAlcadas = new ArrayList<AlcadaCondPagtoFBDTO>();
		List<CondPagtoFB> listaCondicoes = new CondPagtoFBRN().listar();
		for (CondPagtoFB rs : listaCondicoes) {
			AlcadaCondPagtoFBDTO alcadaDTO = new AlcadaCondPagtoFBDTO();
			alcadaDTO.setCondPagtoId(rs.getId());
			alcadaDTO.setCondPagtoDesc(rs.getDescricao());
			alcadaDTO.setGestaoVendaId(gestaoFilter.getId());
			alcadaDTO.setGestaoVendaDesc(gestaoFilter.getNome());
			alcadaDTO.setAlcada(alcadaMulti);
			multiAlcadas.add(alcadaDTO);
		}
		return multiAlcadas;
	}

	public void delete(AlcadaCondPagtoFBDTO selecionada) throws DAOException {
		this.alcadaCondPagtoFBDTODAO.delete(selecionada);
	}
}
