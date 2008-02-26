<%@page buffer="none" session="false" import="org.opencms.main.*, org.opencms.frontend.templatetwo.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cms" uri="http://www.opencms.org/taglib/cms" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %><%
	CmsTemplateLayout cms = new CmsTemplateLayout(pageContext, request, response);
	pageContext.setAttribute("cms", cms);
%>
<cms:template element="head">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<title><cms:property name="Title" file="search" /></title>
	<meta name="description" content="<cms:property name="Description" file="search" />" />
	<meta name="keywords" content="<cms:property name="Keywords" file="search" />" />
	<meta http-equiv="Content-Type" content="text/html; charset=<cms:property name="content-encoding" file="search" default="<%= OpenCms.getSystemInfo().getDefaultEncoding() %>"/>" />
	<meta name="robots" content="index, follow" />
	<meta name="revisit-after" content="7 days" />

	<!-- insert stylesheets needed for the template -->
	<c:forEach items="${cms.stylesheets}" var="cssFile">
		<link href="<cms:link>${cssFile}</cms:link>" rel="stylesheet" type="text/css"/>		
	</c:forEach>
	
	<!-- insert individual stylesheet -->
	<c:set var="stylesheet"><cms:property name="stylesheet" file="search"/></c:set>
	<c:if test="${!empty stylesheet}">
		<link href="<cms:link>${stylesheet}</cms:link>" rel="stylesheet" type="text/css"/>
	</c:if>
	
	<link href="<cms:link>../resources/css/style.css?preset=${cms.presetPath}&amp;style=${cms.stylePath}</cms:link>" rel="stylesheet" type="text/css"/>
	<!--[if lte IE 6]>
		<link href="<cms:link>%(link.weak:../resources/css/patch_ie.css)</cms:link>" rel="stylesheet" type="text/css"/>
    <![endif]-->

	<cms:editable/>
</head>

<body>
	<div id="window">
		<div id="page">
	  		
			<!-- begin: header -->
		    <div id="header">
		    	<div id="topnav">
		    		<a href="#content" class="skip">Skip to Main Content</a>
		    		<c:if test="${!cms.options.value['TopNav'].isEmptyOrWhitespaceOnly}">
		    			<c:out value="${cms.options.value['TopNav']}" escapeXml="false" />
		    		</c:if>
		    	</div>
		    	<c:if test="${!cms.options.value['Header'].isEmptyOrWhitespaceOnly}">
		    		<c:out value="${cms.options.value['Header']}" escapeXml="false" />
		    	</c:if>
			</div>
			<!-- end: header -->
			
			<!-- begin: main navigation -->
			<c:choose>
				<c:when test="${!empty cms.styleValue['nav.main']}">
					<cms:include file="${cms.styleValue['nav.main']}"/>
				</c:when>
				<c:otherwise>
					<cms:include file="%(link.weak:../elements/menu/nav_style2.jsp)"/>
				</c:otherwise>
			</c:choose>
			<!-- end: main navigation -->
			
			<!-- begin: breadcrumb -->
			<div id="breadcrumb">
				<cms:include file="%(link.weak:../elements/breadcrumb.jsp)" />
			</div>
			<!-- end: breadcrumb -->
			
			<!-- begin: content area #main -->
			<div id="main">
			
			<!-- begin: left column -->
			<div id="col1">
				<div id="col1_content" class="clearfix">
					<c:if test="${cms.presetValue['column.left.visible'] == 'true'}">
						<!-- include the left navigation menu -->
						<c:if test="${cms.presetValue['nav.left.visible'] == 'true'}">
							<cms:include file="%(link.weak:../elements/menu/nav_left.jsp)" />
						</c:if>
						
						<!-- include the boxes on the left side -->
						<cms:include file="%(link.weak:../elements/boxes.jsp)">
							<cms:param name="orientation" value="left" />
							<cms:param name="config" value="${cms.configPath}" />
						</cms:include>
					</c:if>
				</div>
			</div>
			<!-- end: left column -->
			
			<!-- begin: right column -->
			<div id="col3">
				<div id="col3_content" class="clearfix">
					<c:catch>
						<c:set var="page" value="${cms:vfs(pageContext).readXml[cms:vfs(pageContext).context.uri]}" />
					</c:catch>
					<c:choose>
						<c:when test="${page.hasValue['rightcol']}" >
							<!-- include the right column of xml pages if available -->
							<cms:include element="rightcol" editable="true"/>
						</c:when>
						<c:otherwise>
							<!-- include the boxes on the right side -->
							<c:if test="${cms.presetValue['column.right.visible'] == 'true'}">
								<cms:include file="%(link.weak:../elements/boxes.jsp)">
									<cms:param name="orientation" value="right" />
									<cms:param name="config" value="${cms.configPath}" />
								</cms:include>
							</c:if>
						</c:otherwise>
					</c:choose>
               </div>
            </div>
            <!-- end: right column -->
			
			<!-- begin: center column -->
			<div id="col2">
				<div id="col2_content" class="clearfix">
					<!-- anchor for accessible link to main content -->
					<a id="content" name="content"></a>
</cms:template>
<cms:template element="body">
	<cms:include element="body" editable="true"/>
</cms:template>
<cms:template element="foot">
					<!-- include the boxes in the center -->
	                <cms:include file="%(link.weak:../elements/boxes.jsp)">
	                	<cms:param name="orientation" value="center" />
						<cms:param name="config" value="${cms.configPath}" />
					</cms:include>
				</div>
				<div class="clear">&nbsp;</div>
			</div>
			<!-- end: center column -->
		</div>
		<!-- end: content area -->
		
		<!-- begin: #footer -->
		<div id="footer">
			<c:if test="${!cms.options.value['Footer'].isEmptyOrWhitespaceOnly}">
		    	<c:out value="${cms.options.value['Footer']}" escapeXml="false" />
		    </c:if>
		</div>
		<!-- end: #footer -->
		
	</div>
	<!-- end: #page -->
</div>
</body>
</html>
</cms:template>
