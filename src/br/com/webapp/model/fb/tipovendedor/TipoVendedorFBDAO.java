package br.com.webapp.model.fb.tipovendedor;

import java.util.List;

import br.com.webapp.model.fb.usuario.UsuarioFB;

public interface TipoVendedorFBDAO {
	
	public TipoVendedorFB carregar(Integer tipovendedorId);
	public List<TipoVendedorFB> listar(String descricaoFilter);

}
