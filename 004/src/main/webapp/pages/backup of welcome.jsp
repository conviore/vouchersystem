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
<!DOCTYPE html >
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/004/js/jquery-easyui-1.4/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="/004/js/jquery-easyui-1.4/themes/icon.css">
	<script type="text/javascript" src="/004/js/jquery-easyui-1.4/jquery.min.js"></script>
	<script type="text/javascript" src="/004/js/jquery-easyui-1.4/jquery.easyui.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"><style type="text/css">
<style type="text/css">
a:link,a:visited{
 text-decoration:none;  /*超链接无下划线*/
}
a:hover{
 text-decoration:underline;  /*鼠标放上去有下划线*/
}

</style>

<title>Welcome Master, this is the <%=companyName %>'s account</title>
</head>
<body>
<h2>Accounting's  MenuButton</h2>
    <p>Move mouse over the button to drop down menu.</p>
    <div style="margin:20px 0;"></div>
    <div class="easyui-panel" style="padding:5px;">
        <a href="#" class="easyui-linkbutton" data-options="plain:true">Home</a>
        <a href="<%=basePath %>pages/monsettleprog/monSettleProgList.jsp" class="easyui-menubutton" data-options="menu:'#mm1',iconCls:'icon-edit'">记账动作</a>
        <a href="#" class="easyui-menubutton" data-options="menu:'#mm2',iconCls:'icon-help'">Help</a>
        <a href="#" class="easyui-menubutton" data-options="menu:'#mm3'">About</a>
    </div>
    <div id="mm1" style="width:150px;">
        <div data-options="iconCls:'icon-undo'" > <a href="<%=basePath %>pages/company/uploadfile.jsp"  >initialData</a></div>
        <div data-options="iconCls:'icon-redo'"><a href="<%=basePath %>pages/monsettleprog/monSettleProgList.jsp" class="easyui-menubutton" data-options="menu:'#mm1',iconCls:'icon-edit'">记账状态</a></div>
        <div data-options="iconCls:'icon-redo'"><a href="<%=basePath %>pages/mondetail/mondetailList.jsp" class="easyui-menubutton" data-options="menu:'#mm1',iconCls:'icon-edit'">科目明细</a></div>
        <div class="menu-sep"></div>
        <div>Cut</div>
        <div>Copy</div>
        <div>Paste</div>
        <div class="menu-sep"></div>
        <div>
            <span>Toolbar</span>
            <div>
                <div>Address</div>
                <div>Link</div>
                <div>Navigation Toolbar</div>
                <div>Bookmark Toolbar</div>
                <div class="menu-sep"></div>
                <div>New Toolbar...</div>
            </div>
        </div>
        <div data-options="iconCls:'icon-remove'">Delete</div>
        <div>Select All</div>
    </div>
    <div id="mm2" style="width:100px;"> <a href="<%=basePath %>pages/tree/mainframe.jsp"  >科目管理</a>
        <div>Help</div>
        <div>Update</div>
        <div>About</div>
    </div>
    <div id="mm3" class="menu-content" style="background:#f0f0f0;padding:10px;text-align:left">
        <img src="http://www.jeasyui.com/images/logo1.png" style="width:150px;height:50px">
        <p style="font-size:14px;color:#444;">Try jQuery EasyUI to build your modern, interactive, javascript applications.</p>
    </div>	
</body>
</html>