<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%  
	String path = request.getContextPath();  
	String basePath  = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
	System.out.println(basePath);
	String companyId=(String)application.getAttribute("companyId");
	String companyName=(String)application.getAttribute("companyName");
%> 
<!DOCTYPE html>

<html>
<head>
    <meta charset="UTF-8">
    <title>Modal Window - jQuery EasyUI Demo</title>
    <link rel="stylesheet" type="text/css" href="/004/js/jquery-easyui-1.4/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="/004/js/jquery-easyui-1.4/themes/icon.css">
	<script type="text/javascript" src="/004/js/jquery-easyui-1.4/jquery.min.js"></script>
	<script type="text/javascript" src="/004/js/jquery-easyui-1.4/jquery.easyui.min.js"></script>
</head>
<body>
    <h2>Modal Window</h2>
    <p>Click the open button below to open the modal window.</p>
    <div style="margin:20px 0;">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#w').window('open')">Open</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#w').window('close')">Close</a>
    </div>
    <div id="w" class="easyui-window" title="Modal Window" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:500px;height:200px;padding:10px;">
        The window content.
        <div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="New Topic" style="width:400px">
		<div style="padding:10px 60px 20px 60px">
	    <form  id="ff"  class="easyui-form" method="post" >
	    	<table cellpadding="5">
	    		<tr>
	    			<td>voucherNumber:</td>
	    			<td><input class="easyui-textbox" type="text" name="voucherNumber" dalta-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>comment:</td>
	    			<td><input class="easyui-textbox" name="comment" data-options="multiline:true" style="height:60px"></input></td>
	    		</tr>
	    		<tr>
	    			<td>SettleBeginDate:</td>
	    			<td>
	    		        <input class="easyui-datebox" name="settleBeginDate"></input>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>SettleBeginDate:</td>
	    			<td>
	    		        <input id="txtDate" type="text" />
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	    <div style="text-align:center;padding:5px">
	    
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">Submit</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">Clear</a>
	    </div>
	    </div>
	</div>
    </div>
 
	<script type="text/javascript">
	function submitForm(){
//			$('#ff').form('submit');
			$.messager.progress();	// display the progress bar
			$('#ff').form('submit', {
				url: '<%=basePath%>/004/testVoucher.action',
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
					var result=obj.info;
					alert('save company success');
					alert(result);
					$('#w').window('close');
				}
			});
		}
    function clearForm(){
			alert("in this function");
			$('#ff').form('clear');
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
		</script>
</body>
</html>