<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script>
function compare()
{
var d1 = document.getElementById("d1").value;
var d2 = document.getElementById("d2").value;
arr1 = d1.split("-");
arr2 = d2.split("-");
date1 = new Date(arr1[0],arr1[1],arr1[2]);
date2 = new Date(arr2[0],arr2[1],arr2[2]);
if(date2.getTime()<date1.getTime())
{
alert("不能比第一个日期小");
return false;
}
}
</script>
</head>
<body>
<input type="text" id="d1"/>
<input type="text" id="d2" nblur="compare()"/>
</body>
</html>