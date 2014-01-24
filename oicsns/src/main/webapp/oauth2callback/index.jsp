<%@page import="java.io.*,java.net.*,org.json.simple.parser.JSONParser,org.json.simple.JSONObject"%>
<%@include file="config.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>OAuth</title>
	</head>
	<body>
		<%
			//System.setProperty("proxySet","true");
			//System.setProperty("proxyHost","prxsrv.oic.jp");
			//System.setProperty("proxyPort","8080");
			HttpURLConnection con=(HttpURLConnection)new URL("https://accounts.google.com/o/oauth2/token").openConnection();
			con.setDoOutput(true);
			//con.setRequestProperty("Referer",REDIRECT_URI);
			con.setRequestMethod("POST");
			PrintWriter pw=new PrintWriter(con.getOutputStream());
			pw.print("code="+request.getParameter("code")+"&client_id="+CLIENT_ID+"&client_secret="+CLIENT_SECRET+"&redirect_uri="+REDIRECT_URI+"&grant_type=authorization_code");
			pw.close();
			BufferedReader buf=new BufferedReader(new InputStreamReader(con.getInputStream()));
			String str=new String();
			String tmp;
			while(null!=(tmp=buf.readLine())){
				str+=tmp+"\n";
			}
			buf.close();
			con.disconnect();
		%>
		<pre><%=str%></pre>
		<%
			con=(HttpURLConnection)new URL("https://www.googleapis.com/oauth2/v1/userinfo?access_token="+((JSONObject)new JSONParser().parse(str)).get("access_token")).openConnection();
			buf=new BufferedReader(new InputStreamReader(con.getInputStream()));
			str="";
			while(null!=(tmp=buf.readLine())){
				str+=tmp+"\n";
			}
			buf.close();
			con.disconnect();
			email=((JSONObject)new JSONParser().parse(str)).get("email").toString();
		%>
		<pre><%=str%></pre>
		<%=email%>
	</body>
</html>