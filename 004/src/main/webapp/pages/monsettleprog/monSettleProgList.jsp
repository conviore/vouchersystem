<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%  
	String path = request.getContextPath();  
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
	System.out.println(basePath);
	String companyId=(String)application.getAttribute("companyId");
	String companyName=(String)application.getAttribute("companyName");
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
<h2><%=companyName %>记账月进度表</h2>
    <p>选择一列进行相应的操作</p>
    <div style="margin:20px 0;">
        <a href="#" class="easyui-linkbutton" onclick="getSelected()">查看明细</a>
        <a href="#" class="easyui-linkbutton" onclick="inputSettles()">录入凭证</a>
        <a href="#" class="easyui-linkbutton" onclick="getBalanceSheet()">查看科目汇总</a>
        <a href="#" class="easyui-linkbutton" onclick="getBalanceSheetExcel()">下载资产负债表</a>
        <a href="#" class="easyui-linkbutton" onclick="getProfitSheetExcel()">下载利润表</a>
        <a href="#" class="easyui-linkbutton" onclick="getVSRExcel()">下载记账凭证汇总表</a>
        <a href="#" class="easyui-linkbutton" onclick="endMsp()">月结</a>
        <a href="#" class="easyui-linkbutton" onclick="addMsp()">创建新月份</a>
    </div>
    <table id="dg" class="easyui-datagrid" title="记账进度列表" style="width:700px;height:250px"
            data-options="singleSelect:true,url:'<%=basePath %>/msp/getMonSettleProgList.action',method:'get'">
        <thead>
            <tr>
                <th data-options="field:'id',width:80"  hidden="true">记账状态ID</th>
                <th data-options="field:'settleYear',width:100">记账年</th>
                <th data-options="field:'settleMonth',width:80,align:'right'">记账月</th>
                <th data-options="field:'comment',width:60,align:'center'">备注</th>
                <th data-options="field:'status',width:60,align:'center'"  hidden="true">状态</th>
                <th data-options="field:'createTime',width:250,align:'center'">创建时间</th>
                <th data-options="field:'updateTime',width:250,align:'center'">更新时间</th>
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
            	window.location.href='<%=basePath%>pages/voucher/voucherList.jsp?time='+time+'&&formattime='+formattime;
            }
        }
        
        function getBalanceSheet(){
        	var row = $('#dg').datagrid('getSelected');
        	var settleTime=row.settleYear+row.settleMonth;
        	//alert(settleTime);
            window.location.href='<%=basePath%>pages/balance/balancesheetshow.jsp?settleTime='+settleTime;
        }
        
        function endMsp(){
        	var row = $('#dg').datagrid('getSelected');
        	var settleTime=row.settleYear+row.settleMonth;
        	$.post('<%=basePath%>msp/endMonSettle.action?companyId=<%=companyId%>&&settleTime='+settleTime,function(rsp) {
                if(rsp.status){
               //$.messager.alert("提示", "提交成功！");
               alert(rsp.msg);
               reload();
     			}
				}, "JSON").error(function() {
				$.messager.alert("提示", "提交错误了！");
			});
        }
        
        function addMsp(){
        	$.post('<%=basePath%>msp/addNewMonSettle.action?companyId=<%=companyId%>',function(rsp) {
                if(rsp.status){
               //$.messager.alert("提示", "提交成功！");
               alert(rsp.msg);
               reload();
     			}
				}, "JSON").error(function() {
				$.messager.alert("提示", "提交错误了！");
			});
        }
        
		function reload(){
			$('#dg').datagrid('reload');
			$('#dg').datagrid('rejectChanges');
			editIndex = undefined;
		}
		function getBalanceSheetExcel(){
        	var row = $('#dg').datagrid('getSelected');
        	var settleTime=row.settleYear+row.settleMonth;
        	//alert(settleTime);
        	
<%--         	window.open('<%=basePath %>msp/getBalanceSheetExcel.action?settleTime='+settleTime, '_blank', "scrollbars=yes,resizable=1,modal=false,alwaysRaised=yes"); --%>
        	window.open('<%=basePath %>msp/getBalanceSheetExcel.action?settleTime='+settleTime, '_blank');

		}

        function getProfitSheetExcel(){
            var row = $('#dg').datagrid('getSelected');
            var settleTime=row.settleYear+row.settleMonth;
            //alert(settleTime);
            window.open('<%=basePath %>msp/getProfitSheetExcel.action?settleTime='+settleTime, '_blank');

        }
        
        function getVSRExcel(){
            var row = $('#dg').datagrid('getSelected');
            var settleTime=row.settleYear+row.settleMonth;
            //alert(settleTime);
            window.open('<%=basePath %>msp/getVSRExcel.action?settleTime='+settleTime, '_blank');

        }
    </script>
</body>
</html>