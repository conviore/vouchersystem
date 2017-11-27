<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html >
<html>
<head>
	<title>Validate Form on Submit - jQuery EasyUI Demo</title>
    <link rel="stylesheet" type="text/css" href="/004/js/jquery-easyui-1.4/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="/004/js/jquery-easyui-1.4/themes/icon.css">
   
	<script type="text/javascript" src="/004/js/jquery-easyui-1.4/jquery.min.js"></script>
	<script type="text/javascript" src="/004/js/jquery-easyui-1.4/jquery.easyui.min.js"></script>
</head>
<body>
	<h2>插入一个新科目</h2>
	<div style="margin: 20px 0;"></div>
	<div class="easyui-panel" title="New Topic" style="width: 400px">
		<div style="padding: 10px 60px 20px 60px">
            <s:form id="ff2" action="004/004/saveSubject.action" class="easyui-form">
            科目主键<s:textfield label="subject Id" name="subjectId" class="easyui-textbox" type="text" readonly="true"/>
            父主键<s:textfield label="parent Id" name="parentId" class="easyui-textbox" type="text" readonly="true" display="false"/>
            父节点编码<s:textfield label="parent code" name="parentCode" class="easyui-textbox" type="text" readonly="true"/>
            科目编码<s:textfield label="subject code" name="subjectCode" class="easyui-textbox" type="text" readonly="true"/>
             科目名<s:textfield label="subject name" name="subjectName" class="easyui-textbox" type="text"/>
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
							$("#treeshow", parent.document.body).attr("src","/004/tree.html");
							$("#treeedit", parent.document.body).attr("src", "/004/helptree.html");
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