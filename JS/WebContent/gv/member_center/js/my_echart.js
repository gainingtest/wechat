window.onload = function(){
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));
    //图表显示提示信息
    myChart.showLoading({text:''});
    var app = {};option = null;
    var data1=[];//保存内圈值
    var data2=[];//保存外圈值
    // 指定图表的配置项和数据
    var itemStyle = {
        normal: {
            borderWidth: 1,
            borderColor: '#fff'
        }
    };
    option = {
        series: [
            {
                name:'访问来源',
                type:'pie',
                selectedOffset: 0,
                legendHoverLink:false,
                hoverAnimation:false,
                selectedMode: 'single',
                radius: [0, '40%'],

                label: {
                    normal: {
                        position: 'inner',
                        textStyle:{
                            fontSize:12
                        }
                    }
                },
                labelLine: {
                    normal: {
                        show: true,
                        textStyle:{
                            fontSize:12
                        }
                    },
                },
                data:data1,
                itemStyle: itemStyle,
            },
            {
                name:'访问来源',
                type:'pie',
                selectedOffset: 0,
                legendHoverLink:false,
                hoverAnimation:false,
                radius: ['50%', '80%'],

                label: {
                    normal: {
                        position: 'inner'
                    }
                },
                labelLine: {
                    normal: {
                        show: true,
                        textStyle:{
                            fontSize:12
                        }
                    },
                },
                data:data2,
                itemStyle: itemStyle,
            }
        ]
    };
    $.ajax({
        url:sUrl+"center/myGuarantee",
        success:function(data){
            if(data.result){
                var categoryValue = data.object;
                var color1 = '#c5c5c5';
                var len1 = categoryValue.length;
                if(len1 <= 0){
                    $('.wealth_pie_show').hide();
                    $('.pie_tips').show();
                    $('.default_btn').click(function(){
                        location.href = "http://zmt.ihxlife.com/zmt/home/goMalls.do";
                    });
                    return false;
                }else{
                    $('.wealth_pie_show').show();
                    $('.pie_tips').hide();
                }
                for(var i = 0 ; i < len1 ; i++){
                    var str1 = '';
                    var len2 = categoryValue[i].dto.length;
                    str1 = '{"value":"'+categoryValue[i].counts+'","name":"'+categoryValue[i].relationToAppntName+'","itemStyle":{'
                        +'"normal":{"color":"'+color1+'"}'
                        +'}}';
                    data1[data1.length] = JSON.parse(str1);
                    for(var j = 0 ; j < len2 ; j++){
                        var str2 = '';
                        var  color2 = '';
                        //通过险种名称判断不用颜色
                        switch (categoryValue[i].dto[j].riskTypeName){
                            case '终身':color2='#68a64d';
                                break;
                            /*年金 万能 分红*/
                            case '年金':color2='#f3a941';
                                break;
                            case '万能':color2='#f3a941';
                                break;
                            case '分红':color2='#f3a941';
                                break;
                            /* 重疾 健康 两全*/
                            case '重疾':color2='#d66443';
                                break;
                            case '健康':color2='#d66443';
                                break;
                            case '两全':color2='#d66443';
                                break;
                            /*定期 医疗*/
                            case '定期':color2='#8c5495';
                                break;
                            case '医疗':color2='#8c5495';
                                break;
                            case '意外':color2='#245e9a';
                                break;
                        }
                        str2 =  '{"value":"'+categoryValue[i].dto[j].count+'","name":"'+categoryValue[i].dto[j].riskTypeName+'","itemStyle":{'
                            +'"normal":{"color":"'+color2+'"}'
                            +'}}';
                        data2[data2.length] = JSON.parse(str2);
                    }
                };
                //隐藏提示信息
                myChart.hideLoading();
                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            }else{
                fnLayerBox(data.info);
                //调回个人中心首页
                $('.layer_btn').click(function(){
                    location.href=sUrl+'center/toIndex';
                })
            }
        }
    });
}



