<%@ page isELIgnored="false"%>
<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>数据提取平台</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="../assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="../assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="../assets/css/main-min.css" rel="stylesheet" type="text/css" />
</head>
<body>

<div class="header">

    <div class="dl-title">
        <!--<img src="/chinapost/Public/assets/img/top.png">-->
    </div>

    <div class="dl-log">欢迎您，<span class="dl-log-user">${sessionScope.user.name}</span><a href="/chinaunicom/user/toLogin" title="退出系统" class="dl-log-quit">[退出]</a>
    </div>
</div>
<div class="content">
    <div class="dl-main-nav">
        <div class="dl-inform"><div class="dl-inform-title"><s class="dl-inform-icon dl-up"></s></div></div>
        <ul id="J_Nav"  class="nav-list ks-clear">
            <li class="nav-item dl-selected"><div class="nav-item-inner nav-home">系统管理</div></li>
			<li class="nav-item dl-selected"><div class="nav-item-inner nav-order">黑名单管理</div></li>
			<li class="nav-item dl-selected"><div class="nav-item-inner nav-order">用户活跃轨迹</div></li>
        </ul>
    </div>
    <ul id="J_NavContent" class="dl-tab-conten">
    </ul>
</div>
<script type="text/javascript" src="../assets/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="../assets/js/bui-min.js"></script>
<script type="text/javascript" src="../assets/js/common/main.js"></script>
<script type="text/javascript" src="../assets/js/config-min.js"></script>
 <script>
    BUI.use('common/main',function(){
        var config = [{id:'1',menu:[{text:'菜单列表',
        	         items:[{id:'12',text:'数据提取',href:'../query/index?pagename=Node/index.html'},
		{id:'3',text:'任务列表',href:'../query/index?pagename=Menu/index.jsp'},
		{id:'4',text:'手动添加',href:'../query/index?pagename=Menu/add.html'}]}]},
		{id:'7',homePage : '9',
		menu:[{text:'黑名单管理', items:[{id:'9',text:'黑名单添加',
		href:'../query/index?pagename=black/add.html'},
		{id:'11',text:'批量添加',href:'../query/index?pagename=black/batchadd.html'},
		{id:'10',text:'黑名单查询',href:'../query/index?pagename=black/search.html'}]}]},
		{id:'20',homePage : '9',
			menu:[{text:'用户活跃轨迹', items:[{id:'9',text:'用户活跃轨迹',
			href:'../query/index?pagename=User/usercycle.jsp'},{id:'50',text:'维度统计',
				href:'../query/index?pagename=User/weidu.html'} ,{id:'51',text:'标签生成',
					href:'../query/index?pagename=User/createweidu.html'} ]}]}];
        new PageUtil.MainPage({
            modulesConfig : config
        }); 
    });
     
    var jobid=""
    function echart(obj){
    	jobid=obj;
    }
    
    
</script> 
<div style="text-align:center;">
</div>
</body>
</html>

