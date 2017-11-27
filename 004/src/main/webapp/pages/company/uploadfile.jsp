<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
System.out.println(basePath);
String companyId=(String)application.getAttribute("companyId");
String companyName=(String)application.getAttribute("companyName");
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html>
<head>
   <title><%=companyName %> 科目数据初始化</title>
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
    <link rel="stylesheet" type="text/css" href="/004/js/jquery-easyui-1.4/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="/004/js/jquery-easyui-1.4/themes/icon.css">
	<script type="text/javascript" src="/004/js/jquery-easyui-1.4/jquery.min.js"></script>
	<script type="text/javascript" src="/004/js/jquery-easyui-1.4/jquery.easyui.min.js"></script>
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
<h2><%=companyName %> 科目数据初始化 上传文件</h2>
    <p>上传您初始化使用的excel文件</p>
	<div style="margin: 20px 0;"></div>
	<div class="easyui-panel" title="上传excel" style="width: 400px">
		<div style="padding: 10px 60px 20px 60px">
         <s:form id="ff2" action='../../004/doUpload.action' enctype="multipart/form-data" >
			<s:file name="uploadFile" label="File"/>
             <a href="javascript:void(0)" class="easyui-linkbutton"
					onclick="submitForm2()">提交初始化</a>
		</s:form>
		<!-- 
		<s:submit/>
		<div style="text-align: center; padding: 5px; display=none">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					onclick="submitForm()">Submit</a> <a href="javascript:void(0)"
					class="easyui-linkbutton" onclick="clearForm()">Clear</a> <a
					href="javascript:void(0)" class="easyui-linkbutton"
					onclick="test()">test</a>
			</div>
		
		 -->
			
		</div>
	</div>
	<script type="text/javascript">

		function submitForm2() {
			alert("in this submitForm2");
			$('#ff2').form(
					'submit',
					{
						onSubmit : function() {
							return $(this).form('enableValidation').form(
									'validate');
						},
						success : function(data) {
							//alert(data);
							 var obj = eval('(' + data + ')');
							var result=obj.info;
// 							alert('process success');
							alert(result);
// 							$("#treeshow", parent.document.body).attr("src","/004/tree.html");
// 							$("#treeedit", parent.document.body).attr("src", "/004/helptree.html");
						}
					});
		}
		function clearForm() {
			alert("in this function");
			$('#ff2').form('clear');
		}
		function test() {
			$("#treeshow", parent.document.body).attr("src", "/004/tree.html");
		}
	</script>

</html>