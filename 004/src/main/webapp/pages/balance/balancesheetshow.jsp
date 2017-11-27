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
<title>Insert title here</title>
</head>
<body>
<div style="margin:20px 0;"></div>
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
<h2>科目汇总</h2>
    <p><%=companyName %>公司<%=settleTime %>科目余额汇总</p>
    <div style="margin:20px 0;">
        <a href="<%=basePath %>/balance/getSummaryReportExcel.action?settleTime=<%=settleTime%>" class="easyui-linkbutton" onclick="">下载excel</a>
    </div>
    <table id="dg" class="easyui-datagrid" title="记账进度列表" style="width:1024px;height:768px"
            data-options="singleSelect:true,url:'<%=basePath %>/balance/getSummaryReport.action?settleTime=<%=settleTime%>',method:'get'">
        <thead>
            <tr>
                <th data-options="field:'id',width:80" hidden=true>记账状态ID</th>
                <th data-options="field:'settleTime',width:80">核对时间</th>
                <th data-options="field:'subjectCode',width:80,align:'right'">科目编码</th>
                <th data-options="field:'subjectName',width:80,align:'right'">科目名</th>
                <th data-options="field:'initYearBalance',width:80,align:'right'">年初余额</th>
                <th data-options="field:'initBalance',width:80,align:'right'">月初余额</th>
                <th data-options="field:'initMonDebit',width:80,align:'right'" hidden="true">起初借方</th>
                <th data-options="field:'initMonCredit',width:80,align:'right'" hidden="true">起初贷方</th>
                <th data-options="field:'currentMonDebit',width:80,align:'right'">当月发生借方</th>
                <th data-options="field:'currentMonCredit',width:80,align:'right'">当月发生贷方</th>
                <th data-options="field:'cumulativeDebit',width:80,align:'right'">累计发生借方</th>
                <th data-options="field:'cumulativeCredit',width:80,align:'right'">累计发生贷方</th>
                <th data-options="field:'finalMonDebit',width:80,align:'right'" hidden="true">月终借方</th>
                <th data-options="field:'finalMonCredit',width:80,align:'right'" hidden="true">月终贷方</th>
                <th data-options="field:'endBalance',width:80,align:'right'">月末余额</th>
                <th data-options="field:'comment',width:60,align:'center'">评论</th>
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
        function getSelected(){
            var row = $('#dg').datagrid('getSelected');
            if (row){
                $.messager.alert('Info', row.itemid+":"+row.productid+":"+row.attr1);
            }
        }l;
        function getSelections(){
            var ss = [];
            var rows = $('#dg').datagrid('getSelections');
            for(var i=0; i<rows.length; i++){
                var row = rows[i];
                ss.push('<span>'+row.itemid+":"+row.productid+":"+row.attr1+'</span>');
            }
            $.messager.alert('Info', ss.join('<br/>'));
        }
        function inputSettles(){
        	var row = $('#dg').datagrid('getSelected');
        	var time=row.settleYear+row.settleMonth;
        	var formattime=row.settleYear+'-'+row.settleMonth;
        	var settleDay='30';
        	if(row.settleMonth==2){
        		settleDay='28';
        	}
        	else{
        		settleDay='30';
        	}
        	formattime=formattime+"-"+settleDay;
        	alert("time is "+time);
            if (row){
            	//window.location.href='<%=basePath%>pages/voucher/voucherList.jsp?time='+time+'&&formattime='+formattime;
            }
        }
    </script>
</body>
</html>