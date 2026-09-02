package br.com.webapp.model.fb.diasuteis;

import java.util.Calendar;
import java.util.Date;

import br.com.webapp.web.util.DAOFactoryFirebird;
import br.com.webapp.web.util.UtilData;

public class DiasUteisFBRN {
	
	private DiasUteisFBDAO diasUteisFBDAO;
	public DiasUteisFBRN() {
		diasUteisFBDAO = DAOFactoryFirebird.criarDiasUteisFBDAO();
	}
	
	public DiasUteisFB carregar(Date dtReferencia) {
		if(dtReferencia!=null) {
			Calendar cDt1 = Calendar.getInstance();
			cDt1.setTime(dtReferencia);
			cDt1.set(Calendar.DAY_OF_MONTH, 1);
			cDt1.set(Calendar.HOUR_OF_DAY, 0);
			cDt1.set(Calendar.MINUTE, 0);
			cDt1.set(Calendar.SECOND, 0);
			
			Calendar cDt2 = Calendar.getInstance();
			cDt2.setTime(dtReferencia);
			cDt2.set(Calendar.HOUR_OF_DAY, 23);
			cDt2.set(Calendar.MINUTE, 59);
			cDt2.set(Calendar.SECOND, 59);
			
			Calendar cDt3 = Calendar.getInstance();
			cDt3.setTime(cDt1.getTime());
			cDt3.add(Calendar.MONTH, 1);
			cDt3.add(Calendar.DATE, -1);
			cDt3.set(Calendar.HOUR_OF_DAY, 23);
			cDt3.set(Calendar.MINUTE, 59);
			cDt3.set(Calendar.SECOND, 59);
			
			String dataFilter1 = UtilData.formatarData(cDt1.getTime(), UtilData.FORMATO_DATA_INVERTIDA);
			String dataFilter2 = UtilData.formatarData(cDt2.getTime(), UtilData.FORMATO_DATA_INVERTIDA);
			String dataFilter3 = UtilData.formatarData(cDt3.getTime(), UtilData.FORMATO_DATA_INVERTIDA);
			return this.diasUteisFBDAO.carregar(dataFilter1, dataFilter2, dataFilter3);
		}else {
			return null;
		}
	}

}
