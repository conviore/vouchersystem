<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%  
    String time =request.getParameter("time");
    String formattime=request.getParameter("formattime");
    System.out.println("time is "+time);
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
<title>Insert title here</title>
</head>
<body>
<h2><%=companyName %>的凭证列表</h2>
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
    <form  id="ff2"  class="easyui-form" method="post" >
	    </form>	
    <div style="margin:20px 0;">
        <a href="#" class="easyui-linkbutton" onclick="addNewVoucher()">增加新凭证</a>
        <a href="#" class="easyui-linkbutton" onclick="editVoucherDetail()">增加凭证明细</a>
        <a href="#" class="easyui-linkbutton" onclick="deleteVoucher()">删除凭证</a>
        <!-- 
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#w').window('open')">Open</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#w').window('close')">Close</a>
         -->
    </div>
    <table id="dg" class="easyui-datagrid" title="DataGrid Selection" style="width:700px;height:250px"
            data-options="singleSelect:true,url:'<%=basePath %>v/getVoucherList.action?time=<%=time %>',method:'get'">
        <thead>
            <tr>
                <th data-options="field:'id',width:80">id</th>
                <th data-options="field:'voucherNo',width:80">凭证编码</th>
                <th data-options="field:'settleTime',width:80">凭证时间</th>
				<th data-options="field:'debitSum',width:80">借方总和</th>
				<th data-options="field:'creditSum',width:80">贷方总和</th>
                <th data-options="field:'debitSubjectCodes',width:80">借方科目编码</th>
                <th data-options="field:'creditSubjectCodes',width:100">贷方科目编码</th>
                <th data-options="field:'updateTime',width:80,align:'right'">更新时间</th>
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
    <div id="w" class="easyui-window" title="增加新凭证" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:800px;height:400px;padding:10px;">
        填写新凭证信息.
        <div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="New Topic" style="width:400px">
		<div style="padding:10px 60px 20px 60px">
	    <form  id="ff"  class="easyui-form" method="post" >
	    	<table >
	    		<tr>
	    			<td>凭证号:</td>
	    			<td><input class="easyui-textbox" type="text" name="voucherNumber" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>注释:</td>
	    			<td><input class="easyui-textbox" name="comment" data-options="multiline:true" style="height:60px"></input></td>
	    		</tr>
	    		<tr>
	    			<td>凭证日期:</td>
	    			<td>
	    		        <input id="voucherDate" name="voucherDate" type="text" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser" />
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	    <div style="text-align:center;padding:5px">
	    
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitWindowForm()">Submit</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearWindowForm()">Clear</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeWindow()">CloseWindow</a>
	    </div>
	    </div>
	</div>
    </div>
     <script type="text/javascript">
     $(function(){  
    	    //设置时间  
    	$("#voucherDate").datebox("setValue","<%=formattime%>");  
    	});
     
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
        function modifyDetails(){
        	var row = $('#dg').datagrid('getSelected');
        	var time=row.settleYear+row.settleMonth;
        	var voucherid=row.id;
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
            	window.location.href='<%=basePath%>pages/voucherdetail/voucherDetailList.jsp?voucherid='+voucherid;
            }
        }
        function viewMore(){
        	var row = $('#dg').datagrid('getSelected');
            if (row){
                $.messager.alert('Info', row.itemid+":"+row.productid+":"+row.attr1);
            }
        }
        function back(){
        	
        }
        function submitWindowForm(){
//			$('#ff').form('submit');
			$.messager.progress();	// display the progress bar
			$('#ff').form('submit', {
				url: '<%=basePath%>/v/saveVoucher.action',
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
// 					alert(result);
					$('#w').window('close');
					reload();
				}
			});
		}
        function addNewVoucher(){
        	$('#w').window('open');
        }
		function clearWindowForm(){
			alert("in this function");
			$('#ff').form('clear');
		}
		function closeWindow(){
			$('#w').window('close');
		}
		
		function myformatter(date){
            var y = date.getFullYear();
            var m = date.getMonth()+1;
            var d = date.getDate();
            return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
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
        }
   function editVoucherDetail(){
	   var row = $('#dg').datagrid('getSelected');
	   var settleTime=row.settleTime;
	   //alert("Settletime is "+settleTime);
   	var time=row.settleYear+row.settleMonth;
   	var voucherid=row.id;
   	var formattime=row.settleYear+'-'+row.settleMonth;
   	var settleDay='30';
   	if(row.settleMonth==2){
   		settleDay='28';
   	}
   	else{
   		settleDay='30';
   	}
   	formattime=formattime+"-"+settleDay;
   	//alert("time is "+time);
       if (row){
       	window.location.href='<%=basePath%>pages/voucherdetail/voucherDetailList.jsp?voucherId='+voucherid+'&&settleTime='+settleTime;
       }
	   
   }
   
   function delVoucher(){
	   var row = $('#dg').datagrid('getSelected');
	   var voucherId=row.id;
		$('#ff').form('submit', {
			url: '<%=basePath%>/v/deleteVoucher.action?voucherId='+voucherId,
			onSubmit: function(){
				//var isValid = $(this).form('validate');
				alert('data is valid');
				return true;	// return false will stop the form submission
			},
			success: function(data){
				$.messager.progress('close');	// hide progress bar while submit successfully
				var obj = eval('(' + data + ')');
				alert(obj.info);
//				alert(result);
				reload();
			}
		});
   }
   
   function modifyVoucher(){
   	var row = $('#dg').datagrid('getSelected');
   	var time=row.settleTime.substring(0,6);
   	var voucherid=row.id;
   	var formattime=row.settleTime.substring(0,4)+'-'+row.settleTime.substring(4,6)+'-'+row.settleTime.substring(6,8);
   	var settleDay=row.settleTime.substring(4,6);
   	formattime=formattime+"-"+settleDay;
   	var settleTime=row.settleTime;
   	alert("in modify function settleTime is "+settleTime);
       if (row){
       	window.location.href='<%=basePath%>pages/voucher/updateVoucher.jsp?voucherId='+voucherid+'&&settleTime='+settleTime+'&&formattime='+formattime;
       }
   }
   
   function deleteVoucher(){
//		$('#ff').form('submit');
	
		var row = $('#dg').datagrid('getSelected');
   	var time=row.settleTime.substring(0,6);
   	var voucherid=row.id;
   	if(confirm("确认删除吗? 凭证号为"+row.voucherNo)){
   		//alert("yes");
   		}
   		else{
   		//alert("")；
   		return;
   		}
   	var URL='<%=basePath%>/v/deleteVoucher.action?voucherId='+voucherid;
   	//alert(URL);
		//$.messager.progress();	// display the progress bar
		$('#ff2').form('submit', {
			url: '<%=basePath%>/v/deleteVoucher.action?voucherId='+voucherid,
			success: function(data){
				//$.messager.progress('close');	// hide progress bar while submit successfully
				var obj = eval('(' + data + ')');
				alert(obj.info);
//				alert(result);
				reload();
			}
		});
	}
   
	function reload(){
		$('#dg').datagrid('reload');
// 		$('#dg').datagrid('rejectChanges');
// 		editIndex = undefined;
	}
		</script>
</body>
</html>