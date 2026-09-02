package br.com.webapp.model.fb.pedvenda.dto;

import java.util.Date;
import java.util.List;

import br.com.webapp.model.fb.cliente.ClienteFB;
import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.pedvendastatus.PedVendaStatusFB;
import br.com.webapp.model.fb.usuario.UsuarioFB;
import br.com.webapp.model.fb.vendedor.VendedorFB;

public interface PedVendaFBDTODAO {

	public PedVendaFBDTO carregar(Integer pedVendaId);
	public List<PedVendaFBDTO> listar(String tipoDataFilter, String tipoDataFilter2, Date dafaFilter1, Date dataFilter2, EmpresaFB empresaFilter, VendedorFB vendedorFilter, ClienteFB clienteFilter, PedVendaStatusFB statusFilter, Boolean carteiraFilter, Integer tipoPedido, UsuarioFB usuario);
	public List<PedVendaFBDTO> listarBloqueados(String descricaoFilter, VendedorFB vendedorFilter, Date dataFilter1, Date dataFilter2, UsuarioFB usuario);
	public List<PedVendaFBDTO> listarEmRecebimento(String descricaoFilter, VendedorFB vendedorFilter, UsuarioFB usuario);
}
