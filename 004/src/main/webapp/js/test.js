$(function(){     
        $('#test2').datagrid({  
             title:'My Title',//表格标题  
             iconCls:'icon-save',//表格图标  
             nowrap: false,//是否只显示一行，即文本过多是否省略部分。  
             striped: true,  
             url:'004/queryEmp.action', //action地址   
            idField:'nodeID',  
            frozenColumns:[[  
            ]],  
            columns:[[  
                {field:'empId',title:'图标',width:150},  
                {field:'lastName',title:'lastname',width:120},                   
            ]],  
            pagination:true, //包含分页  
            rownumbers:true,  
            singleSelect:true,  
            toolbar:[{  
                text:'Add',  
                iconCls:'icon-add',  
                handler:function(){  
                    alert('add')  
                }  
            },{  
                text:'Cut',  
                iconCls:'icon-cut',  
                handler:function(){  
                    alert('cut')  
                }  
            },'-',{  
                text:'Save',  
                iconCls:'icon-save',  
                handler:function(){  
                    alert('save')  
                }  
            }]  
        });  
        });  