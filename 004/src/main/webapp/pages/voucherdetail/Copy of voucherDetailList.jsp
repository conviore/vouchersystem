<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%  
	String path = request.getContextPath();  
	String basePath  = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
	System.out.println(basePath);
	String companyId=(String)application.getAttribute("companyId");
	String companyName=(String)application.getAttribute("companyName");
	String voucherId= request.getParameter("voucherId");
%> 
<!DOCTYPE html >
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
    <title>DataGrid Selection - jQuery EasyUI Demo</title>
    <meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="/004/js/jquery-easyui-1.4/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="/004/js/jquery-easyui-1.4/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="/004/js/demo.css">
	<script type="text/javascript" src="/004/js/jquery-easyui-1.4/jquery.min.js"></script>
	<script type="text/javascript" src="/004/js/jquery-easyui-1.4/jquery.easyui.min.js"></script>
</head>
<body>
<h2>凭证概述</h2>
	<div class="easyui-panel" title="New Topic" style="width:400px">
	<div style="margin:20px 0;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="loadRemote()">LoadRemote</a>
	</div>
		<div style="padding:10px 60px 20px 60px">
	    <form id="vif" method="post">
	    	<table cellpadding="5">
	    		<tr>
	    			<td>记账编号:</td>
	    			<td><input class="easyui-textbox" type="text" name="voucherNo" data-options="required:true" readonly></input></td>
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
	    			<td><input class="easyui-textbox" name="comment" data-options="multiline:true" style="height:60px" readonly></input></td>
	    		</tr>
	    	</table>
	    </form>
	    </div>
	</div>>
    <h2>凭证明细</h2>
    <p>填写明细信息</p>
	<table id="dg" class="easyui-datagrid" title="Row Editing in DataGrid" style="width:700px;height:auto"
			data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				toolbar: '#tb',
				url: '<%=basePath %>vd/queryVoucherDetails.action?voucherId=<%=voucherId %>',
				method: 'get',
				onClickRow: onClickRow
			">
		<thead>
			<tr>
				
				<th data-options="field:'credit',width:80,align:'right',editor:{type:'numberbox',options:{precision:2}}">credit</th>
				<th data-options="field:'debit',width:80,align:'right',editor:{type:'numberbox',options:{precision:2}}">debit</th>
				<th data-options="field:'comment',width:250,editor:'textbox'">Comment</th>
				<th data-options="field:'status',width:60,align:'center',editor:{type:'checkbox',options:{on:'P',off:''}},hidden:true">Status</th>
			</tr>
		</thead>
	</table>

	<div id="tb" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">Append</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">Remove</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept()">Accept</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject()">Reject</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="getChanges()">GetChanges</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="reload()">reload</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="saveAll()">SaveAll</a>
	</div>
    <script type="text/javascript">
    var editIndex = undefined;
    $(function(){  
	    //加载凭证信息  l
    	$('#vif').form('load', '<%=basePath %>/vd/getVoucherInfo.action?voucherId=<%=voucherId%>'); 
	});
    function loadRemote(){
    	alert("in this function");
		$('#vif').form('load', '<%=basePath %>/vd/getVoucherInfo.action?voucherId=<%=voucherId%>');
	}
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
        function endEditing(){
			if (editIndex == undefined){return true}
			if ($('#dg').datagrid('validateRow', editIndex)){
				var ed = $('#dg').datagrid('getEditor', {index:editIndex,field:'productid'});
				var productname = $(ed.target).combobox('getText');
				$('#dg').datagrid('getRows')[editIndex]['productname'] = productname;
				$('#dg').datagrid('endEdit', editIndex);
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		}
		function onClickRow(index){
			if (editIndex != index){
				if (endEditing()){
					$('#dg').datagrid('selectRow', index)
							.datagrid('beginEdit', index);
					editIndex = index;
				} else {
					$('#dg').datagrid('selectRow', editIndex);
				}
			}
		}
		function append(){
			//alert(endEditing());
			//if (endEditing()){
				$('#dg').datagrid('appendRow',{status:'P'});
				editIndex = $('#dg').datagrid('getRows').length-1;
				$('#dg').datagrid('selectRow', editIndex)
						.datagrid('beginEdit', editIndex);
			//}
		}
		function removeit(){
			if (editIndex == undefined){return}
			$('#dg').datagrid('cancelEdit', editIndex)
					.datagrid('deleteRow', editIndex);
			editIndex = undefined;
		}
		function accept(){
			if (endEditing()){
				$('#dg').datagrid('acceptChanges');
			}
		}
		function reject(){
			$('#dg').datagrid('rejectChanges');
			editIndex = undefined;
		}
		function getChanges(){
			alert(editIndex);
			//$('#dg').datagrid('endEdit',editIndex);
			var rows = $('#dg').datagrid('getChanges');
			alert(rows.length+' rows are changed!');
			 var effectRow = {};
			if ($('#dg').datagrid('getChanges').length) {
                var inserted =$('#dg').datagrid('getChanges', "inserted");
                var deleted = $('#dg').datagrid('getChanges', "deleted");
                var updated = $('#dg').datagrid('getChanges', "updated");
                  
               
                if (inserted.length) {
                    effectRow["inserted"] = JSON.stringify(inserted);
                }
                if (deleted.length) {
                    effectRow["deleted"] = JSON.stringify(deleted);
                }
                if (updated.length) {
                    effectRow["updated"] = JSON.stringify(updated);
                }
			}
                alert(effectRow["updated"]);
                alert(effectRow["inserted"]); 
                 
		}
		//user defined
		function reload(){
			$('#dg').datagrid('reload');
			$('#dg').datagrid('rejectChanges');
			editIndex = undefined;
		}
		
		function saveAll(){
			$('#dg').datagrid('endEdit',editIndex);
			var rows = $('#dg').datagrid('getChanges');
			alert(rows.length+' rows are changed!');
			 var effectRow = {};
			if ($('#dg').datagrid('getChanges').length) {
                var inserted =$('#dg').datagrid('getChanges', "inserted");
                var deleted = $('#dg').datagrid('getChanges', "deleted");
                var updated = $('#dg').datagrid('getChanges', "updated");
                  
               
                if (inserted.length) {
                    effectRow["inserted"] = JSON.stringify(inserted);
                }
                if (deleted.length) {
                    effectRow["deleted"] = JSON.stringify(deleted);
                }
                if (updated.length) {
                    effectRow["updated"] = JSON.stringify(updated);
                }
			}
		       $.post("<%=basePath%>vd/saveVoucherDetails.action?voucherId=<%=voucherId%>", effectRow, function(rsp) {
                            if(rsp.status){
                           //$.messager.alert("提示", "提交成功！");
                           alert(rsp.status);
                             $dg.datagrid('acceptChanges');
                           reload();
                 }
         }, "JSON").error(function() {
        $.messager.alert("提示", "提交错误了！");
         });
			
		}
    </script>
</body>
</html>