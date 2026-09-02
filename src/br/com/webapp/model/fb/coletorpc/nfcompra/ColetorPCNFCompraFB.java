package br.com.webapp.model.fb.coletorpc.nfcompra;


import java.io.Serializable;

public class ColetorPCNFCompraFB implements Serializable {

	private static final long serialVersionUID = 8444956187895967696L;
	private Integer coletorId;
	private Integer nfCompraId;

	public Integer getColetorId() {
		return coletorId;
	}
	public void setColetorId(Integer coletorId) {
		this.coletorId = coletorId;
	}
	public Integer getNfCompraId() {
		return nfCompraId;
	}
	public void setNfCompraId(Integer nfCompraId) {
		this.nfCompraId = nfCompraId;
	}

}
