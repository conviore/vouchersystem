<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%  
	String path = request.getContextPath();  
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
	System.out.println(basePath);
	String companyId=(String)application.getAttribute("companyId");
	String companyName=(String)application.getAttribute("companyName");
	String settleTime =request.getParameter("settleTime");
%> 
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/004/js/jquery-easyui-1.4/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="/004/js/jquery-easyui-1.4/themes/icon.css">
   <link rel="stylesheet" type="text/css" href="/004/js/demo.css">
	<script type="text/javascript" src="/004/js/jquery-easyui-1.4/jquery.min.js"></script>
	<script type="text/javascript" src="/004/js/jquery-easyui-1.4/jquery.easyui.min.js"></script>
<title><%=companyName %> 记账工具</title>
</head>
<body>   <div style="margin:20px 0;"></div>
    <div class="easyui-panel" style="padding:5px;">
        <a href="<%=basePath %>" class="easyui-linkbutton" data-options="plain:true">首页</a>
        <a href="<%=basePath %>pages/monsettleprog/monSettleProgList.jsp" class="easyui-menubutton" data-options="menu:'#mm1',iconCls:'icon-edit'">记账动作</a>
        <a href="#" class="easyui-menubutton" data-options="menu:'#mm2',iconCls:'icon-help'">科目管理</a>
        <a href="<%=basePath %>/loginCompany.html" class="easyui-linkbutton" data-options="plain:true">重选公司名</a>
    </div>
    <div id="mm1" style="width:150px;">
        
        <div data-options="iconCls:'icon-redo'"><a href="<%=basePath %>pages/monsettleprog/monSettleProgList.jsp" class="easyui-menubutton" data-options="menu:'#mm1',iconCls:'icon-edit'">记账状态</a></div>
        <div data-options="iconCls:'icon-redo'"><a href="<%=basePath %>pages/mondetail/mondetailList.jsp" class="easyui-menubutton" data-options="menu:'#mm1',iconCls:'icon-edit'">科目明细</a></div>
    </div>
    <div id="mm2" style="width:100px;"> <a href="<%=basePath %>pages/tree/mainframe.jsp"  >科目管理</a>
    <div data-options="iconCls:'icon-undo'" > <a href="<%=basePath %>pages/company/uploadfile.jsp"  >初始化科目数据</a></div>
    </div>	
<h2>科目明细</h2>
    <p><%=companyName %>公司科目明细汇总</p>
    <div id="divSearch" class="easyui-panel" style="width: 600px; height: 200px; padding: 10px;"
          data-options="title:'查询条件',iconCls:'icon-save',
collapsible:true,minimizable:true,maximizable:true,closable:true">
        <table>
            <tr>
                <td>
                    科目
                </td>
                <td>
                    <div style="margin:20px 0"></div>
	<input class="easyui-combobox" id="subjectComboBox" name="subjectComboBox" data-options="
				url: '<%=basePath%>mondetail/<%=basePath%>mondetail/getSubjectComboxData.action',
				method: 'get',
				valueField: 'id',
				textField: 'code',
				panelWidth: 350,
				panelHeight: 'auto',
				formatter: formatCombox
			">
                </td>
                <td>
                    记账周期
                </td>
                <td>
                	<div style="margin:20px 0"></div>
                    <input id="settleDate" name="settleDate" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser"></input>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="submit" value="查询" onclick="search()">
                </td>
                 <td>
                    <a id="aDownload" href="" class="easyui-linkbutton" onclick="downloadExcel()">下载excel</a>
                </td>
            </tr>
        </table>
    </div>
    <div style="margin:20px 0;">
        <a href="" onclick="">下载excel</a>
    </div>
    <table id="dg" class="easyui-datagrid" title="科目明细列表" style="width:600px;height:768px"
            data-options="singleSelect:true,url:'',method:'get'">
        <thead>
            <tr>
                <th data-options="field:'id',width:80" hidden=true>voucherID</th>
                <th data-options="field:'settleTime',width:100">核对时间</th>
                <th data-options="field:'voucherNumber',width:80,align:'right'">凭证号</th>
                <th data-options="field:'comment',width:80,align:'right'">注释</th>
                <th data-options="field:'debit',width:80,align:'right'">借方</th>
                <th data-options="field:'credit',width:80,align:'right'">贷方</th>
                <th data-options="field:'difference',width:80,align:'right'">余额</th>
                <th data-options="field:'settleWay',width:80,align:'right'">记账方向</th>
            </tr>
        </thead>
    </table>
    <div style="margin:10px 0;">
        <span>Selection Mode: </span>
        <select onchange="$('#dg').datagrid({singleSelect:(this.value==0)})">
            <option value="0">Single Row</option>
            <option value="1">Multiple Rows</option>
        </select>
    </div>
 <script type="text/javascript">
 var url;
        function getSelected(){
            var row = $('#dg').datagrid('getSelected');
            if (row){
                $.messager.alert('Info', row.itemid+":"+row.productid+":"+row.attr1);
            }
        }
        function getSelections(){
            var ss = [];
            var rows = $('#dg').datagrid('getSelections');
            for(var i=0; i<rows.length; i++){
                var row = rows[i];
                ss.push('<span>'+row.itemid+":"+row.productid+":"+row.attr1+'</span>');
            }
            $.messager.alert('Info', ss.join('<br/>'));
        }
        function search() {
            var settleDateF=$('#settleDate').datebox('getValue');
//             alert(settleDateF);
            var ss = (settleDateF.split('-'));
            var y = ss[0];
            var m = ss[1];
            var settleDate=y+""+m;
            alert(settleDate);
// 			var settleDate=$('#settleDate').datebox('getValue');
			var subjectCombolVaule=$('#subjectComboBox').combobox('getValue');
			alert(subjectCombolVaule);
//             alert(settleDate);
            var handler = "<%=basePath%>mondetail/getMonDetailList.action?subjectId=" + subjectCombolVaule+"&&settleTime="+settleDate+"&&settleDate="+settleDate;
            alert(handler);
            $('#dg').datagrid('options').url = handler;
            $('#dg').datagrid('reload'); 
        }
        function downloadExcel(){
         var settleDateF=$('#settleDate').datebox('getValue');
//          alert(settleDateF);
         var ss = (settleDateF.split('-'));
         var y = ss[0];
         var m = ss[1];
         var settleDate=y+""+m;
//			var settleDate=$('#settleDate').datebox('getValue');
			var subjectCombolVaule=$('#subjectComboBox').combobox('getValue');
            var handler = "<%=basePath%>mondetail/getMonthDetailExcel.action?subjectId=" + subjectCombolVaule+"&&settleTime="+settleDate;
            $("#aDownload").attr("href",handler);
            document.getElementById("aDownload").click();
        }
        
        function formatCombox(row){
			var s = '<span style="font-weight:bold">' + row.code + '</span><br/>' +
					'<span style="color:#888">' + row.comment + '</span>';
			return s;
		}
        function myformatter(date){
            var y = date.getFullYear();
            var m = date.getMonth()+1;
//             alert(m);
            var d = date.getDate();
            return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
//             return y+""+(m<10?('0'+m):m);
        }
        
        function myparser(s){
          if (!s) return new Date();
          var ss = (s.split('-'));
          var y = parseInt(ss[0],10);
          var m = parseInt(ss[1],10);
          var d = parseInt(ss[2],10);
          if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
              return new Date(y,m-1,d);
					
          } else {
              return new Date();
          }
//          if (!s){
//         	 return new Date();
					
//          } else {
//         	 var y=s.substring(0,2);
//         	 alert(y);
//         	 var m=s.substring(2,4);
//         	 return new Date(y,m,'31');
//          }
  }
    </script>
</body>
</html>