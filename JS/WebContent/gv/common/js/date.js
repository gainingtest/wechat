//初始化日期控件
var now = new Date();
var currYear = now.getFullYear();
var currMonth = now.getMonth() + 1;
var currDay = now.getDate();
var opt1 = {
  preset: 'date',
  theme: 'android-ics light',
  display: 'modal', //显示方式 ，可选：modal\inline\bubble\top\bottom
  mode: 'scroller', //日期选择模式，可选：scroller\clickpick\mixed
  lang: 'zh',
  dateFormat: 'yyyy-mm-dd', // 日期格式
  setText: '确定', //确认按钮名称
  cancelText: '取消', //取消按钮名籍我
  dateOrder: 'yyyymmdd', //面板中日期排列格式
  dayText: '日',
  monthText: '月',
  yearText: '年', //面板中年月日文字
  showNow: false,
  nowText: "今",
  startYear: currYear - 50, //开始年份
  //  endYear: currYear + 100, //结束年份
  onSelect: fn1, //点击确定按钮，触发事件。
  maxDate:new Date()
};
$("#dateStart").mobiscroll(opt1);
function getZero(num){return num < 10 ? '0'+num : num;}
$("#dateStart").val(currYear+'-'+getZero(currMonth-1)+'-'+getZero(currDay));
$("#dateStart").scroller('setDate',new Date(currYear,currMonth-2,currDay),true);

//结束时间
var opt2 = {
  preset: 'date',
  theme: 'android-ics light',
  display: 'modal', //显示方式 ，可选：modal\inline\bubble\top\bottom
  mode: 'scroller', //日期选择模式，可选：scroller\clickpick\mixed
  lang: 'zh',
  dateFormat: 'yyyy-mm-dd', // 日期格式
  hourText: '时' ,
  setText: '确定', //确认按钮名称
  cancelText: '取消', //取消按钮名籍我
  dateOrder: 'yyyymmdd', //面板中日期排列格式
  dayText: '日',
  monthText: '月',
  yearText: '年', //面板中年月日文字
  showNow: false,
  nowText: "今",
  onSelect: fn1, //点击确定按钮，触发事件。
  minDate:new Date(currYear,currMonth-2,currDay),
  maxDate:new Date()
};
$("#dateEnd").mobiscroll(opt2);
$("#dateEnd").val(currYear+'-'+getZero(currMonth)+'-'+getZero(currDay));
$("#dateEnd").scroller('setDate',new Date(),true);


function fn1() {
  var startDate = $("#dateStart").val();
  var endDate = $("#dateEnd").val();
  if(startDate == "" && endDate == ""){
    return;
  }else if(startDate != "" && endDate == ""){
    $('#dateEnd').mobiscroll('option', {
      minDate: new Date(startDate.substr(0,4),parseInt(startDate.substr(5,2)) - 1,startDate.substr(8,2))
    });
  }else if(endDate != "" && startDate == ""){
    $('#dateStart').mobiscroll('option', {
      maxDate: new Date(endDate.substr(0,4),parseInt(endDate.substr(5,2)) - 1,endDate.substr(8,2))
    })
  }else{
    var bOff = fn2(startDate,endDate);
    if(!bOff){
      $('#dateEnd').mobiscroll('option', {
        minDate: new Date(startDate.substr(0,4),parseInt(startDate.substr(5,2)) - 1,startDate.substr(8,2))
      });
    }else{
      $('#dateStart').mobiscroll('option', {
        maxDate: new Date(endDate.substr(0,4),parseInt(endDate.substr(5,2)) - 1,endDate.substr(8,2))
      });
      $('#dateEnd').mobiscroll('option', {
        minDate: new Date(startDate.substr(0,4),parseInt(startDate.substr(5,2)) - 1,startDate.substr(8,2))
      });
    }
  }
}
function fn2(start, end) {
  var arr = start.split("-");
  var starttime = new Date(arr[0], arr[1], arr[2]);
  var starttimes = starttime.getTime();

  var arrs = end.split("-");
  var lktime = new Date(arrs[0], arrs[1], arrs[2]);
  var lktimes = lktime.getTime();
  if (starttimes > lktimes) {
    return false;
  }else{
    return true;
  }
}
