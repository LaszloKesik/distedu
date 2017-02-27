<%
boolean showDescription = false;
if(request.getSession().getAttribute("showDescription")!= null){
	showDescription = (Boolean) request.getSession().getAttribute("showDescription");
}
boolean showPrice = false;
if(request.getSession().getAttribute("showPrice")!= null){
	showPrice = (Boolean) request.getSession().getAttribute("showPrice");
}
%>

<h2>Course list preferences</h2>
<form action="set_preferences.do" method="GET">
	<input type="checkbox" name="showName" value="true" checked disabled>Name<br>
	<br> <input type="checkbox" name="showDescription" value="true" <% if(showDescription) out.print("checked"); %>>Description<br>
	<br> <input type="checkbox" name="showPrice" value="true" <% if(showPrice) out.print("checked"); %>>Price<br>
	<br> <input type="submit" value="Submit">
</form>
