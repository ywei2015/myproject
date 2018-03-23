var request = fastDev.Browser.getRequest();

// 刷新条件
var condition = null;
var barData = null;
var pieData = null;

/**
 * 过滤信息
 */
function doSearch() {
	barData = null;
	pieData = null;
	var fm = fastDev.getInstance('checkform');
	condition = fm.getItems();
	condition.type = fastDev.getInstance("chartType").getValue();
	
	jQuery.post("taskCount_countTask.action", condition, function(data) {
		if(condition.type==1){
			barData = data;
			toBarView(data);
		}else{
			pieData = data;
			toPieView(data);
			
		}
	});

}
/**
 * 重置表单数据
 */
function doReset() {
	fastDev.getInstance('checkform').cleanData();
}

function toBarView(dataString) {
	var data = fastDev.Util.JsonUtil.parseJson(dataString);
	$('#container')
			.highcharts(
					{
						chart : {
							type : 'column'
						},
						title : {
							text : '任务统计'
						},
						subtitle : {
							text : '按状态统计'
						},
						xAxis : {
							categories : data.categories
						},
						yAxis : {
							min : 0,
							title : {
								text : '个数'
							}
						},
						tooltip : {
							headerFormat : '<span style="font-size:10px">{point.key}</span><table>',
							pointFormat : '<tr><td style="color:{series.color};padding:0">{series.name}: </td>'
									+ '<td style="padding:0"><b>{point.y} 个</b></td></tr>',
							footerFormat : '</table>',
							shared : true,
							useHTML : true
						},
						plotOptions : {
							column : {
								pointPadding : 0.2,
								borderWidth : 0
							}
						},
						series : data.series
					});
}
function valueChange(e,item)
{
	if (item.value==2) {

		if (pieData != null) {
			toPieView(pieData);
			return;
		}
		var fm = fastDev.getInstance('checkform');
		condition = fm.getItems();
		condition.type = 2;
		jQuery.post("taskCount_countTask.action", condition, function(
				data) {
			pieData = data;
			toPieView(data);
		});

	} else {
		if (barData != null) {
			toBarView(barData);
			return;
		}
		var fm = fastDev.getInstance('checkform');
		condition = fm.getItems();
		condition.type = 1;
		jQuery.post("taskCount_countTask.action", condition, function(data) {
			barData = data;
			toBarView(data);
		});
	}
}
function typeClick() {
	

}

function toPieView(dataString)
{
	//dataString = dataString.replace(/\\/g, "\\\\");
	var data = fastDev.Util.JsonUtil.parseJson(dataString);

    $('#container').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: '任务统计'
        },
        tooltip: {
    	    pointFormat: '<b>占百分比:</b> {point.percentage:.1f}% <br/> <b>{series.name}: </b>{point.y}'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    color: '#000000',
                    connectorColor: '#000000',
                    formatter: function() {
                        return '<b>个数:</b> '+ this.point.y +' 个';
                    }
                }
            }
        },
        series: [{
            type: 'pie',
            name: '个数',
            per:"占百分比",
            data: data
        }]
    });
	



}