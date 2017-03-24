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
    <script type="text/javascript" src="../js/jquery.js"></script>
    <script type="text/javascript" src="../js/jquery.sorted.js"></script>
    <script type="text/javascript" src="../js/bootstrap.js"></script>
    <script type="text/javascript" src="../js/ckform.js"></script>
    <script type="text/javascript" src="../js/common.js"></script>

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
<form class="form-inline definewidth m20" action="index.html" method="get">
     任务名称：
    <input type="text" name="menuname" id="keywords"class="abc input-default" placeholder="" value="">&nbsp;&nbsp; 
    <button type="button"  onclick="getTaskByPage(1)"  class="btn btn-primary">查询</button>
</form>
<table class="table table-bordered table-hover definewidth m10">
    <thead>
    <tr>
        <th>任务ID</th>
        <th>活动主题</th>
        <th>需求人</th>
        <th>文件名称</th>
        <th>数据编码</th>
        <th>数据量</th>
		<th>提交时间</th>
		<th>操作</th>
    </tr>
    </thead>
    <tbody id="content">

    </tbody>
       </table>
	<div style="margin-left: 80%;">
			<ul class="pagination">
				
			</ul>
	</div> 
		
	<!-- 模板 -->	
	<!-- Modal -->
<div class="modal fade" id="myModal"  data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">标签</h4>
      </div>
      <div class="modal-body">
                标签名称： <input type="text" id="label" placeholder="请输入标签名称"> 
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" id="btn">确定</button>
      </div>
    </div>
  </div>
</div>	
		
<script>
		$(function() {
			getTaskByPage(1);
		});

		function getTaskByPage(obj) {
			var keywords = $("#keywords").val();
			var str = "";
			$
					.post(
							"/chinaunicom/task/gettask",
							{
								"currentPage" : obj,
								"pageNum" : 10,
								"condition" : keywords
							},
							function(data) {

								/*  alert( JSON.stringify(data) );
								 alert(data.data.data[0].file_path); */
								for (var i = 0; i < data.data.data.length; i++) {
									filename = data.data.data[i].file_path
											.substring(data.data.data[i].file_path
													.lastIndexOf("/") + 1);
									timeCoutnt = timeCount(new Date(
											data.data.data[i].submit_time)
											.getTime(), new Date(
											data.data.data[i].finish_time)
											.getTime());
									
									id=data.data.data[i].taskid;
									str += '<tr><td>' + id
									+ '</td>';
									str += '<td>' + data.data.data[i].topic
											+ '</td>';
									str += '<td>' + data.data.data[i].username
									+ '</td>';
							
									
									str += '<td style="text-align:left;">'
											+ filename + '</td>';
									str += '<td>' + data.data.data[i].datacode
											+ '</td>';
									str += '<td>'
											+ data.data.data[i].file_linenum
											+ '</td>';
									str += '<td>'
										+ data.data.data[i].submit_time
										+ '</td>';
									if (data.data.data[i].status == 1) {
										str += '<td><a href="javascript:void(0)" onclick="toSubmitQunfa('
												+ id
												+ ')">提交</a>'
												+ '<span style="color:blue;">|</span><a href="javascript:void(0)" onclick="createLable('
												+ id
												+ ')">标签</a><span style="color:blue;">|</span><a onclick="echart('+id+')" href="javascript:void(0)">详情</a></td>';
									}
									if (data.data.data[i].status == 2) {
										str += '<td><span>已提交</span><span style="color:blue;">|</span><a  href="javascript:void(0)" onclick="createLable('
												+ id
												+ ')">标签</a><span style="color:blue;">|</span><a onclick="echart('+id+')" href="javascript:void(0)">详情</a></td>';
									}

									str += '</tr>';
								}

								$("#content").html(str);

								pagination = "";
								if ((obj - 1) > 0) {
									pagination = '<li><a href="javascript:void(0)" onclick="getTaskByPage('
											+ (obj - 1) + ')">上一页</a> &nbsp;&nbsp;&nbsp;</li>&nbsp;&nbsp;&nbsp;';
								}
								if ((obj + 1) <= data.data.count) {
									pagination += '<li><a href="javascript:void(0)" onclick="getTaskByPage('
											+ (obj + 1) + ')">下一页</a> </li>';
								}
								$(".pagination").html(pagination);
 
							}, "json");
		}

		function timeCount(startTime, endTIme) {

			//计算相差毫秒数
			var sub = endTIme - startTime;
			//计算相差天数
			var days = Math.floor(sub / (24 * 3600 * 1000));
			//计算相差小时数
			var leave1 = sub % (24 * 3600 * 1000);//取去掉天数后剩下的秒数
			var hours = Math.floor(leave1 / (3600 * 1000));
			//计算相差分钟数
			var leave2 = leave1 % (3600 * 1000); //计算小时数后剩余的毫秒数
			var minutes = Math.floor(leave2 / (60 * 1000));
			//计算相差秒数
			var leave3 = leave2 % (60 * 1000); //计算分钟数后剩余的毫秒数
			var seconds = Math.round(leave3 / 1000);
			str = minutes + " 分" + seconds + " 秒";
			return str;
		}

		function tasksearch() {
			var keywords = $("#keywords").val();
			getTaskByPage(obj)
		}

		function toSubmitQunfa(obj) {
			$.post("/chinaunicom/task/submit", {
				"taskid" : obj
			}, function(data) {
				alert(data.data.resultDes);
				getTaskByPage(1);
			}, "json");
		}
		
		var taskid = "";
		function createLable(obj) {
			taskid = obj;
			$.post("/chinaunicom/task/getLabel",{"taskid":taskid},function(data){
				//alert(data.msg);
				var str="";
				for(var i=0;i<data.data.length;i++){
					str+=data.data[i].labelname+"|";
				}
				$("#label").val(str);
				$('#myModal').modal('show'); 
			},"json"); 
			
		}
		$(function(){
			$("#btn").click(function(){
				var label = $("#label").val();
				$('#myModal').modal('hide');
				$.post("/chinaunicom/task/addLabel",{"taskid":taskid,"labelName":label},function(data){
					alert(data.msg);
				},"json"); 
			});
		});
		//调用父页面 给父页面变量赋值
		function echart(obj){
			window.parent.echart(obj);
			urlrr = '/chinaunicom/query/index?pagename=Menu/detail.html';
			location.href=urlrr; 
		}
</script>
</body>
</html>
