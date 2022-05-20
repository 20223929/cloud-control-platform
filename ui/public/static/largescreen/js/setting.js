var timer, counter = 0;
var wsArr = [];
var stompClient;
var connectNormal = false;
var hrefs = [
	"http://192.168.30.223:8080/intelligent-audit/freeflow-data-api/v1/api/",
	"http://192.168.30.223:8080/intelligent-audit/",
	"192.168.30.230:8083/bigmonitor/websocket?modelCode=",
   ];
function init() {
	//设置定时器
	document.getElementById('datetime').innerHTML = '当前时间：' + new Date().Format();
	timer = setInterval(function () {
		document.getElementById('datetime').innerHTML = '当前时间：' + new Date().Format();
		counter++;
		if (counter % 10 === 0) {
			//30分钟更新数据
			update('payBackAudit');
		}
		if (counter % 180 === 0) {
			//60分钟更新数据
			update('evasionAudit');

			//重置counter防止溢出
			counter = 0;
		}

	}, 1000);

	// 初始化图表
	

	// 请求数据
	update('payBackAudit');
	update('evasionAudit');

	//开始websocket连接
	[...document.querySelectorAll(".ws")].forEach(function (val, index) {
		// statements
		let action = val.getAttribute("data-ws");
		let ws;
		getSocket(action, ws, val);

	});
}

/**
* 重新连接
* */
// function reConnect(action,ws,val){
// 	console.log("socket 连接断开，正在尝试重新建立连接");
// 	getSocket(action,ws,val);
// 	setTimeout(function(){
// 		if(!connectNormal){
// 			reConnect(action,ws,val);
// 		}
// 	},5000)
// }

// 心跳定时
// var heartCheck = {
//     timeout: heartBeatTime*1000,  //  心跳检测时长
//     timeoutObj: null, // 定时变量
//     reset: function () { // 重置定时
//         clearTimeout(this.timeoutObj);
//         return this;
//     },
//     start: function () { // 开启定时
//         var self = this;
//         this.timeoutObj = setTimeout(function () {
//           // 心跳时间内收不到消息，主动触发连接关闭，开始重连
//             ws.close();
//         },this.timeout)
//     }
// }

//连接socket
function getSocket(action, ws, val) {
	if (action == "onLinePathQuery") {
		const url = hrefs[1] + "sock"
		connect(url);
	} else {
		const url = hrefs[2] + action;
		if ('WebSocket' in window) {
			ws = new WebSocket("ws:" + url);
		} else if ('MozWebSocket' in window) {
			ws = new MozWebSocket("ws:" + url);
		} else {
			ws = new SockJS("http:" + url);
		}
		ws.val = val.id;
		ws.onopen = function (evt) {
			connectNormal = true;
			// console.log(evt);
			// 发送消息
			// wsDatas[val.id].param.provinceId = provinceId;
			// ws.send(JSON.stringify(wsDatas[val.id].param));
		};
		ws.onmessage = function (evt) {
			// console.log(evt);
			//拿到数据后进行处理
			initWebSocket(evt.target.val, JSON.parse(evt.data));
		};
		ws.onclose = function (evt) {
			connectNormal = false;
			console.log(evt.target.val + " WebSocketClosed!");
			setTimeout(function () {
				getSocket(action, ws, val)
			}, 5000)
		};
		ws.onerror = function (evt) {
			connectNormal = false;
			console.log(evt.target.val + "WebSocketError!");
			setTimeout(function () {
				getSocket(action, ws, val)
			}, 5000)
		};
		wsArr[val.id] = ws;
	}
}

function update(api) {
	$.ajax({
		type: "post",
		url: hrefs[0] + api,
		contentType: 'application/json;charset=utf-8',
		dataType: 'json',
		success: function (res) {
			console.log(api);
			console.log(res);
			if (res.code == 200) {
				if (api == "payBackAudit") {
					var data = res.data;
					Object.keys(data).forEach(function (key, index) {
						var list = data[key];
						Object.keys(list).forEach(function (k) {
							$("#" + k).html(list[k]);
							// if(k == "lossPayMoney"){
							// 	if(list[k]-0 >= 10000){
							// 		list[k] = (list[k] / 10000).toFixed(2);
							// 		$("#"+key+"_"+k+"_m").text("万元");
							// 	}else{
							// 		list[k] = (list[k] / 1);
							// 		$("#"+key+"_"+k+"_m").text("元");
							// 	}
							// $("#"+key+"_"+k).html(list[k]);
							// }else if(k == 'lossAuditMoney'){
							// 	$("#"+k).html(((list[k]?list[k]:0)/100));
							// }else{

							// }
						})
					})
				} else if (api == "evasionAudit") {
					var data = res.data;
					data2.bar1.name = data.lossStationName.reverse();
					data2.bar1.data = data.lossCnt.reverse();
					data2.bar2.name = data.mtcLossStationName.reverse();
					data2.bar2.data = data.mtcLossCnt.reverse();
					Object.keys(data).forEach(function (key, index) {
						var list = data[key], name = key;
						lData(name, list, data2);
					})
				}
			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
			console.log(XMLHttpRequest.status);
			console.log(XMLHttpRequest.readyState);
			console.log(textStatus);
		}
	});
}

function showCharts() {
	getOptionData();
}

function formatData(num) {
	num += '';
	if (!num.includes('.')) num += '.';
	return num.replace(/(\d)(?=(\d{3})+\.)/g, function ($0, $1) {
		return $1 + ',';
	}).replace(/\.$/, '');
}

function initWebSocket(id, data) {
	if (data && data.code == 1) {
		// const d = data.data;
		let da = data.data;
		let opt = option.line;
		let opt1 = option.line2;
		let lastData = {
			tem1: [],
			tem2: [],
			x: [],
		}
		let falseData = {
			obu: [
				// 99.10278063,
				// 99.10341379,
				// 99.10246879,
				// 99.10118471,
				// 99.09384147,
				// 99.09144863,
				// 99.09251883,
				// 99.09085056,
				// 99.09023045,
				// 99.09003056,
				// 99.0886656,
				// 99.08960564,
				// 99.08904588,
				// 99.09535982,
				// 99.0946304,
				// 98.98128898,
				// 99.00545742,
				// 99.08146484,
				// 99.12142305,
				// 99.12961884,
				// 99.04194718,
				// 99.19244546,
				// 99.01796487,
				99.25231221,
				99.40855672,
				99.21835813,
				99.25366911,
				99.32853893,
				99.20797058,
				99.22487918,
				99.36 ,
				99.36 ,
				99.30 ,
				
				99.15,
				99.19,
				99.19,
				99.26,
				99.20
			],
			obuGet: [
				// 99.71096347,
				// 99.71057112,
				// 99.70954956,
				// 99.70756218,
				// 99.70558281,
				// 99.70190584,
				// 99.70160602,
				// 99.70089055,
				// 99.70098004,
				// 99.70043597,
				// 99.69581525,
				// 99.69403939,
				// 99.69114904,
				// 99.69550528,
				// 99.69627198,
				// 99.63689884,
				// 99.64230783,
				// 99.70396448,
				// 99.6730615,
				// 99.72868217,
				// 99.73007122,
				// 99.82784383,
				// 99.68556615,
				99.68953473,
				99.7126767,
				99.59127497,
				99.68386023,
				99.67023304,
				99.62059451,
				99.74309167,
				99.85 ,
				99.86 ,
				99.85 ,
				99.90 ,
				99.83 ,
				99.77 ,
				99.81 ,
				99.79
			],
			carGet: [
				// 98.3266,
				// 98.27768,
				// 95.45669,
				// 98.74355,
				// 95.26533495,
				// 96.53186,
				// 97.88079,
				// 98.65342,
				// 98.52313,
				// 98.49,
				// 99.24,
				// 99.16,
				// 99.21,
				// 99.15,
				// 99.22,

				// 98.29784501,
				// 98.18193817,
				// 95.11609287,
				// 97.05380756,
				// 95.33858943,
				// 98.25481666,
				// 98.28967715,
				// 98.43567061,
				// 98.44636513,
				97.9589929,
				97.44358998,
				98.68273256,
				98.57964448,
				98.15003049,
				98.31918396,
				97.88079,
				99.6176219,
				99.6014058,
				99.6395849,
				99.5408331,
				99.5403171,
				99.3001931,
				99.5912707,
				99.439742
			]
		};
		let xData = [ '06/26', '06/27', '06/28',
			'06/29', '06/30', '06/31', '07/01', '07/02', '07/03', '07/04', '07/05',
			'07/06', '07/07', '07/08', '07/09']
		switch (id) {
			case "hb_1_obuSuccessRate":
				da.forEach(function (item, i) {
					lastData.tem1.push(item.singleObuSuccessRate);
					lastData.tem2.push(item.doubleObuSuccessRate);
					lastData.x.push(item.statisticalTime);
				});
				//obu交易成功率
				opt.legend.data = ['单片式', '双片式'];
				opt.title.text = title[0];
				// opt.xAxis[0].data = lastData.x;
				opt.xAxis[0].data = xData;

				opt.yAxis[0].min = 95;
				opt.series[0].data = lastData.tem1;
				// opt.series[1].data = lastData.tem2;

				opt.series[1].data = falseData.obu;

				opt.series[0].name = '单片式';
				opt.series[1].name = '双片式';
				opt.series[0].itemStyle.normal.lineStyle.color = '#0192d8';
				opt.series[1].itemStyle.normal.lineStyle.color = '#895bee';
				opt.series[0].itemStyle.normal.color = '#0192d8';
				opt.series[1].itemStyle.normal.color = '#895bee';
				opt.series[1].markLine = {
					symbol: 'none',//去掉箭头
					label: { show: true, position: 'end', formatter: '{c}%' },
					itemStyle: {
						normal: {
							lineStyle: { type: 'solid', color: '#f48916' }
						},
					},
					data: [{
						name: '12',
						yAxis: 98
					}],
				}
				opt.series[1].areaStyle = {
					color: '#895bee', normal: {
						//颜色渐变函数 前四个参数分别表示四个位置依次为左、下、右、上
						color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
							offset: 0,
							color: '#895bee'
						}, {
							offset: 1,
							color: 'rgba(24,27,78,0.00)'
						}])
					}
				}
				aa.setOption(opt);
				break;
			case "hb_1_obuGetSuccessRate":
				da.forEach(function (item, i) {
					lastData.tem1.push(item.singleObuSuccessRate);
					lastData.tem2.push(item.doubleObuSuccessRate);
					lastData.x.push(item.statisticalTime);
				});
				//obu信息获取成功率
				opt.legend.data = ['单片式', '双片式'];
				opt.title.text = title[1];
				// opt.xAxis[0].data = lastData.x;
				opt.xAxis[0].data = xData;

				opt.yAxis[0].min = 98;
				opt.series[0].data = lastData.tem1;
				// opt.series[1].data = lastData.tem2;

				opt.series[1].data = falseData.obuGet;

				opt.series[0].name = '单片式';
				opt.series[1].name = '双片式';
				opt.series[0].itemStyle.normal.lineStyle.color = '#be8d4b';
				opt.series[1].itemStyle.normal.lineStyle.color = '#14b344';
				opt.series[0].itemStyle.normal.color = '#be8d4b';
				opt.series[1].itemStyle.normal.color = '#14b344';
				opt.series[1].markLine = {
					symbol: 'none',//去掉箭头
					label: { show: true, position: 'end', formatter: '{c}%' },
					itemStyle: {
						normal: {
							lineStyle: { type: 'solid', color: '#f48916' }
						},
					},
					data: [{
						name: '12',
						yAxis: 99.5
					}],
				}
				opt.series[1].areaStyle = {
					color: '#14b344', normal: {
						//颜色渐变函数 前四个参数分别表示四个位置依次为左、下、右、上
						color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
							offset: 0,
							color: '#14b344'
						}, {
							offset: 1,
							color: 'rgba(24,27,78,0.00)'
						}])
					}
				}
				bb.setOption(opt);
				break;
			case "hb_1_cpcSuccessRate":
				da.forEach(function (item, i) {
					lastData.tem1.push(item.cpcSuccessRate);
					lastData.x.push(item.statisticalTime);
				});
				//cpc计费成功率
				// opt1.xAxis[0].data = lastData.x;
				opt1.xAxis[0].data = xData;

				opt1.series[0].data = lastData.tem1;
				// opt1.series[0].data = [];
				opt1.title.text = title[2];
				opt1.series[0].itemStyle.normal.lineStyle.color = '#56c1fe'
				opt1.series[0].itemStyle.normal.color = '#56c1fe';
				opt1.series[0].markLine = {};
				opt1.series[0].areaStyle = {
					color: '#56c1fe', normal: {
						//颜色渐变函数 前四个参数分别表示四个位置依次为左、下、右、上
						color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
							offset: 0,
							color: '#56c1fe'
						}, {
							offset: 1,
							color: 'rgba(24,27,78,0.00)'
						}])
					}
				}
				cc.setOption(opt1);
				break;
			case "hb_1_imageSuccessRate":
				da.forEach(function (item, i) {
					lastData.tem1.push(item.successRate);
					lastData.x.push(item.statisticalTime);
				});
				//车辆图像识别成功率
				// opt1.xAxis[0].data = lastData.x;
				opt1.xAxis[0].data = xData;

				opt1.yAxis[0].min = 90;
				// opt1.series[0].data = lastData.tem1;

				opt1.series[0].data = falseData.carGet;

				opt1.title.text = title[3];
				opt1.series[0].itemStyle.normal.lineStyle.color = '#fb73a0'
				opt1.series[0].itemStyle.normal.color = '#fb73a0'
				opt1.series[0].markLine = {
					symbol: 'none',//去掉箭头
					label: { show: true, position: 'end', formatter: '{c}%' },
					itemStyle: {
						normal: {
							lineStyle: { type: 'solid', color: '#f48916' }
						},
					},
					data: [{
						name: '12',
						yAxis: 95
					}],
				}
				opt1.series[0].areaStyle = {
					color: '#fb73a0', normal: {
						//颜色渐变函数 前四个参数分别表示四个位置依次为左、下、右、上
						color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
							offset: 0,
							color: 'rgba(251,115,160,0.8)'
						}, {
							offset: 1,
							color: 'rgba(24,27,78,0.00)'
						}])

					}
				}
				dd.setOption(opt1);
				break;
			case "etcConnectStatus": //ETC门架状态
			case "alarmCountInfo":   //预警/工单统计
			case "transaction":      //交易量交易金额
				const d = data.data;
				for (k in d) {
					// if(id=="transaction" && (k=="etcPayTotalNum" || k=="cpcChargingTotalNum")){
					// 	d[k]-=0;
					// 	if(d[k]-0 >= 10000){
					// 		d[k] = (d[k] / 10000).toFixed(2);
					// 		$("#"+id+"_"+k+"_m").text("万辆");
					// 	}else{
					// 		$("#"+id+"_"+k+"_m").text("辆");
					// 	}
					// }
					if (id == "transaction" && (k == "etcPayTotalTurnover" || k == "cpcChargingTotalTurnover")) {
						// if(d[k]-0 >= 10000){
						// 	d[k] = (d[k] / 10000).toFixed(2);
						// 	$("#"+id+"_"+k+"_m").text("万元");
						// }else{
						d[k] = (d[k] / 100).toFixed(2);
						$("#" + id + "_" + k + "_m").text("元");
						d[k] = formatData(d[k]);
						// }
					}
					// if(id=="transaction"){
					// 	d[k] = (d[k] / 100).toFixed(2);
					// 	if(d[k]-0 >= 1000){
					// 		var re=/(?=(?!(\b))(\d{3})+$)/g;
					// 		d[k]=d[k].toString().replace(re,",");
					// 	}
					// }
					if (k == "overtimeRate" || k == "orderUpperRate" || k == "orderTransformRate") {
						d[k] = ((d[k] - 0) * 100).toFixed(2) + "%";
					}
					if (id == "etcConnectStatus" && k == "detail") {
						console.log(d[k]);
						// $("#etcConnectStatus_normal").html(d["normal"]);
						// $("#etcConnectStatus_unConnect").html(parseInt(d["abnormal"])+parseInt(d["unConnect"]));
						if (d[k].length > 0) {
							d[k].forEach(function (item, i) {
								//只显示170
								if (item.virtualStationId + "_" + item.frameNo == "G0002110010170_1") {

									if (item.status == 1) {
										$("#etcConnectStatus_normal_green").html(6);
										$("#etcConnectStatus_unConnect_red").html(0);
									} else {
										$("#etcConnectStatus_normal_green").html(5);
										$("#etcConnectStatus_unConnect_red").html(1);
									}

									// statements
									const cls = { "0": "error", "1": "online", "2": "error" };
									$("#" + item.virtualStationId + "_" + item.frameNo + " span").attr("class", cls[item.status]);
									let html = "";
									if (item.status == 0) {
										html += "<li>未连接</li>";
										$("#" + item.virtualStationId + "_" + item.frameNo + " ul").html(html);
									}
									if (item.status == 2) {
										let count = 0;
										if (item.mstrBakFlag&&item.mstrBakFlag.indexOf("2") > -1) {
											html += "<li>主备切换</li>";
											count++;
										}
										if (item.takeOverPointName) {
											html += "<li>被"+item.takeOverPointName+"门架接管</li>";
											count++;
										}
										if (item.computer != 1){
											html += "<li>工控机异常</li>";
											count++;
										}
										if (item.rsuStatus.indexOf("0") > -1) {
											html += "<li>天线异常</li>";
											count++;
										}
										if (item.vplrStatus.indexOf("0") > -1) {
											html += "<li>高卡异常</li>";
											count++;
										}
										if (item.softWare != 1){
											html += "<li>软件异常</li>";
											count++;
										}
										if (item.paramStatus != 1){
											html += "<li>费率异常</li>";
											count++;
										}
										if(item.powerType == 1){
											html += "<li>UPS供电</li>";
											count++;
										}
										if(item.cableNetState != 1){
											html += "<li>有线网络异常</li>";
											count++;
										}
										if(item.wireLessState != 1){
											html += "<li>无线网络异常</li>";
											count++;
										}
										
										$("#" + item.virtualStationId + "_" + item.frameNo + " ul").html(html).attr("class", "item" + count);
									}

									if (html) {
										$("#" + item.virtualStationId + "_" + item.frameNo).removeClass("none");
									} else {
										$("#" + item.virtualStationId + "_" + item.frameNo).addClass("none");
									}

								}

							});
						}
					} else {
						$("#" + id + "_" + k).text(d[k]);
						console.log("#" + id + "_" + k + '=' + d[k]);
					}
				}
				break;
			default:
				// statements_def
				break;
		}
	}
}

function connect(url) {
	//接收车子路径
	var socket = new SockJS(url);
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function (frame) {
		stompClient.subscribe('/topic/onLinePathQuery', function (res) {
			result = JSON.parse(res.body);
			var arr = [];
			for (var i = 0; i < result.length; i++) {
				arr[i] = result[i].stationId;
				$('#car span').html(result[i].exVehiclePlate);
			}
			moveList(arr);

			// var i=0;
			// console.log(result);
			// setInterval(function(){
			// 	if(i<result.length){
			// 		$('.car,.car1').attr('class',firstPoint[result[i].stationId]);
			// 		$('.car,.car1').find('span').html(result[i].exVehiclePlate);
			// 		i++;
			// 	}
			// },4000)
		});
	});
}

// Date格式化
Date.prototype.Format = function () {
	var fmt = "<font>yyyy</font> 年 <font>MM</font>月<font>dd</font>日 hh:mm:ss";
	var o = {
		"M+": this.getMonth() + 1, //月份 
		"d+": this.getDate(), //日 
		"h+": this.getHours(), //小时 
		"m+": this.getMinutes(), //分 
		"s+": this.getSeconds(), //秒 
	};
	if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};
