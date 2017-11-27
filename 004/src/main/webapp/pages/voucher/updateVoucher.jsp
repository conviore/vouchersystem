<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%  
    String voucherId =request.getParameter("voucherId");
    System.out.println("voucherId is "+voucherId);
    String settleTime =request.getParameter("settleTime");
    String formattime =request.getParameter("formattime");
    String time="199009";
    if(!(settleTime==null)){
    	time=settleTime.substring(0, 6);
    }
    System.out.println("settleTime is "+settleTime);
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
	<script type="text/javascript" src="/004/js/jquery-easyui-1.4/jquery.min.js"></script>
	<script type="text/javascript" src="/004/js/jquery-easyui-1.4/jquery.easyui.min.js"></script>
<title>更新<%=companyName %>凭证信息</title>
</head>
<body>
<h2><%=companyName %>的凭证</h2>
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
<div class="easyui-panel" title="凭证概要更新" style="width:400px">
    <div style="margin:20px 0;">
        <a href="#" class="easyui-linkbutton" onclick="submitVoucherInfo()">提交凭证更新</a>
    </div>
	    <div style="margin:20px 0;">
        <a href="<%=basePath %>vd/getVoucherSummaryExcel.action?voucherId=<%=voucherId%>&&settleTime=<%=settleTime %>" class="easyui-linkbutton" onclick="">下载记账汇总excel</a>
    </div>
		<div style="padding:10px 60px 20px 60px">
	    <form id="vif" method="post">
	    	<table  title="凭证概要" cellpadding="5">
	    		<tr>
	    			<td>记账编号:</td>
	    			<td><input class="easyui-textbox" type="text" name="voucherNo" data-options="required:true" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>记账日期:</td>
	    			<td><input class="easyui-textbox" type="text" name="settleTime" data-options="required:true" readonly></input></td>
	    		</tr>
	    		<tr>
	    			<td>借方总额:</td>
	    			<td><input class="easyui-textbox" type="text" name="debitSum" data-options="required:true" readonly></input></td>
	    		</tr>
	    		<tr>
	    			<td>贷方总额:</td>
	    			<td><input class="easyui-textbox" type="text" name="creditSum" data-options="required:true" readonly></input></td>
	    		</tr>
	    		<tr>
	    			<td>comment:</td>
	    			<td><input class="easyui-textbox" name="comment" data-options="multiline:true" style="height:60px" ></input></td>
	    		</tr>
	    	</table>
	    </form>
	    </div>
	</div>
<script type="text/javascript">
$(function(){  
    //加载凭证信息  
	$('#vif').form('load', '<%=basePath %>/vd/getVoucherInfo.action?voucherId=<%=voucherId%>'); 
});

function submitVoucherInfo(){
//	$('#ff').form('submit');
	$.messager.progress();	// display the progress bar
	$('#vif').form('submit', {
		url: '<%=basePath%>v/updateVoucher.action?voucherId=<%=voucherId%>',
		onSubmit: function(){
			var isValid = $(this).form('validate');
			if (!isValid){
				alert('data is invalid');
				$.messager.progress('close');	// hide progress bar while the form is invalid
			}
			alert('data is valid');
			return isValid;	// return false will stop the form submission
		},
		success: function(data){
			$.messager.progress('close');	// hide progress bar while submit successfully
				var obj = eval('(' + data + ')');
			alert(obj.info);
//				alert(result);
            time=<%=time%>;
            formattime=<%=formattime%>;
            window.location.href='<%=basePath%>pages/voucher/voucherList.jsp?time='+time+'&&formattime='+formattime;
// 			reload();
		}
	});
}
</script>
</body>
</html>