//<![CDATA[ 
var chartPeriodoAvaliacao;
var chartCFsPeriodoAvaliacao;
var chartEmAberto;
var chartEmAndamento;
var chartModulos;
var dataPeriodoAvaliacao;
var dataCFsPeriodoAvaliacao;
var dataEmAberto;
var dataEmAndamento;
var dataModulos;
var idJson;
var descJson;
var totalJson;
var percJson;
var idCFsJson;
var descCFsJson;
var totalCFsJson;
var percCFsJson;
var idJsonTipos;
var corJsonTipos;
var descJsonTipos;
var totalJsonTiposEmAberto;
var totalJsonTiposEmAndamento;
var idModJson;
var descModJson;
var descSisModJson;
var totalModJson;
var pesoTipoModJson;
var pesoUrgenciaModJson;

	function handleIndicadoresBean(xhr, status, args) {
	
		if(args.idJson !== undefined){
			
			idJson = JSON.parse(args.idJson);//serialized data from server
		    descJson = JSON.parse(args.descJson);//serialized data from server
		    totalJson = JSON.parse(args.totalJson);//serialized data from server
		    percJson = JSON.parse(args.percJson);//serialized data from server
		    
		    idCFsJson = JSON.parse(args.idCFsJson);//serialized data from server
		    descCFsJson = JSON.parse(args.descCFsJson);//serialized data from server
		    totalCFsJson = JSON.parse(args.totalCFsJson);//serialized data from server
		    percCFsJson = JSON.parse(args.percCFsJson);//serialized data from server
		    
		    idJsonTipos = JSON.parse(args.idJsonTipos);
			descJsonTipos = JSON.parse(args.descJsonTipos);
			corJsonTipos = JSON.parse(args.corJsonTipos);
			totalJsonTiposEmAberto = JSON.parse(args.totalJsonTiposEmAberto);
			totalJsonTiposEmAndamento = JSON.parse(args.totalJsonTiposEmAndamento);
			
			idModJson = JSON.parse(args.idModJson);
			descModJson = JSON.parse(args.descModJson);
			descSisModJson = JSON.parse(args.descSisModJson);
			totalModJson = JSON.parse(args.totalModJson);
			pesoTipoModJson = JSON.parse(args.pesoTipoModJson);
			pesoUrgenciaModJson = JSON.parse(args.pesoUrgenciaModJson);
			
		    
		    if(args.totalJson !== undefined) {
			   
			   var obj = $.parseJSON(args.totalJson);
			   dataPeriodoAvaliacao = new google.visualization.DataTable();
			   dataPeriodoAvaliacao.addColumn('string', 'Descricao');
			   dataPeriodoAvaliacao.addColumn('number', '%');
			   dataPeriodoAvaliacao.addColumn({type:'number', role:'annotation'});
			   dataPeriodoAvaliacao.addColumn({type: 'string', role: 'style'});
			   
		       $.each(obj, function (index, val) {
		    	   dataPeriodoAvaliacao.addRows([[descJson[index], Math.round(percJson[index]), Math.round(percJson[index]), 'opacity:0.8;']]);
			   });
		       
		       dataCFsPeriodoAvaliacao = new google.visualization.DataTable();
			   dataCFsPeriodoAvaliacao.addColumn('string', 'Descricao');
			   dataCFsPeriodoAvaliacao.addColumn('number', '%');
			   dataCFsPeriodoAvaliacao.addColumn({type: 'string', role: 'style'});
		        
		       $.each(obj, function (index, val) {
		    	   dataCFsPeriodoAvaliacao.addRows([[descCFsJson[index], percCFsJson[index], 'opacity:0.8;']]);
			   });
		       
		       var obj2 = $.parseJSON(args.idJsonTipos);
		       dataEmAberto = new google.visualization.DataTable();
		       dataEmAberto.addColumn('string', 'Descricao');
		       dataEmAberto.addColumn('number', '%');
		       dataEmAberto.addColumn({type: 'string', role: 'style'});
		       
		       dataEmAndamento = new google.visualization.DataTable();
		       dataEmAndamento.addColumn('string', 'Descricao');
		       dataEmAndamento.addColumn('number', '%');
		       dataEmAndamento.addColumn({type: 'string', role: 'style'});
		        
		       $.each(obj2, function (index, val) {
		    	   //console.log("["+index+"][descJsonTipos]: " + descJsonTipos[index] + " [corJsonTipos]: #" + corJsonTipos[index]);
		    	   dataEmAberto.addRows([[descJsonTipos[index], totalJsonTiposEmAberto[index], 'color: #' + corJsonTipos[index]]]);
		    	   dataEmAndamento.addRows([[descJsonTipos[index], totalJsonTiposEmAndamento[index], 'color: #' + corJsonTipos[index]]]);
			   });
		       
		       var obj3 = $.parseJSON(args.idModJson);
		       dataModulos = new google.visualization.DataTable();
		       dataModulos.addColumn('string', 'Modulo');
		       dataModulos.addColumn('number', 'Tipo');
		       dataModulos.addColumn('number', 'Urgência');
		       dataModulos.addColumn('string', 'Sistema');
		       dataModulos.addColumn('number', 'Chamados');
		       
		       $.each(obj3, function (index, val) {
		    	   console.log("["+index+"][descModJson]: " + descModJson[index]);
		    	   console.log("["+index+"][descSisModJson]: " + descSisModJson[index]);
		    	   console.log("["+index+"][totalModJson]: " + totalModJson[index]);
		    	   dataModulos.addRows([[descModJson[index], pesoTipoModJson[index], pesoUrgenciaModJson[index], descSisModJson[index], totalModJson[index]]]);
			   });
		       
				var options = {
					legend: { position: "none" },
					is3D: true
				};
				
				var options2 = {
					colors : $.parseJSON(args.corJsonTipos),
					legend: { position: "none" },
					is3D: true
				};
				
				var options3 = {
					//colorAxis: {colors: ['yellow', 'red']},
					legend: { position: "top" },
					hAxis: {title: 'Tipo'},
			        vAxis: {title: 'Urgência'},
				};
				
				chartPeriodoAvaliacao = new google.visualization.ColumnChart(document.getElementById('collumnChartIndicadoresBean'));
				//google.visualization.events.addListener(chartPeriodoAvaliacao, 'select', selectHandlerIndicadoresBean);
				chartPeriodoAvaliacao.draw(dataPeriodoAvaliacao, options);
				
				chartCFsPeriodoAvaliacao = new google.visualization.ColumnChart(document.getElementById('collumnCFsChartIndicadoresBean'));
				//google.visualization.events.addListener(chartPeriodoAvaliacao, 'select', selectHandlerIndicadoresBean);
				chartCFsPeriodoAvaliacao.draw(dataCFsPeriodoAvaliacao, options);
				
				chartEmAberto = new google.visualization.PieChart(document.getElementById('chartEmAbertoIndicadoresBean'));
				//google.visualization.events.addListener(chartEmAberto, 'select', selectHandlerIndicadoresBean);
				chartEmAberto.draw(dataEmAberto, options2);
				
				chartEmAndamento = new google.visualization.PieChart(document.getElementById('chartEmAndamentoIndicadoresBean'));
				//google.visualization.events.addListener(chartEmAberto, 'select', selectHandlerIndicadoresBean);
				chartEmAndamento.draw(dataEmAndamento, options2);
				
				chartModulos = new google.visualization.BubbleChart(document.getElementById('bubbleChartIndicadoresBean'));
				//google.visualization.events.addListener(chartEmAberto, 'select', selectHandlerIndicadoresBean);
				chartModulos.draw(dataModulos, options3);
				
		   }
		}else{
			//alert('Erro no indicador');
			PF('bUIIndicadoresBean').hide();
			PF('dialogErroIndicadorBean').show();
			
		}  
	}
	
function erroPaginaIndicadoresBean(){
	PF('dialogErroIndicadorBean').hide();
	closeTab(18);
}

// ]]>

function selectHandlerIndicadoresBean() {
    var selectedItem = chart.getSelection()[0];
    if (selectedItem) {
        //alert('idJson: ' + idJson[selectedItem.row] + ' - Row: ' + selectedItem.row);
        document.getElementById("filtroIndicadoresBean:id_checkinoutmotivo").value = idJson[selectedItem.row];
    	document.getElementById("filtroIndicadoresBean:hdnBtn").click();
    }
  }