//<![CDATA[
var chartPieNormal;
var chartPieUrgente;
var chartPieProgramado;
var chartPieProgramado;
var barAprovCategDBLab;

var dataPieNormal;
var dataPieUrgente;
var dataPieProgramado;
var dataPieAmostras;
var dataBarCategoria;

var dashBoardLabSlas;
var dashBoardLabProdutoLinhas;

function handleDashBoardLaboratorio(xhr, status, args) {
	
	//console.log("handleDashBoardLaboratorio - inicio");
	dashBoardLabSlas = JSON.parse(args.dashBoardLabSlas); //serialized data from server
	dashBoardLabProdutoLinhas = JSON.parse(args.dashBoardLabProdutoLinhas); //serialized data from server
    
    if(args.dashBoardLabSlas !== undefined) {
	   
	   
    	var obj = $.parseJSON(args.dashBoardLabSlas);
	   
    	dataPieNormal = new google.visualization.DataTable();
    	dataPieNormal.addColumn('string', 'Descricao');
    	dataPieNormal.addColumn('number', 'Total');

    	dataPieUrgente = new google.visualization.DataTable();
    	dataPieUrgente.addColumn('string', 'Descricao');
    	dataPieUrgente.addColumn('number', 'Total');

    	dataPieProgramado = new google.visualization.DataTable();
    	dataPieProgramado.addColumn('string', 'Descricao');
    	dataPieProgramado.addColumn('number', 'Total');

    	dataPieAmostras = new google.visualization.DataTable();
    	dataPieAmostras.addColumn('string', 'Descricao');
    	dataPieAmostras.addColumn('number', 'Total');
	        
    	$.each(obj, function (index, val) {
    		if(val.id==2){ // normal
    			dataPieNormal.addRows([["Em Desenv.", val.desenv]]);
    			dataPieNormal.addRows([["Aprovados", val.aprovados]]);
    			dataPieNormal.addRows([["Reprovados", val.reprovados]]);
    		}
    		else if(val.id==1){ // urgente
    			dataPieUrgente.addRows([["Em Desenv.", val.desenv]]);
    			dataPieUrgente.addRows([["Aprovados", val.aprovados]]);
    			dataPieUrgente.addRows([["Reprovados", val.reprovados]]);
    		}
    		else if(val.id==3){ // programado
    			dataPieProgramado.addRows([["Em Desenv.", val.desenv]]);
    			dataPieProgramado.addRows([["Aprovados", val.aprovados]]);
    			dataPieProgramado.addRows([["Reprovados", val.reprovados]]);
    		}else if(val.id==0){ // total geral
    			dataPieAmostras.addRows([["Em Desenv.", val.desenv]]);
    			dataPieAmostras.addRows([["Aprovados", val.aprovados]]);
    			dataPieAmostras.addRows([["Reprovados", val.reprovados]]);
    		}
    	});

    	var options = {
    			pieHole: 0.4,
    			legend: 'none',
    			colors:['#324ca1','#43d14a','#ff0000',],
    	};

    	chartPieNormal = new google.visualization.PieChart(document.getElementById('pieNormalDBLab'));
    	//google.visualization.events.addListener(chart, 'select', selectHandlerCheckInOut);
    	chartPieNormal.draw(dataPieNormal, options);

    	chartPieUrgente = new google.visualization.PieChart(document.getElementById('pieUrgenteDBLab'));
    	chartPieUrgente.draw(dataPieUrgente, options);

    	chartPieProgramado = new google.visualization.PieChart(document.getElementById('pieProgramadoDBLab'));
    	chartPieProgramado.draw(dataPieProgramado, options);

    	chartPieAmostras = new google.visualization.PieChart(document.getElementById('pieAmostrasDBLab'));
    	chartPieAmostras.draw(dataPieAmostras, options);

    	// Bar Categoria
    	var obj2 = $.parseJSON(args.dashBoardLabProdutoLinhas);

    	dataBarCategoria = new google.visualization.DataTable();
    	dataBarCategoria.addColumn('string', 'Descricao');
    	dataBarCategoria.addColumn('number', 'Em Desenv.');
    	dataBarCategoria.addColumn('number', 'Aprovados');
    	dataBarCategoria.addColumn('number', 'Reprovados');

    	$.each(obj2, function (index, val) {
    		dataBarCategoria.addRows([[val.descricao, val.desenv, val.aprovados, val.reprovados]]);
    	});

    	barAprovCategDBLab = new google.visualization.ColumnChart(document.getElementById('barAprovCategDBLab'));
    	barAprovCategDBLab.draw(dataBarCategoria, options);

   }

   //console.log("handleDashBoardLaboratorio - fim");

}

// ]]>