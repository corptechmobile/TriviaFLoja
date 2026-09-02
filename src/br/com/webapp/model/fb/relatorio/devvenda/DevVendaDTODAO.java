package br.com.webapp.model.fb.relatorio.devvenda;

import java.util.Date;
import java.util.List;

import br.com.webapp.model.fb.cliente.ClienteFB;
import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.produto.ProdutoFB;
import br.com.webapp.model.fb.produtolinha.ProdutoLinhaFB;
import br.com.webapp.model.fb.vendedor.VendedorFB;

public interface DevVendaDTODAO {
	public List<DevVendaDTO> listar(String boletim, EmpresaFB empresaFilter, VendedorFB vendedorFilter, ClienteFB clienteFilter, Integer fornecedorFilter, ProdutoFB produtoFilter, ProdutoLinhaFB linhaProdutoFilter, Date dataFilter1, Date dataFilter2, String agruparPor);
	public List<DevVendaDTO> listarBoletim(String boletim, EmpresaFB empresa, VendedorFB vendedor, ClienteFB cliente, ProdutoFB produto, Date dataFilter1, Date dataFilter2);
}
