package br.com.webapp.model.fb.relatorio.vendaforn.pedvendadiverg;

import java.util.Date;
import java.util.List;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.usuario.UsuarioFB;
import br.com.webapp.model.fb.vendedor.VendedorFB;

public interface PedVendaDivergRelDAO {
	public List<PedVendaDivergRel> listar(VendedorFB vendedorFB, EmpresaFB empresaFB, String produtoFilter, String[] splitDescricao, Integer tipoDataFilter, Date dataFilter1, Date dataFilter2, UsuarioFB usuarioFB, Integer tipoDiverg, Integer situacaoDiverg);
}
