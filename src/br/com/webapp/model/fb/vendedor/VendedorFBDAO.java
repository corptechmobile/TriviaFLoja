package br.com.webapp.model.fb.vendedor;

import java.util.List;

import br.com.webapp.model.fb.usuario.UsuarioFB;

public interface VendedorFBDAO {
	
	public VendedorFB carregar(Integer vendedorId);
	public VendedorFB carregar(String cnpjCpf);
	public List<VendedorFB> listar(String descricaoFilter);
	public VendedorFB carregar(UsuarioFB usuarioFB);
	public VendedorFB verificarAssocTipoVendEmp(Integer vendedorId, Integer empresaId);

}
