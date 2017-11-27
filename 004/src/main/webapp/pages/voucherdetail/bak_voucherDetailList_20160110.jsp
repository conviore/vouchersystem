<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%  
	String path = request.getContextPath();  
	String basePath  = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
	System.out.println(basePath);
	String companyId=(String)application.getAttribute("companyId");
	String companyName=(String)application.getAttribute("companyName");
	String voucherId= request.getParameter("voucherId");
	String settleTime= request.getParameter("settleTime");
	
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
<h2><%=companyName %> <%=settleTime %> 的凭证明细概述</h2>
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
<a href="#" class="easyui-linkbutton" onclick="gotofrontpage()">返回</a>
	<div class="easyui-panel" title="凭证概要" style="width:400px">
	    <div style="margin:20px 0;">
        <a href="<%=basePath %>vd/getVoucherSummaryExcel.action?voucherId=<%=voucherId%>&&settleTime=<%=settleTime %>" class="easyui-linkbutton" onclick="">下载记账汇总excel</a>
    </div>
		<div style="padding:10px 60px 20px 60px">
	    <form id="vif" method="post">
	    	<table  title="凭证概要" cellpadding="5">
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
	<div style="margin:20px 0;"></div>
	
	<table id="dg" class="easyui-datagrid" title="凭证明细" style="width:700px;height:auto"
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
				<th data-options="field:'id',width:80,align:'right',hidden:'true'">pk</th>
				<th data-options="field:'subjectId',width:100,
						formatter:function(value,row){
							return value;
						},
						editor:{
							type:'combobox',
							options:{
								valueField:'id',
								textField:'code',
								method:'get',
								url:'<%=basePath %>004/querySubject4Details.action',
								required:true
							}
						}">subjectId</th>
			    <th data-options="field:'subjectCode',width:80,align:'right'">帐目编码</th>
				<th data-options="field:'debit',width:80,align:'right',editor:{type:'numberbox',options:{precision:2}}">借方</th>
				<th data-options="field:'credit',width:80,align:'right',editor:{type:'numberbox',options:{precision:2}}">贷方</th>
				<th data-options="field:'comment',width:250,editor:'textbox'">摘要</th>
				<th data-options="field:'settleWay',width:250,editor:'textbox'">结算方式</th>
				<th data-options="field:'status',width:60,align:'center',editor:{type:'checkbox',options:{on:'P',off:''}},hidden:true">Status</th>
			</tr>
		</thead>
	</table>

	<div id="tb" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept()">Accept</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject()">撤销</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="getChanges()">获取变化</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="reload()">刷新</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="saveAll()">保存</a>
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
				var ed = $('#dg').datagrid('getEditor', {index:editIndex,field:'subjectId'});
				var subjectCode = $(ed.target).combobox('getText');
				$('#dg').datagrid('getRows')[editIndex]['subjectCode'] = subjectCode;
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
                if (deleted.length) {l;
                    effectRow["deleted"] = JSON.stringify(deleted);
                }
                if (updated.length) {
                    effectRow["updated"] = JSON.stringify(updated);
                }
			}
		       $.post("<%=basePath%>vd/saveVoucherDetails.action?voucherId=<%=voucherId%>&&settleTime=<%=settleTime%>", effectRow, function(rsp) {
                            if(rsp.status){
                           $.messager.alert("提示", "提交成功！");
//                            alert(rsp.status);
//                            $dg.datagrid('acceptChanges');
                           location.reload();
                 }
         }, "JSON").error(function() {
        $.messager.alert("提示", "提交错误了！");
         });
		       reload();	
		}
		
		function gotofrontpage(){
			location.replace(document.referrer);
		}
    </script>
</body>
</html>