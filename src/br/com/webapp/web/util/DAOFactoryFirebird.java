package br.com.webapp.web.util;

import org.hibernate.Session;

import br.com.webapp.model.fb.alcadacondpagto.AlcadaCondPagtoFBDAO;
import br.com.webapp.model.fb.alcadacondpagto.AlcadaCondPagtoFBDAOHibernate;
import br.com.webapp.model.fb.alcadacondpagto.dto.AlcadaCondPagtoFBDTODAO;
import br.com.webapp.model.fb.alcadacondpagto.dto.AlcadaCondPagtoFBDTODAOHibernate;
import br.com.webapp.model.fb.cliente.ClienteFBDAO;
import br.com.webapp.model.fb.cliente.ClienteFBDAOHibernate;
import br.com.webapp.model.fb.cobrtipo.CobrTipoFBDAO;
import br.com.webapp.model.fb.cobrtipo.CobrTipoFBDAOHibernate;
import br.com.webapp.model.fb.coletor.ColetorInvFBDAO;
import br.com.webapp.model.fb.coletor.ColetorInvFBDAOHibernate;
import br.com.webapp.model.fb.coletorcontagem.ColetorInvContagemFBDAO;
import br.com.webapp.model.fb.coletorcontagem.ColetorInvContagemFBDAOHibernate;
import br.com.webapp.model.fb.coletorpc.ColetorDivergenciaFBDAO;
import br.com.webapp.model.fb.coletorpc.ColetorDivergenciaFBDAOHibernate;
import br.com.webapp.model.fb.coletorpc.ColetorPCDivergFBDAO;
import br.com.webapp.model.fb.coletorpc.ColetorPCDivergFBDAOHibernate;
import br.com.webapp.model.fb.coletorpc.ColetorDivergenciaFBDAO;
import br.com.webapp.model.fb.coletorpc.ColetorDivergenciaFBDAOHibernate;
import br.com.webapp.model.fb.coletorpc.ColetorPCFBDAO;
import br.com.webapp.model.fb.coletorpc.ColetorPCFBDAOHibernate;
import br.com.webapp.model.fb.coletorpc.ColetorPCItemFBDAO;
import br.com.webapp.model.fb.coletorpc.ColetorPCItemFBDAOHibernate;
import br.com.webapp.model.fb.coletorpc.contagem.ColetorPCFBContagemDAO;
import br.com.webapp.model.fb.coletorpc.contagem.ColetorPCFBContagemDAOHibernate;
import br.com.webapp.model.fb.coletorpc.nfcompra.ColetorPCNFCompraFBDAO;
import br.com.webapp.model.fb.coletorpc.nfcompra.ColetorPCNFCompraFBDAOHibernate;
import br.com.webapp.model.fb.comissaofaixadesc.ComissaoFaixaDescFBDAO;
import br.com.webapp.model.fb.comissaofaixadesc.ComissaoFaixaDescFBDAOHibernate;
import br.com.webapp.model.fb.condpagto.CondPagtoFBDAO;
import br.com.webapp.model.fb.condpagto.CondPagtoFBDAOHibernate;
import br.com.webapp.model.fb.conferente.ConferenteFBDAO;
import br.com.webapp.model.fb.conferente.ConferenteFBDAOHibernate;
import br.com.webapp.model.fb.diasuteis.DiasUteisFBDAO;
import br.com.webapp.model.fb.diasuteis.DiasUteisFBDAOHibernate;
import br.com.webapp.model.fb.empresa.EmpresaFBDAO;
import br.com.webapp.model.fb.empresa.EmpresaFBDAOHibernate;
import br.com.webapp.model.fb.enderecotipo.EnderecoTipoFBDAO;
import br.com.webapp.model.fb.enderecotipo.EnderecoTipoFBDAOHibernate;
import br.com.webapp.model.fb.estado.EstadoFBDAO;
import br.com.webapp.model.fb.estado.EstadoFBDAOHibernate;
import br.com.webapp.model.fb.eventofinanceiro.EventoFinanceiroFBDAO;
import br.com.webapp.model.fb.eventofinanceiro.EventoFinanceiroFBDAOHibernate;
import br.com.webapp.model.fb.formapagto.FormaPagtoFBDAO;
import br.com.webapp.model.fb.formapagto.FormaPagtoFBDAOHibernate;
import br.com.webapp.model.fb.fornecedor.FornecedorFBDAO;
import br.com.webapp.model.fb.fornecedor.FornecedorFBDAOHibernate;
import br.com.webapp.model.fb.fretetipo.FreteTipoFBDAO;
import br.com.webapp.model.fb.fretetipo.FreteTipoFBDAOHibernate;
import br.com.webapp.model.fb.gestaovenda.GestaoVendaFBDAO;
import br.com.webapp.model.fb.gestaovenda.GestaoVendaFBDAOHibernate;
import br.com.webapp.model.fb.gestaovendamob.GestaoVendaMobFBDAO;
import br.com.webapp.model.fb.gestaovendamob.GestaoVendaMobFBDAOHibernate;
import br.com.webapp.model.fb.grupofinanceiro.GrupoFinanceiroFBDAO;
import br.com.webapp.model.fb.grupofinanceiro.GrupoFinanceiroFBDAOHibernate;
import br.com.webapp.model.fb.infogerproduto.InfoGerProdutoFBDAO;
import br.com.webapp.model.fb.infogerproduto.InfoGerProdutoFBDAOHibernate;
import br.com.webapp.model.fb.linhaprodutometa.LinhaProdutoMetaFBDAO;
import br.com.webapp.model.fb.linhaprodutometa.LinhaProdutoMetaFBDAOHibernate;
import br.com.webapp.model.fb.movfisctipo.MovFiscTipoFBDAO;
import br.com.webapp.model.fb.movfisctipo.MovFiscTipoFBDAOHibernate;
import br.com.webapp.model.fb.municipio.MunicipioFBDAO;
import br.com.webapp.model.fb.municipio.MunicipioFBDAOHibernate;
import br.com.webapp.model.fb.nfcompra.NFCompraFBDAO;
import br.com.webapp.model.fb.nfcompra.NFCompraFBDAOHibernate;
import br.com.webapp.model.fb.nfcompra.NFCompraFBRN;
import br.com.webapp.model.fb.nfcompra.NFCompraItemFBDAO;
import br.com.webapp.model.fb.nfcompra.NFCompraItemFBDAOHibernate;
import br.com.webapp.model.fb.orcamentogrupo.OrcamentoGrupoFBDAO;
import br.com.webapp.model.fb.orcamentogrupo.OrcamentoGrupoFBDAOHibernate;
import br.com.webapp.model.fb.orcamentometa.OrcamentoMetaFBDAO;
import br.com.webapp.model.fb.orcamentometa.OrcamentoMetaFBDAOHibernate;
import br.com.webapp.model.fb.orcamentometaitem.OrcamentoMetaItemFBDAO;
import br.com.webapp.model.fb.orcamentometaitem.OrcamentoMetaItemFBDAOHibernate;
import br.com.webapp.model.fb.pais.PaisFBDAO;
import br.com.webapp.model.fb.pais.PaisFBDAOHibernate;
import br.com.webapp.model.fb.parametro.ParametroFBDAO;
import br.com.webapp.model.fb.parametro.ParametroFBDAOHibernate;
import br.com.webapp.model.fb.pedvenda.PedVendaFBDAO;
import br.com.webapp.model.fb.pedvenda.PedVendaFBDAOHibernate;
import br.com.webapp.model.fb.pedvenda.PedVendaItemFBDAO;
import br.com.webapp.model.fb.pedvenda.PedVendaItemFBDAOHibernate;
import br.com.webapp.model.fb.pedvenda.cartao.PedVendaCartaoFBDAO;
import br.com.webapp.model.fb.pedvenda.cartao.PedVendaCartaoFBDAOHibernate;
import br.com.webapp.model.fb.pedvenda.diverg.PedVendaDivergFBDAO;
import br.com.webapp.model.fb.pedvenda.diverg.PedVendaDivergFBDAOHibernate;
import br.com.webapp.model.fb.pedvenda.dto.PedVendaFBDTODAO;
import br.com.webapp.model.fb.pedvenda.dto.PedVendaFBDTODAOHibernate;
import br.com.webapp.model.fb.pedvendacomposto.PedVendaCompostoFBDAO;
import br.com.webapp.model.fb.pedvendacomposto.PedVendaCompostoFBDAOHibernate;
import br.com.webapp.model.fb.pedvendaitem.dto.PedVendaItemFBDTODAO;
import br.com.webapp.model.fb.pedvendaitem.dto.PedVendaItemFBDTODAOHibernate;
import br.com.webapp.model.fb.pedvendaitemprodlote.dto.PedVendaItemProdLoteDTODAO;
import br.com.webapp.model.fb.pedvendaitemprodlote.dto.PedVendaItemProdLoteDTODAOHibernate;
import br.com.webapp.model.fb.pedvendastatus.PedVendaStatusFBDAOHibernate;
import br.com.webapp.model.fb.pedvendastatus.PedvendaStatusFBDAO;
import br.com.webapp.model.fb.planilhacegafirebird.PlanilhaCegaFirebirdDAO;
import br.com.webapp.model.fb.planilhacegafirebird.PlanilhaCegaFirebirdDAOHibernate;
import br.com.webapp.model.fb.planilhacegafirebird.PlanilhaCegaItemFirebirdDAO;
import br.com.webapp.model.fb.planilhacegafirebird.PlanilhaCegaItemFirebirdDAOHibernate;
import br.com.webapp.model.fb.prodcomposto.ProdCompostoFBDAO;
import br.com.webapp.model.fb.prodcomposto.ProdCompostoFBDAOHibernate;
import br.com.webapp.model.fb.prodcomposto.ProdCompostoItemFBDAO;
import br.com.webapp.model.fb.prodcomposto.ProdCompostoItemFBDAOHibernate;
import br.com.webapp.model.fb.produto.ProdutoEstoqueFBDAO;
import br.com.webapp.model.fb.produto.ProdutoEstoqueFBDAOHibernate;
import br.com.webapp.model.fb.produto.ProdutoEstoqueLoteFBDAO;
import br.com.webapp.model.fb.produto.ProdutoEstoqueLoteFBDAOHibernate;
import br.com.webapp.model.fb.produto.ProdutoFBDAO;
import br.com.webapp.model.fb.produto.ProdutoFBDAOHibernate;
import br.com.webapp.model.fb.produtocb.ProdutoCBFBDAO;
import br.com.webapp.model.fb.produtocb.ProdutoCBFBDAOHibernate;
import br.com.webapp.model.fb.produtolinha.ProdutoLinhaFBDAO;
import br.com.webapp.model.fb.produtolinha.ProdutoLinhaFBDAOHibernate;
import br.com.webapp.model.fb.relatorio.devvenda.DevVendaDTODAO;
import br.com.webapp.model.fb.relatorio.devvenda.DevVendaDTODAOHibernate;
import br.com.webapp.model.fb.relatorio.vendaforn.VendaFornDTODAO;
import br.com.webapp.model.fb.relatorio.vendaforn.VendaFornDTODAOHibernate;
import br.com.webapp.model.fb.relatorio.vendaforn.ecfvendas.ECFVendasFBDAO;
import br.com.webapp.model.fb.relatorio.vendaforn.ecfvendas.ECFVendasFBDAOHibernate;
import br.com.webapp.model.fb.relatorio.vendaforn.ecfvendas.periodo.ECFVendasPeriodoDAO;
import br.com.webapp.model.fb.relatorio.vendaforn.ecfvendas.periodo.ECFVendasPeriodoDAOHibernate;
import br.com.webapp.model.fb.relatorio.vendaforn.formapagto.VendaFornFPagtoDTODAO;
import br.com.webapp.model.fb.relatorio.vendaforn.formapagto.VendaFornFPagtoDTODAOHibernate;
import br.com.webapp.model.fb.relatorio.vendaforn.pedvenda.VendaFornPedVendaDAO;
import br.com.webapp.model.fb.relatorio.vendaforn.pedvenda.VendaFornPedVendaDAOHibernate;
import br.com.webapp.model.fb.relatorio.vendaforn.pedvendadiverg.PedVendaDivergRelDAO;
import br.com.webapp.model.fb.relatorio.vendaforn.pedvendadiverg.PedVendaDivergRelDAOHibernate;
import br.com.webapp.model.fb.relatorio.vendaforn.resumo.VendaFornResumoDAO;
import br.com.webapp.model.fb.relatorio.vendaforn.resumo.VendaFornResumoDAOHibernate;
import br.com.webapp.model.fb.reserva.ReservaFBDAO;
import br.com.webapp.model.fb.reserva.ReservaFBDAOHibernate;
import br.com.webapp.model.fb.reservafila.ReservaFilaFBDAO;
import br.com.webapp.model.fb.reservafila.ReservaFilaFBDAOHibernate;
import br.com.webapp.model.fb.reservalote.ReservaLoteFBDAO;
import br.com.webapp.model.fb.reservalote.ReservaLoteFBDAOHibernate;
import br.com.webapp.model.fb.romaneio.RomaneioContagemFBDAO;
import br.com.webapp.model.fb.romaneio.RomaneioContagemFBDAOHibernate;
import br.com.webapp.model.fb.romaneio.RomaneioFBDAO;
import br.com.webapp.model.fb.romaneio.RomaneioFBDAOHibernate;
import br.com.webapp.model.fb.romaneio.RomaneioItemFBDAO;
import br.com.webapp.model.fb.romaneio.RomaneioItemFBDAOHibernate;
import br.com.webapp.model.fb.romaneio.RomaneioItemPedidoFBDAO;
import br.com.webapp.model.fb.romaneio.RomaneioItemPedidoFBDAOHibernate;
import br.com.webapp.model.fb.tabpreco.TabPrecoFBDAO;
import br.com.webapp.model.fb.tabpreco.TabPrecoFBDAOHibernate;
import br.com.webapp.model.fb.telefonetipo.TelefoneTipoFBDAO;
import br.com.webapp.model.fb.telefonetipo.TelefoneTipoFBDAOHibernate;
import br.com.webapp.model.fb.tipovendedor.TipoVendedorFBDAO;
import br.com.webapp.model.fb.tipovendedor.TipoVendedorFBDAOHibernate;
import br.com.webapp.model.fb.usuario.UsuarioFBDAO;
import br.com.webapp.model.fb.usuario.UsuarioFBDAOHibernate;
import br.com.webapp.model.fb.usuariocoletordiverg.UsuarioColetorDivergFBDAO;
import br.com.webapp.model.fb.usuariocoletordiverg.UsuarioColetorDivergFBDAOHibernate;
import br.com.webapp.model.fb.vendasproduto.dto.VendasProdutoDTODAO;
import br.com.webapp.model.fb.vendasproduto.dto.VendasProdutoDTODAOHibernate;
import br.com.webapp.model.fb.vendedor.VendedorFBDAO;
import br.com.webapp.model.fb.vendedor.VendedorFBDAOHibernate;

public class DAOFactoryFirebird {
	
	public static UsuarioFBDAO criarUsuarioFBDAO() {
		UsuarioFBDAOHibernate usuarioFBDAO = new UsuarioFBDAOHibernate();
		usuarioFBDAO.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return usuarioFBDAO;
	}
	
	public static EmpresaFBDAO criarEmpresaFBDAO() {
		EmpresaFBDAOHibernate empresaFBDAO = new EmpresaFBDAOHibernate();
		empresaFBDAO.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return empresaFBDAO;
	}

	public static VendedorFBDAO criarVendedorFBDAO() {
		VendedorFBDAOHibernate vendedorFBDAO = new VendedorFBDAOHibernate();
		vendedorFBDAO.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return vendedorFBDAO;
	}

	public static ClienteFBDAO criarClienteFBDAO() {
		ClienteFBDAOHibernate clienteFBDAO = new ClienteFBDAOHibernate();
		clienteFBDAO.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return clienteFBDAO;
	}

	public static PedVendaFBDAO criarPedVendaFBDAO() {
		PedVendaFBDAOHibernate pedVendaFBDAO = new PedVendaFBDAOHibernate();
		pedVendaFBDAO.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return pedVendaFBDAO;
	}

	public static PaisFBDAO criarPaisFBDAO() {
		PaisFBDAOHibernate paisFBDAOHibernate = new PaisFBDAOHibernate();
		paisFBDAOHibernate.SetSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return paisFBDAOHibernate;
	}

	public static EstadoFBDAO criarEstadoFB() {
		EstadoFBDAOHibernate estadoFBDAOHibernate = new EstadoFBDAOHibernate();
		estadoFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return estadoFBDAOHibernate;
	}

	public static MunicipioFBDAO criarMunicipioFB() {
		MunicipioFBDAOHibernate municipioFBDAOHibernate = new MunicipioFBDAOHibernate();
		municipioFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return municipioFBDAOHibernate;
	}

	public static TelefoneTipoFBDAO criarTelefoneTipoDAO() {
		TelefoneTipoFBDAOHibernate telefoneTipoFBDAOHibernate = new TelefoneTipoFBDAOHibernate();
		telefoneTipoFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return telefoneTipoFBDAOHibernate;
	}

	public static EnderecoTipoFBDAO criarEnderecoFBDAO() {
		EnderecoTipoFBDAOHibernate enderecoTipoFBDAOHibernate = new EnderecoTipoFBDAOHibernate();
		enderecoTipoFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return enderecoTipoFBDAOHibernate;
	}

	public static FreteTipoFBDAO criarFreteTipoDAO() {
		FreteTipoFBDAOHibernate freteTipoFBDAOHibernate = new FreteTipoFBDAOHibernate();
		freteTipoFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return freteTipoFBDAOHibernate;
	}

	public static CondPagtoFBDAO criarCondPagtoFB() {
		CondPagtoFBDAOHibernate condPagtoFBDAOHibernate = new CondPagtoFBDAOHibernate();
		condPagtoFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return condPagtoFBDAOHibernate;
	}

	public static FormaPagtoFBDAO criarFormaPagtoFBDAO() {
		FormaPagtoFBDAOHibernate formaPagtoFBDAOHibernate = new FormaPagtoFBDAOHibernate();
		formaPagtoFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return formaPagtoFBDAOHibernate;
	}

	public static MovFiscTipoFBDAO criarMovFiscTipoFB() {
		MovFiscTipoFBDAOHibernate movFiscTipoFBDAOHibernate = new MovFiscTipoFBDAOHibernate();
		movFiscTipoFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return movFiscTipoFBDAOHibernate;
	}

	public static ProdutoFBDAO criarProdutoFBDAO() {
		ProdutoFBDAOHibernate podutoFBDAO = new ProdutoFBDAOHibernate();
		podutoFBDAO.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return podutoFBDAO;
	}

	public static CobrTipoFBDAO criarCobrTipoFB() {
		CobrTipoFBDAOHibernate cobrTipoFBDAOHibernate = new CobrTipoFBDAOHibernate();
		cobrTipoFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return cobrTipoFBDAOHibernate;
	}

	public static TabPrecoFBDAO criarTabPrecoFBDAO() {
		TabPrecoFBDAOHibernate tabPrecoFBDAOHibernate = new TabPrecoFBDAOHibernate();
		tabPrecoFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return tabPrecoFBDAOHibernate;
	}

	public static PedVendaItemFBDAO criarPedVendaItemFBDAO() {
		PedVendaItemFBDAOHibernate pedVendaItemDAOHibernate = new PedVendaItemFBDAOHibernate();
		pedVendaItemDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return pedVendaItemDAOHibernate;
	}

	public static PedvendaStatusFBDAO criarPedVendaStatusFBDAO() {
		PedVendaStatusFBDAOHibernate pedVendaStatusFBDAOHibernate = new PedVendaStatusFBDAOHibernate();
		pedVendaStatusFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return pedVendaStatusFBDAOHibernate;
	}

	public static ProdutoLinhaFBDAO criarProdutoLinhaFBDAO() {
		ProdutoLinhaFBDAOHibernate produtoLinhaFBDAOHibernate = new ProdutoLinhaFBDAOHibernate();
		produtoLinhaFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return produtoLinhaFBDAOHibernate;
	}

	public static PedVendaFBDTODAO criarPedVendaFBDTODAO() {
		PedVendaFBDTODAOHibernate pedVendaFBDTODAOHibernate = new PedVendaFBDTODAOHibernate();
		pedVendaFBDTODAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return pedVendaFBDTODAOHibernate;
	}

	public static ReservaFBDAO criarReservaFBDAO() {
		ReservaFBDAOHibernate reservaFBDAOHibernate = new ReservaFBDAOHibernate();
		reservaFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return reservaFBDAOHibernate;
	}

	public static ReservaLoteFBDAO criarReservaLoteFBDAO() {
		ReservaLoteFBDAOHibernate reservaLoteFBDAOHibernate = new ReservaLoteFBDAOHibernate();
		reservaLoteFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return reservaLoteFBDAOHibernate;
	}

	public static ProdutoEstoqueFBDAO criarProdutoEstoqueFBDAO() {
		ProdutoEstoqueFBDAOHibernate produtoEstoqueFBDAOHibernate = new ProdutoEstoqueFBDAOHibernate();
		produtoEstoqueFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return produtoEstoqueFBDAOHibernate;
	}

	public static PedVendaItemFBDTODAO criarPedVendaItemFBDTODAO() {
		PedVendaItemFBDTODAOHibernate pedVendaItemFBDTODAO = new PedVendaItemFBDTODAOHibernate();
		pedVendaItemFBDTODAO.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return pedVendaItemFBDTODAO;
	}

	public static AlcadaCondPagtoFBDAO criarAlcadaCondPagtoFBDAO() {
		AlcadaCondPagtoFBDAOHibernate alcadaCondPagtoFBDAO = new AlcadaCondPagtoFBDAOHibernate();
		alcadaCondPagtoFBDAO.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return alcadaCondPagtoFBDAO;
	}
	
	public static AlcadaCondPagtoFBDTODAO criarAlcadaCondPagtoFBDTODAO() {
		AlcadaCondPagtoFBDTODAOHibernate alcadaCondPagtoFBDTODAOHibernate = new AlcadaCondPagtoFBDTODAOHibernate();
		alcadaCondPagtoFBDTODAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return alcadaCondPagtoFBDTODAOHibernate;
	}

	public static PedVendaDivergFBDAO criarPedVendaDivergFBDAO() {
		PedVendaDivergFBDAOHibernate pedVendaDivergFBDAO = new PedVendaDivergFBDAOHibernate();
		pedVendaDivergFBDAO.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return pedVendaDivergFBDAO;
	}

	public static ReservaFilaFBDAO criarReservaFilaFBDAO() {
		ReservaFilaFBDAOHibernate reservaFilaFBDAO = new ReservaFilaFBDAOHibernate();
		reservaFilaFBDAO.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return reservaFilaFBDAO;
	}

	public static GestaoVendaFBDAO criarGestaoVendaFBDAO() {
		GestaoVendaFBDAOHibernate gestaoVendaFBDAOHibernate = new GestaoVendaFBDAOHibernate();
		gestaoVendaFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return gestaoVendaFBDAOHibernate;
	}

	public static ProdCompostoFBDAO criarProdCompostoFBDAO() {
		ProdCompostoFBDAOHibernate prodCompostoFBDAO = new ProdCompostoFBDAOHibernate();
		prodCompostoFBDAO.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return prodCompostoFBDAO;
	}

	public static ProdCompostoItemFBDAO criarProdCompostoItemFBDAO() {
		ProdCompostoItemFBDAOHibernate prodCompostoItemFBDAO = new ProdCompostoItemFBDAOHibernate();
		prodCompostoItemFBDAO.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return prodCompostoItemFBDAO;
	}
	
	public static PedVendaCompostoFBDAO criarPedVendaCompostoFBDAO() {
		PedVendaCompostoFBDAOHibernate pedVendaCompostoFBDAO = new PedVendaCompostoFBDAOHibernate();
		pedVendaCompostoFBDAO.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return pedVendaCompostoFBDAO;
	}

	public static VendaFornDTODAO criarVendaFornDTO() {
		VendaFornDTODAOHibernate vendaFornDTODAOHibernate = new VendaFornDTODAOHibernate();
		vendaFornDTODAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return vendaFornDTODAOHibernate;
	}

	public static VendasProdutoDTODAO criarVendasProdutoDTO() {
		VendasProdutoDTODAOHibernate vendasProdutoDTODAOHibernate = new VendasProdutoDTODAOHibernate();
		vendasProdutoDTODAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return vendasProdutoDTODAOHibernate;
	}

	public static VendaFornResumoDAO criarVendaFornRerumoDAO() {
		VendaFornResumoDAOHibernate vendaFornResumoDAOHibernate = new VendaFornResumoDAOHibernate();
		vendaFornResumoDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return vendaFornResumoDAOHibernate;
	}

	public static VendaFornFPagtoDTODAO criarVendaFornFPagtoDTODAO() {
		VendaFornFPagtoDTODAOHibernate vendaFornFPagtoDTODAOHibernate = new VendaFornFPagtoDTODAOHibernate();
		vendaFornFPagtoDTODAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return vendaFornFPagtoDTODAOHibernate;
	}

	public static VendaFornPedVendaDAO criarVendaFornPedVenda() {
		VendaFornPedVendaDAOHibernate vendaFornPedVendaDAOHibernate = new VendaFornPedVendaDAOHibernate();
		vendaFornPedVendaDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return vendaFornPedVendaDAOHibernate;
	}

	public static PedVendaDivergRelDAO criarPedVendaDivergRelDAO() {
		PedVendaDivergRelDAOHibernate pedVendaDivergRelDAOHibernate = new PedVendaDivergRelDAOHibernate();
		pedVendaDivergRelDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return pedVendaDivergRelDAOHibernate;
	}

	public static DiasUteisFBDAO criarDiasUteisFBDAO() {
		DiasUteisFBDAOHibernate diasUteisFBDAOHibernate = new DiasUteisFBDAOHibernate();
		diasUteisFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return diasUteisFBDAOHibernate;
	}

	public static PedVendaCartaoFBDAO criarPedVendaCartaoFBDAO() {
		PedVendaCartaoFBDAOHibernate pedVendaCartaoFBDAOHibernate = new PedVendaCartaoFBDAOHibernate();
		pedVendaCartaoFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return pedVendaCartaoFBDAOHibernate;
	}

	public static ECFVendasFBDAO criarECFVendasFBDAO() {
		ECFVendasFBDAOHibernate ecfVendasFBDAOHibernate = new ECFVendasFBDAOHibernate();
		ecfVendasFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return ecfVendasFBDAOHibernate;
	}

	public static PedVendaItemProdLoteDTODAO criarPedVendaItemProdLoteDTODAO() {
		PedVendaItemProdLoteDTODAOHibernate pedVendaItemProdLoteDTODAO = new PedVendaItemProdLoteDTODAOHibernate();
		pedVendaItemProdLoteDTODAO.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return pedVendaItemProdLoteDTODAO;
	}

	public static ECFVendasPeriodoDAO criarECFVendasPeriodoDAO() {
		ECFVendasPeriodoDAOHibernate ecfVendasPeriodoDAOHibernate = new ECFVendasPeriodoDAOHibernate();
		ecfVendasPeriodoDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return ecfVendasPeriodoDAOHibernate;
	}

	public static ComissaoFaixaDescFBDAO criarComissaoFaixaDescFBDAO() {
		ComissaoFaixaDescFBDAOHibernate comissaoFaixaDescFBDAOHibernate = new ComissaoFaixaDescFBDAOHibernate();
		comissaoFaixaDescFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return comissaoFaixaDescFBDAOHibernate;
	}

	public static OrcamentoMetaFBDAO criarMetaGastoFinanceiroFBDAO() {
		OrcamentoMetaFBDAOHibernate metaGastoFinanceiroFBDAOHibernate = new OrcamentoMetaFBDAOHibernate();
		metaGastoFinanceiroFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return metaGastoFinanceiroFBDAOHibernate;
	}

	public static EventoFinanceiroFBDAO criarEventoFinanceiroFBDAO() {
		EventoFinanceiroFBDAOHibernate eventoFinanceiroFBDAOHibernate = new EventoFinanceiroFBDAOHibernate();
		eventoFinanceiroFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return eventoFinanceiroFBDAOHibernate;
	}

	public static OrcamentoMetaItemFBDAO criarOrcamentoMetaItemFBDAO() {
		OrcamentoMetaItemFBDAOHibernate orcamentoMetaItemFBDAOHibernate = new OrcamentoMetaItemFBDAOHibernate();
		orcamentoMetaItemFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return orcamentoMetaItemFBDAOHibernate;
	}

	public static OrcamentoGrupoFBDAO criarOrcamentoGrupoFBDAO() {
		OrcamentoGrupoFBDAOHibernate orcamentoGrupoFBDAOHibernate = new OrcamentoGrupoFBDAOHibernate();
		orcamentoGrupoFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return orcamentoGrupoFBDAOHibernate;
	}

	public static OrcamentoMetaFBDAO criarOrcamentoMetaFBDAO() {
		OrcamentoMetaFBDAOHibernate orcamentoMetaFBDAOHibernate = new OrcamentoMetaFBDAOHibernate();
		orcamentoMetaFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return orcamentoMetaFBDAOHibernate;
	}

	public static LinhaProdutoMetaFBDAO criarLinhaProdutoMetaFBDAO() {
		LinhaProdutoMetaFBDAOHibernate linhaProdutoMetaFBDAOHibernate = new LinhaProdutoMetaFBDAOHibernate();
		linhaProdutoMetaFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return linhaProdutoMetaFBDAOHibernate;
	}

	public static GestaoVendaMobFBDAO criarGestaoVendaMobFBDAO() {
		GestaoVendaMobFBDAOHibernate gestaoVendaMobFBDAOHibernate = new GestaoVendaMobFBDAOHibernate();
		gestaoVendaMobFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return gestaoVendaMobFBDAOHibernate;
	}

	public static GrupoFinanceiroFBDAO criarGrupoFinanceiroFBDAO() {
		GrupoFinanceiroFBDAOHibernate grupoFinanceiroFBDAOHibernate = new GrupoFinanceiroFBDAOHibernate();
		grupoFinanceiroFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return grupoFinanceiroFBDAOHibernate;
	}
	
	public static ProdutoEstoqueLoteFBDAO criarProdutoEstoqueLoteFBDAO() {
		ProdutoEstoqueLoteFBDAOHibernate produtoEstoqueLoteFBDAOHibernate = new ProdutoEstoqueLoteFBDAOHibernate();
		produtoEstoqueLoteFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return produtoEstoqueLoteFBDAOHibernate;
	}

	public static DevVendaDTODAO criarDevVendaDTODAO() {
		DevVendaDTODAOHibernate devVendaDTODAOHibernate = new DevVendaDTODAOHibernate();
		devVendaDTODAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return devVendaDTODAOHibernate;
	}

	public static ParametroFBDAO criarParametroFBDAO() {
		ParametroFBDAOHibernate parametroFBDAOHibernate = new ParametroFBDAOHibernate();
		parametroFBDAOHibernate.SetSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return parametroFBDAOHibernate;
	}

	public static ColetorInvFBDAO criarColetorInvFB() {
		ColetorInvFBDAOHibernate coletorInvFBDAOHibernate = new ColetorInvFBDAOHibernate();
		coletorInvFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return coletorInvFBDAOHibernate;
	}

	public static ColetorInvContagemFBDAO criarColetorInvContagemFB() {
		ColetorInvContagemFBDAOHibernate coletorInvContagemFBDAOHibernate = new ColetorInvContagemFBDAOHibernate();
		coletorInvContagemFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return coletorInvContagemFBDAOHibernate;
	}

	public static InfoGerProdutoFBDAO criarInfoGerProdutoFBDAO() {
		InfoGerProdutoFBDAOHibernate infoGerProdutoFBDAOHibernate = new InfoGerProdutoFBDAOHibernate();
		infoGerProdutoFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return infoGerProdutoFBDAOHibernate;
	}

	public static ProdutoCBFBDAO criarProdutoCBFBDAO() {
		ProdutoCBFBDAOHibernate produtoCBFBDAOHibernate = new ProdutoCBFBDAOHibernate();
		produtoCBFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return produtoCBFBDAOHibernate;
	}

	public static ColetorPCFBDAO criarColetorPCFB() {
		ColetorPCFBDAOHibernate coletorPCFBDAOHibernate = new ColetorPCFBDAOHibernate();
		coletorPCFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return coletorPCFBDAOHibernate;
	}
	
	public static ColetorPCFBDAO criarColetorPCFB(Session session) {
		ColetorPCFBDAOHibernate coletorPCFBDAOHibernate = new ColetorPCFBDAOHibernate();
		coletorPCFBDAOHibernate.setSession(session);
		return coletorPCFBDAOHibernate;
	}
	
	public static ColetorPCFBContagemDAO criarColetorPCFBContagem() { 
		ColetorPCFBContagemDAOHibernate coletorPCFContagemDAOHibernate = new ColetorPCFBContagemDAOHibernate();
		coletorPCFContagemDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return coletorPCFContagemDAOHibernate;
	}

	public static NFCompraFBDAO criarNFCompraFBDAO() {
		NFCompraFBDAOHibernate nfCompraFBDAOHibernate = new NFCompraFBDAOHibernate();
		nfCompraFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return nfCompraFBDAOHibernate;
	}

	public static NFCompraItemFBDAO criarNFCompraItemFB() {
		NFCompraItemFBDAOHibernate nfCompraItemFBDAOHibernate = new NFCompraItemFBDAOHibernate();
		nfCompraItemFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return nfCompraItemFBDAOHibernate;
	}

	public static ColetorPCItemFBDAO criarColetorPCItemFBDAO() {
		ColetorPCItemFBDAOHibernate coletorPCItemFBDAOHibernate = new ColetorPCItemFBDAOHibernate();
		coletorPCItemFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return coletorPCItemFBDAOHibernate;
	}
	
	public static ColetorPCItemFBDAO criarColetorPCItemFBDAO(Session session) {
		ColetorPCItemFBDAOHibernate coletorPCItemFBDAOHibernate = new ColetorPCItemFBDAOHibernate();
		coletorPCItemFBDAOHibernate.setSession(session);
		return coletorPCItemFBDAOHibernate;
	}
	
	public static FornecedorFBDAO criarFornecedorDao() {
		FornecedorFBDAOHibernate fornecedorFBDAOHibernate = new FornecedorFBDAOHibernate();
		fornecedorFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return fornecedorFBDAOHibernate;
	}
	
	public static ColetorPCNFCompraFBDAO criarNFCompraFBNFDAO() {
		ColetorPCNFCompraFBDAOHibernate coletorPCFBNFDAOHibernate = new ColetorPCNFCompraFBDAOHibernate();
		coletorPCFBNFDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return coletorPCFBNFDAOHibernate;
	}

	public static ColetorDivergenciaFBDAO criarDivergenciaPCFB() {
		ColetorDivergenciaFBDAOHibernate  coletorPCDivergenciaFBDAOHibernate = new ColetorDivergenciaFBDAOHibernate();
		coletorPCDivergenciaFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return coletorPCDivergenciaFBDAOHibernate;
	}

	public static ColetorPCDivergFBDAO criarDivergPCFB() {
		ColetorPCDivergFBDAOHibernate coletorPCDivergFBDAOHibernate = new ColetorPCDivergFBDAOHibernate();
		coletorPCDivergFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return coletorPCDivergFBDAOHibernate;
	}

	public static PlanilhaCegaFirebirdDAO criarPlanilhaCegaFirebirdDAO() {
		PlanilhaCegaFirebirdDAOHibernate planilhaCegaFirebirdDAOHibernate = new PlanilhaCegaFirebirdDAOHibernate();
		planilhaCegaFirebirdDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return planilhaCegaFirebirdDAOHibernate;
	}

	public static PlanilhaCegaItemFirebirdDAO criarPlanilhaCegaItemFirebirdDAO() {
		PlanilhaCegaItemFirebirdDAOHibernate planilhaCegaItemFirebirdDAOHibernate = new PlanilhaCegaItemFirebirdDAOHibernate();
		planilhaCegaItemFirebirdDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return planilhaCegaItemFirebirdDAOHibernate;
	}

	public static UsuarioColetorDivergFBDAO criarUsuarioColetorDiverg() {
		UsuarioColetorDivergFBDAOHibernate usuarioColetorDivergFBDAOHibernate = new UsuarioColetorDivergFBDAOHibernate();
		usuarioColetorDivergFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());		
		return usuarioColetorDivergFBDAOHibernate;
	}

	public static ConferenteFBDAO criarConferenteFBDAO() {
		ConferenteFBDAOHibernate conferenteFBDAOHibernate = new ConferenteFBDAOHibernate();
		conferenteFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return conferenteFBDAOHibernate;
	}

	public static RomaneioFBDAO criarRomaneioFB() {
		RomaneioFBDAOHibernate romaneioFBDAOHibernate = new RomaneioFBDAOHibernate();
		romaneioFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return romaneioFBDAOHibernate;
	}

	public static RomaneioItemFBDAO criarRomaneioItemFBDAO() {
		RomaneioItemFBDAOHibernate romaneioItemFBDAOHibernate = new RomaneioItemFBDAOHibernate();
		romaneioItemFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return romaneioItemFBDAOHibernate;
	}

	public static TipoVendedorFBDAO criarTipoVendedorFBDAO() {
		TipoVendedorFBDAOHibernate tipoVendedorFBDAOHibernate = new TipoVendedorFBDAOHibernate();
		tipoVendedorFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return tipoVendedorFBDAOHibernate;
	}

	public static RomaneioItemPedidoFBDAO criarRomaneioItemPedidoFBDAO() {
		RomaneioItemPedidoFBDAOHibernate romaneioItemFBDAOHibernate = new RomaneioItemPedidoFBDAOHibernate();
		romaneioItemFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return romaneioItemFBDAOHibernate;
	}

	public static RomaneioContagemFBDAO criarRomaneioContagemFB() {
		RomaneioContagemFBDAOHibernate romaneioContagemFBDAOHibernate = new RomaneioContagemFBDAOHibernate();
		romaneioContagemFBDAOHibernate.setSession(HibernateUtil.getSessionFactoryFirebird().getCurrentSession());
		return romaneioContagemFBDAOHibernate;
	}

}
