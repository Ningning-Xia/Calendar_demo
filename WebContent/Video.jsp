<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.*, java.util.*, model.Event "%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Manage Your Events</title>

<script type="text/javascript" src="fancybox/lib/jquery-1.9.0.min.js"></script>
<script type="text/javascript"
	src="fancybox/lib/jquery.mousewheel-3.0.6.pack.js"></script>
<link rel="stylesheet"
	href="fancybox/source/jquery.fancybox.css?v=2.1.4" type="text/css"
	media="screen" />
<script type="text/javascript"
	src="fancybox/source/jquery.fancybox.pack.js?v=2.1.4"></script>
<!--  <link rel="StyleSheet" href="styles/bootstrap.css" type="text/css" /> -->
<link rel="StyleSheet" href="styles/calendar.css" type="text/css" />
<script type="text/javascript">
	$(document).ready(function() {
		$(".fancybox").fancybox();
	});
</script>
</head>
<body>
	<jsp:include page="header.jsp" />
	<br>
	<br>
	<div id="main_div">
	<H2>
			<font color="white"> Your Video List: </font>
		</H2>

		<form id="form1" method="post"
			enctype="multipart/form-data">
			<%
			String eid = (String)request.getAttribute("eid");
				if (request.getAttribute("eid") != null) {
			%>
			<input type="hidden" name="testname" value="test1" /> 
			<input	type="hidden" name="eid" id="eid"
				value="<%=eid%>" />
			<%
				}
			%>
			<fieldset>
				<legend>Video Upload:</legend>
				Select a file to upload: <input class="button" type="file"
					name="file" size="50" /> <br /> <input type="submit"
					class="button" value="Upload File" onclick="form.action='UploadVideo?eid=<%=eid%>';"/> <br>
				<%
					if (request.getAttribute("wrongType") != null) {
						String wrongType = (String) request.getAttribute("wrongType");
						//out.println(wrongType);
				%>
				<font color="red"><%=wrongType%> </font>
				<%
					}

					if (request.getAttribute("uploadSucceed") != null) {
						String uploadSucceed = (String) request
								.getAttribute("uploadSucceed");
						//out.println(wrongType);

				%>
				<font color="blue"><%=uploadSucceed%> </font>
				<%
					}
				%>
			</fieldset>
		</form>

	
	
		<%
			if (request.getAttribute("video") != null) {

				String videoName = (String) request.getAttribute("video");
				if (!videoName.equals("null")) {
		%>
		
		<fieldset>
			<legend>
			Your Video
			</legend>
			<form id="form2" method="post">
			<table width = "600px">
				<%
					String eid1 = (String)request.getAttribute("eid");
					if (request.getAttribute("eid") != null) {
				%>
				<input type="hidden" name="eid"
					value="<%=request.getAttribute("eid")%>" />
				<%
					}
				%>
				<input type="hidden" name="key" value="<%=videoName%>" />
				
				<tr>
				<td width="20%" height = "30px">  <a class = "button" value = "View" href = "ViewVideoServlet?eid=<%=eid1%>&&key=<%=videoName%>">  View Video</a></td> 
					<td>
						<h4><%=videoName%></h4>
					</td>
				
					
				</tr>
				<tr>	
				<td width="20%" height = "30px">  <a class = "button" value = "Transform" href = "TransVideoServlet?eid=<%=eid1%>&&key=<%=videoName%>">Transform</a></td>
				
				<td><select name="presetID">
						<option value="1351620000001-000060">Generic 320x240</option>
						<option value="1351620000001-000050">Generic 360p 4:3</option>
						<option value="1351620000001-000040">Generic 360p 16:9</option>
						<option value="1351620000001-000030">Generic 480p 4:3</option>
						<option value="1351620000001-000020">Generic 480p 16:9</option>
						<option value="1351620000001-000010">Generic 720p</option>
						<option value="1351620000001-000001">Generic 1080p</option>
						<option value="1351620000001-100010">iPhone4</option>
						<option value="1351620000001-100020">iPhone4S</option>
						<option value="1351620000001-100030">iPhone3GS</option>
						<option value="1351620000001-100040">iPod Touch</option>
						<option value="1351620000001-100050">Apple TV 2G</option>
						<option value="1351620000001-100060">Apple TV 3G</option>
						<option value="1351620000001-100070">Web</option>
						<option value="1351620000001-100080">KindleFireHD</option>
						<option value="1351620000001-100090">KindleFire</option>
						<option value="1351620000001-100100">KindleFire</option>
						<option value="1351620000001-200010">HLS 2M</option>
						<option value="1351620000001-200020">HLS 1.5M</option>
						<option value="1351620000001-200030">HLS 1M</option>
						<option value="1351620000001-200040">HLS 600k</option>
						<option value="1351620000001-200050">HLS 400k</option>
				</select></td>
				
					
					</tr>
					
					 <% if (request.getAttribute("url") != null) {
						String url = (String) request.getAttribute("url");
						%> 
						
						
			<tr> 
				<td width="20%"> <input type="button" class = "button" value="Download" /> </td> 
				<td > <a href="<%=url%>" title="<%=url%>"><%=url%></a>
						<%
					}
				%> 
				</td>
				</tr>
			</table>
		</form>
		<%
			}
			}
		%>
		
		</fieldset>

<br>
<br>

		<%
			String videoname, svideo, dvideo, s, d, src, fsrc = null;
			if ((videoname = (String) request.getAttribute("view")) != null) {
				s = (String) request.getAttribute("stream");
				d = (String) request.getAttribute("download");
				svideo = s + videoname + "\"";
				dvideo = d + videoname + "\"";
				s = s + "\"";
		%>
		<fieldset>
			<legend>
				Watch Video
				<%=videoname%>
			</legend>
			Stream:
			<%=svideo%>
			<br /> Download:
			<%=dvideo%>
			<br />
			<div id="container"></div>
			<script type='text/javascript' src="jwplayer/jwplayer.js"></script>
			<script type='text/javascript'>
				jwplayer("container").setup({
					file :
			<%=svideo%>
				,
					provider : "rtmp",
					streamer :
			<%=s%>
				,
					width : "792",
					height : "594",
					modes : [ {
						type : "flash",
						src : "jwplayer/jwplayer.flash.swf"
					}, {
						type : "html5",
						config : {
							file :
			<%=dvideo%>
				,
							provider : "video"
						}
					} ]
				});
			</script>
		</fieldset>
		<%
			}
		%>
	</div>


</body>
</html>