<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%  
	String path = request.getContextPath();  
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
	System.out.println(basePath);
	String companyId=(String)application.getAttribute("companyId");
	String companyName=(String)application.getAttribute("companyName");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Validate Form on Submit - jQuery EasyUI Demo</title>
<link rel="stylesheet" type="text/css"
	href="/004/js/jquery-easyui-1.4/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="/004/js/jquery-easyui-1.4/themes/icon.css">

<script type="text/javascript"
	src="/004/js/jquery-easyui-1.4/jquery.min.js"></script>
<script type="text/javascript"
	src="/004/js/jquery-easyui-1.4/jquery.easyui.min.js"></script>
</head>
<body>
	<h2>subjectDetail 4 update JSP file</h2>
	<p>this is just a test</p>
	<div style="margin: 20px 0;"></div>
	<div class="easyui-panel" title="New Topic" style="width: 400px">
		<div style="padding: 10px 60px 20px 60px">
            <s:form id="ff2" action="" class="easyui-form">
            <s:textfield label="subject Id" name="subjectId" class="easyui-textbox" type="text" readonly="true" hidden='true'/>
            <s:textfield label="code" name="code" class="easyui-textbox" type="text" readonly="true"/>
            <s:textfield label="parent code" name="parentCode" class="easyui-textbox" type="text"/>
             <s:textfield label="subject name" name="subjectName" class="easyui-textbox" type="text"/>
             <a href="javascript:void(0)" class="easyui-linkbutton"
					onclick="submitForm2()">Submit</a>
</s:form>
			<div style="text-align: center; padding: 5px">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					onclick="submitForm()">Submit</a> <a href="javascript:void(0)"
					class="easyui-linkbutton" onclick="clearForm()">Clear</a> <a
					href="javascript:void(0)" class="easyui-linkbutton"
					onclick="test()">test</a>
			</div>
		</div>
	</div>
	<script type="text/javascript">

		function submitForm2() {
			alert("in this submitForm2");
			$('#ff2').form(
					'submit',
					{
						url: '<%=basePath %>subject/updateSubject.action',
						onSubmit : function() {
							return $(this).form('enableValidation').form(
									'validate');
						},
						success : function(data) {
							//alert(data);
							 var obj = eval('(' + data + ')');
							var result=obj.info;
							alert('save success');
							alert(result);
							$("#treeshow", parent.document.body).attr("src","/004/pages/tree/tree.html");
							$("#treeedit", parent.document.body).attr("src", "/004/pages/tree/helptree.html");
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
</body>
</html>