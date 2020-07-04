<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>フォロー情報　一覧</h2>
        <table id="follow_list">
            <caption>【${follows_count} フォロー中】</caption>
            <tbody>
                <tr>
                    <th class="follow_name">氏名</th>
                    <th class="follow_report">日報</th>
                    <th class="follow_action">操作</th>
                </tr>
                <c:forEach var="follow" items="${follows}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="follow_name"><c:out value="${follow.followed.name}" /></td>
                        <td class="follow_report"><a href="<c:url value='/follows/reports?id=${follow.followed.id}' />">日報一覧を見る</a></td>
                        <td class="follow_action"><a href="<c:url value='/follows/destroy?id=${follow.id}' />">フォローを解除する</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <table id="follower_list">
            <caption>【${followers_count} フォロワー】</caption>
            <tbody>
                <tr>
                    <th class="follower_name">氏名</th>
                    <th class="follower_action">操作</th>
                </tr>
                <c:forEach var="follower" items="${followers}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="follower_name"><c:out value="${follower.follow.name}" /></td>
                        <c:choose>
                            <c:when test="${mutual_list[status.count - 1] == 1}">
                                <td class="follower_action">フォローしています</td>
                            </c:when>
                            <c:otherwise>
                                <td class="follower_action"><a href="<c:url value='/follows/create?id=${follower.follow.id}' />">フォローする</a></td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <div class="clear"></div>
    </c:param>
</c:import>