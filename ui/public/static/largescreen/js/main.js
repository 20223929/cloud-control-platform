// 稽查接口 若有项目名称需要加上：如/demo/api/v1/
var root4 = "/api/v1/";

var token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhcHBJZCI6NjUsInRlbmFudElkIjoiZmFsc2UiLCJleHAiOjE2MjU3MTk1MzYsInVzZXJuYW1lIjoiYWRtaW4ifQ.ELJHh7UU9cq6BiudAAeZ8Bp06dqcb6Pr4s4BvpUrus8";
// var token = JSON.parse(localStorage.getItem("bigmonitor")).token;
// WS
var wsUrl = "//zhgly.hgits.cn/bigMonitorStandardPage/websocket?token=" + token + "&modelCode=";


var timer, counter = 0;
var chartArr = [],
  wsArr = [];
var lightIndex = 0;

function getCData() {
  getData1();
  getData2();
  getData3();
  getData4();
  getData5();
}

function init() {
  getCData();
  window.clearInterval(timer);
  //设置定时器
  timer = setInterval(function () {
    console.log(counter);
    counter++;
    if (counter % 5 === 0) {
    }
    if (counter % 15 === 0) {
      //15秒切换地图门架
      // if (mapInfo.data.length > 0) playList();

    }
    if (counter % 30 === 0) {
      getData1();
      getData2();
      getData3();
      getData4();
      getData5();
      //15分钟更新数据
      // update('feeIdxStat');
      // update('getOutFeeStat');

      counter = 0;
    }

  }, 60000);

  // 初始化图表
  [...document.querySelectorAll(".chart")].forEach((val) => {
    if (val.id) {
      var c = echarts.init(val);
      c.setOption(getOption(val));
      chartArr[val.id] = c;
    }
  });

  // // 开始websocket连接
  // [...document.querySelectorAll(".ws")].forEach(function(val, index) {
  //     // getSocket(val);
  // });
}

//连接socket
function getSocket(val) {
  let action = val.id;
  let ws;
  const url = wsUrl + action;
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
      getSocket(val)
    }, 5000)
  };
  ws.onerror = function (evt) {
    connectNormal = false;
    console.log(evt.target.val + "WebSocketError!");
    // setTimeout(function () {
    // 	getSocket(action, ws, val)
    // }, 5000)
  };
  wsArr[val.id] = ws;
}

//参数：id或者doom对象
function getOption(val) {
  if (typeof (val) == "string") val = documemt.getElementById(val);
  return option[val.dataset.chart];
}

function initWebSocket(id, data) {
  if (data && data.desc == "success") {
    console.log(data);
    switch (id) {
      case "passTradeTrend": //1.	通行交易趋势和当天车流量金额

        document.getElementById("exFlowMoney_totalVeh").innerHTML = data.data.exFlowMoney.totalVeh.toLocaleString();
        var r = ((data.data.exFlowMoney['etcVeh'] / data.data.exFlowMoney['totalVeh']) * 100).toFixed(2);
        document.getElementById("exFlowMoney_etcVeh").innerHTML = r;
        document.getElementById("exFlowMoney_cpcVeh").innerHTML = (100 - r).toFixed(2);

        document.getElementById("exFlowMoney_totalMoney").innerHTML = Math.round(data.data.exFlowMoney['totalMoney'] / 100).toLocaleString();
        r = ((data.data.exFlowMoney['etcMoney'] / data.data.exFlowMoney['totalMoney']) * 100).toFixed(2);
        document.getElementById("exFlowMoney_etcMoney").innerHTML = r;
        document.getElementById("exFlowMoney_cpcMoney").innerHTML = (100 - r).toFixed(2);

        var day = [];
        var exVehFlow = [];
        var tradeTur = [];
        var min = [1000000000000000000, 1000000000000000000];
        var max = [0, 0];
        line_double_data = [
          [],
          []
        ];
        data.data.passTradeTrendList.forEach(function (val) {
          val.day = val.day.replace(/-/g, "/");
          day.push(new Date(val.day).Format("MM-dd"));

          line_double_data[0].push(val.exVehFlow.toLocaleString());
          val.exVehFlow = (val.exVehFlow / 10000).toFixed(2);
          exVehFlow.push(val.exVehFlow);
          if (val.exVehFlow > max[0]) max[0] = val.exVehFlow;
          if (val.exVehFlow < min[0]) min[0] = val.exVehFlow;

          line_double_data[1].push(Math.round(val.tradeTur / 100).toLocaleString());
          val.tradeTur = Math.round(val.tradeTur / 1000000);
          tradeTur.push(val.tradeTur);
          if (val.tradeTur > max[1]) max[1] = val.tradeTur;
          if (val.tradeTur < min[1]) min[1] = val.tradeTur;
        });

        var opt = option['line-double'];
        opt.xAxis[0].data = day;
        opt.yAxis[0].min = Math.floor(min[0] * .8);
        opt.yAxis[0].max = Math.ceil(max[0] * 1.2);
        opt.yAxis[1].min = Math.floor(min[1] * .8);
        opt.yAxis[1].max = Math.ceil(max[1] * 1.2);
        opt.series[0].data = exVehFlow;
        opt.series[1].data = tradeTur;
        opt.series[1].yAxisIndex = 1;
        chartArr[id].setOption(opt);
        break;
      case "vfSuccessRateTrendTask": //2.	门架成功率趋势
        var day = [];
        var card = [];
        var sign = [];
        var cpc = [];
        var min = 0;
        data.data.forEach(function (val) {
          val.day = val.day.replace(/-/g, "/");
          day.push(new Date(val.day).Format("MM-dd"));

          if (val.card > min) {
            min = val.card;
          }
          if (val.sign > min) {
            min = val.sign;
          }
          if (val.cpc > min) {
            min = val.cpc;
          }
          card.push(val.card);
          sign.push(val.sign);
          cpc.push(val.cpc);
        });

        console.log(min);
        var opt = option['lineArea'];
        opt.xAxis.data = day;
        if (min > 0) {
          opt.yAxis[0].min = Math.floor(min / 5) * 5;
        }
        opt.series[0].data = card;
        opt.series[1].data = sign;
        opt.series[2].data = cpc;
        chartArr[id].setOption(opt);
        break;
      case "frameSpecialCaseTask": //3.	门架特情分析
        var html = "";
        data.data.frameSpecialCaseList.forEach(function (val, index) {
          html += `<div class="flexItem flex align-center">
                                <div class="flexItem flex">
                                    <span class="text-blue">` + (index + 1) + `</span>
                                    <div class="flexItem text-white"><div>` + val.scType + `&nbsp;` + val.scName + `</div></div>
                                </div>
                                <div class="text-blue">` + val.scNum + `</div>
                            </div>`;
        });
        document.getElementById("list").innerHTML = html;
        scroll(); //文字滚动

        var r = data.data.rate ? data.data.rate : 0;
        document.getElementById("frameSpecialCaseTask_rate").innerHTML = r;
        document.getElementById("frameSpecialCaseTask_bar").style = "width:" + r + "%";


        // var opt = option['radar'];
        // opt.title.text = '{a|特情流水占比}\n'+ r +'%';
        // opt.series[0].data[0].value = 100 - r;
        // opt.series[0].data[1].value = r;
        // chartArr[id].setOption(opt);
        break;
      case "mapTask": //地图
        for (k in data.data) {
          if (k == "mapStatusList") {
            var first = mapInfo.data.length == 0 ? true : false;
            if (!first) {
              //比较并更新站点状态
              data.data[k].forEach((item, index) => {
                if (mapInfo.data[index].status != item.status) {
                  if (item.status) {
                    $("#" + item.stationId).removeClass("error");
                  } else {
                    $("#" + item.stationId).addClass("error");
                  }
                }
              });
            }
            mapInfo.data = data.data[k];

            //首次打开
            if (first) {
              //错误站点标红
              data.data[k].forEach((item, index) => {
                if (!item.status) $("#" + item.stationId).addClass("error");
              })
              $(".info").show();
              playList();
            }
          } else {
            document.getElementById("mapTask_" + k).innerHTML = data.data[k];
          }
        }
        break;
      case "frameUpload": //门架流水上传情况
        for (k in data.data) {
          if (k == "toPlatTimelyRate") {
            var opt = option['pie'];
            var r = data.data.toPlatTimelyRate;
            opt.title.text = text = '{a|及时率}\n' + r + '%',
              opt.series[0].data[0].value = r;
            opt.series[0].data[1].value = 100 - r;
            chartArr[id].setOption(opt);
          } else {
            if (k == "toPlatAvgTime") data.data[k] = Math.round(data.data[k] / 1000) + "秒";
            document.getElementById("frameUpload_" + k).innerHTML = data.data[k];
          }
        }
        break;
      case "warningInfoTask": //报警
        for (k in data.data) {
          if (k == "warningInfoList") {
            var warningInfo = ["未处理", '手动关闭'];
            var html = "";
            data.data[k].forEach((item, index) => {
              html += `<tr>
										<td><div>` + item.stationName + `</div></td>
										<td><div>` + item.startTime.substring(5, item.startTime.length - 3) + `</div></td>
										<td><div>` + item.reason + `</div></td>
										<td><div>` + warningInfo[item.status - 0] + `</div></td>
									</tr>`;
            });
            $("#tableId tbody").html(html);
            //						tableScroll('tableId', 90, 100, 10);
            tableScroll('tableId', 126, 5000, 10);
          } else {
            document.getElementById("warningInfoTask_" + k).innerHTML = data.data[k];
          }
        }
        break;
      default:
        // statements_def
        break;
    }
  }
}

// Date格式化
Date.prototype.Format = function (fmt) {
  if (!fmt) fmt = "<font>yyyy</font> 年 <font>MM</font>月<font>dd</font>日 hh:mm:ss";
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

var MyMarhq;

function tableScroll(tableid, hei, speed, len) {
  clearTimeout(MyMarhq);
  $('#' + tableid).parent().find('.tableid_').remove();

  //少于3个时不滚动
  if ($('#' + tableid).find('tbody tr').length < 3) {
    $('#' + tableid).removeAttr("style");
    return false;
  }
  $('#' + tableid).parent().prepend(
    '<table class="tableid_"><thead>' + $('#' + tableid + ' thead').html() + '</thead></table>'
  ).css({
    'position': 'relative',
    'overflow': 'hidden',
    'height': hei + 'px'
  })
  $(".tableid_").find('th').each(function (i) {
    $(this).css('width', $('#' + tableid).find('th:eq(' + i + ')').width());
  });
  $(".tableid_").css({
    'position': 'absolute',
    'top': 0,
    'left': 0,
    'z-index': 9
  })
  $('#' + tableid).css({
    'position': 'absolute',
    'top': 0,
    'left': 0,
    'z-index': 1
  })

  if ($('#' + tableid).find('tbody tr').length > len) {
    $('#' + tableid).find('tbody').html($('#' + tableid).find('tbody').html() + $('#' + tableid).find('tbody').html());
    $(".tableid_").css('top', 0);
    $('#' + tableid).css('top', 0);
    var tblTop = 0;
    var outerHeight = $('#' + tableid).find('tbody').find("tr").outerHeight();

    function Marqueehq() {
      let hh = $('#' + tableid + ' tr:first-of-type').height();
      console.log(hh);
      if (tblTop <= -outerHeight * $('#' + tableid).find('tbody').find("tr").length) {
        tblTop = 0;
      } else {
        tblTop -= hh; //根据一组的高度(hh),此处可以修改一次切换几组
        console.log(tblTop);
      }
      $('#' + tableid).css('margin-top', tblTop + 'px');
      clearTimeout(MyMarhq);
      MyMarhq = setTimeout(function () {
        Marqueehq()
      }, speed);
    }

    MyMarhq = setTimeout(Marqueehq, speed);
    $('#' + tableid).find('tbody').hover(function () {
      clearTimeout(MyMarhq);
    }, function () {
      clearTimeout(MyMarhq);
      if ($('#' + tableid).find('tbody tr').length > len) {
        MyMarhq = setTimeout(Marqueehq, speed);
      }
    })
  }

}

function update(api) {
  var data, cb;
  if (api == "getOutFeeStat") {
    data = {
      transDate: new Date().Format("yyyy-MM-dd")
    }
    cb = function (res) {
      $("#outFeeCount").html(res.outFeeCount);
      var opt = option['liquidfill'];
      opt.title.text = '兜底占比\n{a|' + res.outFeeProportion + '}';
      var r = res.outFeeProportion.replace("%", "") / 100;
      opt.series[0].data[0] = r;
      chartArr['liquidfill'].setOption(opt);
    };
  } else {
    data = {
      startDay: getBeforeDate(14),
      endDay: new Date().Format("yyyy-MM-dd"),
    }
    cb = function (res) {
      lineBar_data = [
        [],
        []
      ];

      var opt = option['lineBar'];
      var day = [];
      var auditFee = [];
      var tollFee = [];
      res.forEach(function (val) {
        val.transDay = val.transDay.replace(/-/g, "/");
        day.push(new Date(val.transDay).Format("MM-dd"));

        lineBar_data[0].push(Math.round(val.auditFee / 100).toLocaleString());
        val.auditFee = Math.round(val.auditFee / 1000000);
        auditFee.push(val.auditFee);

        lineBar_data[1].push(Math.round(val.tollFee / 100).toLocaleString());
        val.tollFee = Math.round(val.tollFee / 1000000);
        tollFee.push(val.tollFee);
      });
      opt.xAxis.data = day;
      opt.series[0].data = auditFee;
      opt.series[1].data = tollFee;
      chartArr["lineBar"].setOption(opt);
    };
  }
  console.log(data);
  postData(root4 + api, data, cb)
}

function postData(url, data, callback) {
  $.ajax({
    headers: {'token': token},
    type: 'post',
    url: url,
    data: data,
    timeout: 30000, //超时时间：30秒
    error: function (XMLHttpRequest, textStatus, errorThrown) {
      console.log(errorThrown);
    },
    success: function (e) {
      console.log(e);
      if (e.code === "00000" || e.code === "success") {
        if (typeof (callback) == 'function') {
          callback(e.data);
        }
      }
    }
  });
}

function getData(url, data, callback, key) {
  $.ajax({
    headers: {'token': token},
    type: 'get',
    url: root4 + url + (key ? key : ''),
    data: data,
    timeout: 30000, //超时时间：30秒
    error: function (XMLHttpRequest, textStatus, errorThrown) {
      console.log(errorThrown);
    },
    success: function (e) {
      console.log(url, e);
      if (e.code === "00000" || e.code === "success" || e.code == "200") {
        if (typeof (callback) == 'function') {
          callback(e.data);
        }
      }
    }
  });
}


// 过车交易统计
function getData1() {
  getData('largescreen/vehiclecount/', {
    time_dimension: time_dimension,
  }, function (res) {

    console.log('data1', res);
    $("#plateNum1").html(res.vehicle_total ? res.vehicle_total : 0);
    $("#plateNum2").html(res.vehicle_gas_station_ratio ? res.vehicle_gas_station_ratio : 0);
    $("#plateNum3").html(res.vehicle_parking_lot_ratio ? res.vehicle_parking_lot_ratio : 0);
    $("#plateNum4").html(res.amount_total ? res.amount_total : 0);
    $("#plateNum5").html(res.amount_gas_station_ratio ? res.amount_gas_station_ratio : 0);
    $("#plateNum6").html(res.amount_parking_lot_ratio ? res.amount_parking_lot_ratio : 0);

  }, time_dimension)
}

// 2.2.最近交易流量金额统计
function getData2() {
  getData('largescreen/recentcount', '', function (res) {
    console.log(res);
    var XData = [];
    var data1 = [];
    var data2 = [];
    var cpc = [];
    var min = 0;
    res.recent_amount.forEach(function (val) {
      val.month = val.month.replace(/-/g, "/");
      XData.push(val.month);
      data1.push(val.amount);
    });
    res.recent_vehicle.forEach(function (val) {
      data2.push(val.count);
    });

    var opt = option['lineBar'];
    opt.xAxis.data = XData;
    if (min > 0) {
      opt.yAxis[0].min = Math.floor(min / 5) * 5;
    }
    opt.series[1].data = data1;
    opt.series[0].data = data2;
    chartArr['vfSuccessRateTrendTask'].setOption(opt);
  })
}

// 2.3.拓展场所列表查询
function getData3() {
  getData('largescreen/placelist/', {
    service_type: service_type,
  }, function (res) {
    map.clearMap();
    markers = res;
    markers.forEach(function (marker) {
      new AMap.Marker({
        map: map,
        // icon: marker.icon,
        content: getContent(marker),
        position: [Number(marker.lng), Number(marker.lat)],
        offset: new AMap.Pixel(-13, -30)
      });
    });
    map.add(markers);
    map.setFitView();
  }, service_type)
}

// 2.4.异常查询和统计
function getData4() {
  getData('largescreen/alarmcount', '', function (res) {
    console.log(res);
    $(".errorNum").html(res.serious_alarm_count ? res.serious_alarm_count : 0)
    $(".normalNum").html(res.general_alarm_count ? res.general_alarm_count : 0)
    $("#backlist1").html(res.full_card_blacklist_version ? res.full_card_blacklist_version : "暂无数据")
    $("#backlist2").html(res.incre_card_blacklist_version ? res.incre_card_blacklist_version : "暂无数据")
    $("#backlist3").html(res.full_obu_blacklist_version ? res.full_obu_blacklist_version : "暂无数据")
    $("#backlist4").html(res.incre_obu_blacklist_version ? res.incre_obu_blacklist_version : "暂无数据")
  })
}

// 2.5.今日交易额排行查询
function getData5() {
  getData('largescreen/todayrank', '', function (res) {
    console.log(res);
    var ht = '';
    var ht1 = '';
    if (res.parking_lot_rank.length == 0) {
      // for (var i = 0; i < 5; i++) {
      //   ht += '<tr>\
      //           <td>' + i + '</td>\
      //           <td>' + '停车场'+ i + '</td>\
      //           <td>' + 0 + '</td>\
      //       </tr>';
      // }
      ht += '<tr><td colspan="3">暂无数据</td></tr>'
    } else {
      for (var i = 0; i < res.parking_lot_rank.length; i++) {
        ht += '<tr>\
                <td>' + i + '</td>\
                <td>' + res.parking_lot_rank[i].name + '</td>\
                <td>' + res.parking_lot_rank[i].amount + '</td>\
            </tr>';
      }
    }
    if (res.gas_station_rank.length == 0) {
      // for (var i = 0; i < 5; i++) {
      //   ht1 += '<tr>\
      //           <td>' + i + '</td>\
      //           <td>' + '加油站'+ i + '</td>\
      //           <td>' + 0 + '</td>\
      //       </tr>';
      // }
      ht1 += '<tr><td colspan="3">暂无数据</td></tr>'
    } else {
      for (var i = 0; i < res.gas_station_rank.length; i++) {
        ht1 += '<tr>\
                <td>' + i + '</td>\
                <td>' + res.gas_station_rank[i].name + '</td>\
                <td>' + res.gas_station_rank[i].amount + '</td>\
            </tr>';
      }
    }
    $("#parking_lot_rank").empty().append(ht);
    $("#gas_station_rank").empty().append(ht1);
  })
}

function getBeforeDate(number) {
  const date = new Date();
  date.setDate(date.getDate() - number);
  return date.Format("yyyy-MM-dd");
}
