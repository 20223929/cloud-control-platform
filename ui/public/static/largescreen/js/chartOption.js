var line_double_data = [
    [],
    []
];
var lineBar_data = [
    [],
    []
];
var option = {
    "bar": {
        tooltip: {
            trigger: 'axis',
            axisPointer: { // 坐标轴指示器，坐标轴触发有效
                type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
            },
            formatter: function(params) {
                var tar;
                if (params[1].value !== '-') {
                    tar = params[1];
                } else {
                    tar = params[0];
                }
                return tar.name + '<br/>' + tar.seriesName + ' : ' + tar.value;
            }
        },
        grid: {
            top: '12%',
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            boundaryGap: true,
            splitLine: {
                show: true,
                lineStyle: {
                    type: 'dashed',
                    color: '#142f60'
                }
            },
            axisLabel: { textStyle: { color: "#fff", fontSize: 8 } },
            axisTick: { show: false },
            axisLine: { show: true, lineStyle: { color: '#1d4882' } },
            data: function() {
                var list = [];
                for (var i = 1; i <= 12; i++) {
                    list.push(i + '月');
                }
                return list;
            }()
        },
        yAxis: {
            splitLine: {
                lineStyle: { color: '#1c406d' }
            },
            axisLabel: { textStyle: { color: "#fff" } },
            axisLine: { lineStyle: { color: '#1d4882' } },
            axisTick: { show: false },
            type: 'value'
        },
        series: [{
                name: '总金额',
                type: 'bar',
                stack: '总量',
                itemStyle: {
                    barBorderColor: 'rgba(0,0,0,0)',
                    color: 'rgba(0,0,0,0)'
                },
                emphasis: {
                    itemStyle: {
                        barBorderColor: 'rgba(0,0,0,0)',
                        color: 'rgba(0,0,0,0)'
                    }
                },
                data: [0, 10, 20, 30, 40, 50, 50, 60, 80, 100, 140, 170]
            },
            {
                name: '出口流量金额',
                type: 'bar',
                stack: '总量',
                label: {
                    show: true,
                    position: 'top',
                    color: '#fff'
                },
                barWidth: '10px',
                itemStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: 'rgba(51,129,255,1)' // 0% 处的颜色
                        }, {
                            offset: 1,
                            color: 'rgba(12,150,255,0)' // 100% 处的颜色
                        }], false),
                    }
                },
                data: [10, 10, 10, 10, 10, 0, 10, 20, 20, 20, 40, 30]
            }
        ]
    },
    "lineBar": {
        tooltip: {
            confine: true,
            trigger: 'axis',
            axisPointer: { type: 'line' },
            backgroundColor: "rgba(48,83,175,.5)",
            formatter: function(params) {
                console.log(params);
                return '<span style="display:inline-block;background-color:#e9ba42;border-radius:50%;width:8px;height:8px;margin-right:5px"></span>' + params[0].seriesName + ': ' + params[0].data +
                    '辆<br /><span style="display:inline-block;background-color:#0088ff;border-radius:50%;width:8px;height:8px;margin-right:5px"></span>' + params[1].seriesName + ': ' + params[1].data + '万元';
            },
        },
        legend: {
            show: true,
            textStyle: {
                color: '#fff'
            },
            itemGap: 20,
            left: '30',
            top: '10'
        },
        grid: {
            top: '12%',
            left: '3%',
            right: '3%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            boundaryGap: true,
            splitLine: { show: false },
            axisLabel: { textStyle: { color: "#fff", fontSize: 8 } },
            axisTick: { show: false },
            axisLine: { show: true, lineStyle: { color: '#1d4882' } },
            data: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15"]
        },
        yAxis: [{
            type: 'value',
            splitLine: {
                lineStyle: {
                    color: '#78c5ff'
                }
            },
            axisLabel: { textStyle: { color: "#fff" } },
            axisLine: { lineStyle: { color: '#1d4882' }, show: false },
            axisTick: { show: false },
        }, ],
        series: [{
            name: "交易量",
            type: 'line',
            symbolSize: 2,
            lineStyle: { width: 2 },
            color: "#ffb32f",
            data: [10, 10, 60, 10, 10, 15, 10, 18, 10, 10, 10, 10]
        }, {
            name: "交易金额(万元)",
            type: 'line',
            symbolSize: 2,
            lineStyle: { width: 2 },
            color: "#0088fe",
            data: [50, 50, 10, 50, 50, 50, 70, 50, 50, 60, 50, 50]
        }, ]
    },
    "pie": {
        title: {
            text: '{a|及时率}\n0%',
            x: 'center',
            top: 'center',
            textStyle: {
                color: '#fff',
                fontSize: 18,
                rich: { a: { fontSize: 13, lineHeight: 30 } }
            }
        },
        color: ['#717071'],
        series: [{
            type: 'pie',
            clockWise: true,
            radius: ['72%', '90%'],
            // roseType: true,
            itemStyle: {
                normal: {
                    label: { show: false },
                    labelLine: { show: false },
                    shadowBlur: 40,
                    borderWidth: 10,
                    shadowColor: 'rgba(0, 0, 0, 0)' //边框阴影
                }
            },
            hoverAnimation: false,
            data: [{
                value: 0,
                itemStyle: {
                    color: new echarts.graphic.LinearGradient(1, 0, 0, 0, [{
                        offset: 0,
                        color: '#97C157'
                    }, {
                        offset: 1,
                        color: '#00D8EC'
                    }]),
                }
            }, {
                value: 100,
                itemStyle: { color: '#1B2E70' }
            }]
        }]
    },
    "radar": {
        tooltip: {
            trigger: 'item'
        },
        radar: {
            radius: '90%',
            startAngle: 0,
            name: {
                textStyle: {
                    color: '#fff',
                    fontSize: 14
                },
            },
            splitArea: {
                areaStyle: {
                    color: ['transparent', 'transparent']
                }

            },
            axisLine: {
                lineStyle: {
                    color: '#1d3656'
                }
            },
            splitLine: {
                lineStyle: {
                    color: ['#4d7d93', '#2e5573', '#1e4062']
                }
            },
            indicator: [{
                    name: '用户卡交易\n成功率',
                    max: 100
                },
                {
                    name: '标签交易成功率',
                    max: 100
                },
                {
                    name: '数据传输完整率',
                    max: 100
                },
                {
                    name: 'CPC车牌识别\n准确率',
                    max: 100
                },
                {
                    name: 'ETC车牌识别准确率',
                    max: 100
                },
                {
                    name: 'CPC交易成功率',
                    max: 100
                }
            ]
        },
        series: [{
            type: 'radar',
            symbolSize: 6,
            itemStyle: { borderWidth: 0 },
            lineStyle: {
                normal: {
                    color: "#01eaf0",
                    opacity: .5
                }
            },
            areaStyle: {
                normal: {
                    color: {
                        type: 'linear',
                        x: 0, //右
                        y: 0, //下
                        x2: 1, //左
                        y2: 1, //上
                        colorStops: [{
                            offset: 0,
                            color: '#4a97ff'
                        }, {
                            offset: 1,
                            color: '#00fdff'
                        }],
                        globalCoord: false
                    },
                    opacity: .5
                },
            },
            data: [{
                value: [50, 70, 90, 50, 64, 74],
            }]
        }]
    },
    "gauge": {
        series: [{
            //name: item.name,
            type: 'gauge',
            radius: '78.10%',
            startAngle: 225,
            endAngle: -45,
            min: 0,
            max: 100,
            axisLine: {
                show: true,
                lineStyle: {
                    width: 6,
                    shadowBlur: 0,
                    color: [
                        [0, 'transparent'],
                        [0.1, '#ff004e'],
                        [0.108, 'transparent'],
                        [0.2, '#ff004e'],
                        [0.208, 'transparent'],
                        [0.3, '#0066ff'],
                        [0.308, 'transparent'],
                        [0.4, '#0066ff'],
                        [0.408, 'transparent'],
                        [0.5, '#0066ff'],
                        [0.508, 'transparent'],
                        [0.6, '#0066ff'],
                        [0.608, 'transparent'],
                        [0.7, '#0066ff'],
                        [0.708, 'transparent'],
                        [0.8, '#0066ff'],
                        [0.808, 'transparent'],
                        [0.9, '#0066ff'],
                        [0.908, 'transparent'],
                        [1, '#0066ff']
                    ],
                }
            },
            axisTick: { show: false, },
            splitLine: { show: false, },
            axisLabel: { show: false, },
            pointer: { show: false, },
            detail: { show: false, },
            data: [{
                show: false,
            }]
        }, {
            //name: item.name,
            type: 'gauge',
            radius: '82%',
            startAngle: 225,
            endAngle: -45,
            min: 0,
            max: 100,
            axisLine: {
                show: true,
                lineStyle: {
                    width: 100,
                    color: [
                        [
                            60 / 100, new echarts.graphic.LinearGradient(
                                0, 1, 0, 0, [{
                                        offset: 0,
                                        color: 'rgba(69, 161, 255,0)',
                                    }, {
                                        offset: 0.3,
                                        color: 'rgba(69, 161, 255,0)',
                                    },
                                    {
                                        offset: 1,
                                        color: 'rgba(69, 161, 255,0.7)',
                                    }
                                ]
                            )
                        ],
                        [
                            1, 'rgba(28,128,245,.0)'
                        ]
                    ],
                }
            },
            axisTick: { show: false, },
            splitLine: { show: false, },
            axisLabel: { show: false, },
            pointer: {
                show: true,
                length: '60%',
                width: 3
            },
            itemStyle: {
                color: '#fff',
                borderColor: 'rgba(28, 128, 245,1)',
            },
            detail: {
                show: false
            },
            data: [{ value: 60 }]
        }, {
            type: 'gauge',
            radius: '70%',
            splitNumber: 10,
            min: 0,
            max: 100,
            startAngle: 225,
            endAngle: -45,
            axisTick: { splitNumber: 1 },
            axisLabel: { color: "#fff", fontSize: 8 },
            axisLine: {
                show: false,
                lineStyle: { width: 0, }
            },

            splitLine: {
                show: true,
                length: 8,
                color: "#90D1FE"

            },
            pointer: { show: 0 },
            detail: { show: 0 }
        }, {
            type: 'gauge',
            radius: '82%',
            splitNumber: 10,
            splitLine: {
                show: false
            },
            min: 0,
            max: 100,
            startAngle: 225,
            endAngle: -45,
            axisLabel: { show: false },
            axisLine: {
                show: true,
                lineStyle: {
                    width: 1,
                    shadowBlur: 0,
                    color: [
                        [0.05, '#0072ff'],
                        [0.10, '#0072ff'],
                        [0.15, '#0072ff'],
                        [0.20, '#0072ff'],
                        [0.25, '#0072ff'],
                        [0.30, '#0072ff'],
                        [0.35, '#0072ff'],
                        [0.40, '#0072ff'],
                        [0.45, '#0072ff'],
                        [0.50, '#0072ff'],
                        [0.55, '#0072ff'],
                        [0.60, '#0072ff'],
                        [0.65, '#0072ff'],
                        [0.70, '#0072ff'],
                        [0.75, '#0072ff'],
                        [0.80, '#0072ff'],
                        [0.85, '#0072ff'],
                        [0.90, '#0072ff'],
                        [0.95, '#0072ff'],
                        [1, '#0072ff']
                    ],
                }
            },
            pointer: {
                show: 0
            },
            axisTick: {
                show: false
            },
            detail: {
                show: false
            }

        }, {
            type: 'gauge',
            radius: '96%',
            splitNumber: 10,
            splitLine: { show: false },
            min: 0,
            max: 100,
            startAngle: 225,
            endAngle: -45,
            axisLabel: { show: false },
            axisLine: {
                show: true,
                lineStyle: {
                    width: 2,
                    shadowBlur: 0,
                    color: [
                        [0.05, 'rgba(0,27,255,1)'],
                        [0.10, 'rgba(0,27,255,1)'],
                        [0.15, 'rgba(0,27,255,1)'],
                        [0.20, 'rgba(0,27,255,1)'],
                        [0.25, 'rgba(0,27,255,.7)'],
                        [0.30, 'rgba(0,27,255,.6)'],
                        [0.35, 'rgba(0,27,255,.5)'],
                        [0.40, 'rgba(0,27,255,.3)'],
                        [0.45, 'rgba(0,27,255,.2)'],
                        [0.50, 'rgba(0,27,255,.1)'],
                        [0.55, 'rgba(0,27,255,.1)'],
                        [0.60, 'rgba(0,27,255,.2)'],
                        [0.65, 'rgba(0,27,255,.3)'],
                        [0.70, 'rgba(0,27,255,.5)'],
                        [0.75, 'rgba(0,27,255,.6)'],
                        [0.80, 'rgba(0,27,255,.7)'],
                        [0.85, 'rgba(0,27,255,1)'],
                        [0.90, 'rgba(0,27,255,1)'],
                        [0.95, 'rgba(0,27,255,1)'],
                        [1, 'rgba(0,27,255,1)']
                    ],

                }

            },
            pointer: {
                show: 0
            },
            axisTick: {
                show: false
            },
            detail: {
                show: false
            }

        }]
    }
};