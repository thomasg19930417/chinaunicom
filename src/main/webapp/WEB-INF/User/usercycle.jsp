<%@ page isELIgnored="false"%>
<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="../css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="../css/bootstrap-responsive.css" />
    <link rel="stylesheet" type="text/css" href="../css/style.css" />
     <link rel="stylesheet" type="text/css" href="../css/bootstrap-datepicker.min.css" />
    <script type="text/javascript" src="../js/jquery.js"></script>
    <script type="text/javascript" src="../js/jquery.sorted.js"></script>
    <script type="text/javascript" src="../js/bootstrap.js"></script>
    <script type="text/javascript" src="../js/ckform.js"></script>
    <script type="text/javascript" src="../js/common.js"></script>
    <script type="text/javascript" src="../js/bootstrap-datepicker.js"></script>
     <script type="text/javascript" src="../js/bootstrap-datepicker.zh-CN.min.js"></script>
    <style type="text/css">
        body {
	        background: #ECF5FF;
            padding-bottom: 40px;
        }
        .sidebar-nav {
            padding: 9px 0;
        }

        @media (max-width: 980px) {
            /* Enable use of floated navbar text */
            .navbar-text.pull-right {
                float: none;
                padding-left: 5px;
                padding-right: 5px;
            }
        }
       .pagination{
        list-style: none;
       }
       .pagination  li{
       	float: left;
       }

    </style>
</head>
<body>
<form class="form-inline definewidth m20" id="myform">
     手机号：
    <input type="text" name="mobile" class="abc input-default" placeholder="" value="">&nbsp;&nbsp; 
     开始时间：
   <input type="date" name="start" class="abc input-default " value="" >  
   
      结束时间：
    <input type="date" name="end" class="abc input-default" placeholder="" value="">&nbsp;&nbsp; 
    <button type="button"  onclick="search()"  class="btn btn-primary">查询</button>
</form>
<table class="table table-bordered table-hover definewidth m10">
    <thead>
    <tr>
        <th>手机号</th>
        <th>群发类型</th>
        <th>活跃时间</th>
    </tr>
    </thead>
    <tbody id="content">

    </tbody>
       </table>
<script>
		var str="";
		function search(){
			alert($("#myform").serialize());
			str="";
			$.post("/chinaunicom/userActicve/get",$("#myform").serialize(),function(data){
				if(data.data.length >0){
					for(var i=0;i<data.data.length;i++){
						str+="<tr>";
						str+="<td>"+data.data[i].user_device+"</td>";
						str+="<td>"+data.data[i].target+"</td>";
						str+="<td>"+data.data[i].timestreap+"</td>";
						str+="</tr>";
					}
					$("#content").html(str);
				
				}else{
					alert("没有检索到相关记录！！！");
				}
			},"json");	
		 }
		
</script>
</body>
</html>
