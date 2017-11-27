<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=companyName %>科目树操作</title>
    <link rel="stylesheet" type="text/css" href="/004/js/jquery-easyui-1.4/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="/004/js/jquery-easyui-1.4/themes/icon.css">
   
	<script type="text/javascript" src="/004/js/jquery-easyui-1.4/jquery.min.js"></script>
	<script type="text/javascript" src="/004/js/jquery-easyui-1.4/jquery.easyui.min.js"></script>
</head>
<body>
<h2><%=companyName %>科目树操作</h2>
	<p>Click the buttons below to perform actions.</p>

<div class="easyui-panel" style="padding:5px">
		<ul id="tt" class="easyui-tree" data-options="
				url: '<%=basePath %>subject/generateTree.action',
				method: 'get',
				animate: true,
				onContextMenu: function(e,node){
					e.preventDefault();
					$(this).tree('select',node.target);
					$('#mm').menu('show',{
						left: e.pageX,
						top: e.pageY
					});
				},
				onClick: onClickNode
			"></ul>
	</div>
	<div id="mm" class="easyui-menu" style="width:120px;">
		<div onclick="append()" data-options="iconCls:'icon-add'">Append</div>
		<div onclick="removeit()" data-options="iconCls:'icon-remove'">Remove</div>
		<div class="menu-sep"></div>
		<div onclick="expand()">Expand</div>
		<div onclick="collapse()">Collapse</div>
		<div onclick="expandAll()">ExpandAll</div>
		<div onclick="reload()" data-options="iconCls:'icon-reload'">Reload</div>
	</div>

	<script type="text/javascript">
		function append(){
			var t = $('#tt');
			var node = t.tree('getSelected');
			$("#treeedit",parent.document.body).attr("src","<%=basePath %>subject/forInsert.action?nodeId="+node.id)
		}
		function removeit(){
			var node = $('#tt').tree('getSelected');
			$('#tt').tree('remove', node.target);
		}
		function collapse(){
			var node = $('#tt').tree('getSelected');
			$('#tt').tree('collapse',node.target);
		}
		function expand(){
			var node = $('#tt').tree('getSelected');
			$('#tt').tree('expand',node.target);
		}
		function reload(){
			$('#tt').tree('reload');
		}
		function onClickNode(node){
					//alert("node is "+node.id);  // alert node text property when clicked
					//change another iframes's content
					$("#treeedit",parent.document.body).attr("src","<%=basePath %>subject/showSubjectDetail.action?nodeId="+node.id)
		}
		
		
		function appendBak(){
			var t = $('#tt');
			var node = t.tree('getSelected');
			t.tree('append', {
				parent: (node?node.target:null),
				data: [{
					text: 'new item1'
				},{
					text: 'new item2'
				}]
			});
		}
		function expandAll(){
            var node = $('#tt').tree('getSelected');
            if (node) {
                $('#tt').tree('expandAll', node.target);
            }
            else {
                $('#tt').tree('expandAll');
            }
        }
	</script>
	
</body>
</html>