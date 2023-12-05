<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR" %>
<%@ page import="java.sql.*" %>
<% request.setCharacterEncoding("EUC-KR"); %>
<%@ include file="config.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="euc-kr" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>게시판 글 보기</title>
    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" />
    <link href="css/styles.css" rel="stylesheet" />
    <link rel="icon" type="image/x-icon" href="assets/img/favicon.png" />
    <script data-search-pseudo-elements defer src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/js/all.min.js" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/feather-icons/4.28.0/feather.min.js" crossorigin="anonymous"></script>
</head>

<body class="nav-fixed">
<%
    String pno = request.getParameter("pno");
    Connection con = null;
    PreparedStatement pstmt = null;
    
    String sql = "select * from board where boardid = " + pno;

    try {
        Class.forName(driverName);
        con = DriverManager.getConnection(dbURL, dbID, dbPW);
        pstmt = con.prepareStatement(sql);        
        ResultSet result = pstmt.executeQuery();
        while (result.next()) {
%>
 
    <%@ include file="topbar.jsp" %>
    <div id="layoutSidenav">
    <%@ include file="sidebar.jsp" %>

        <div id="layoutSidenav_content">
            <main>
                <header class="page-header page-header-compact page-header-light border-bottom bg-white mb-4">
                    <div class="container-xl px-4">
                        <div class="page-header-content">
                            <div class="row align-items-center justify-content-between pt-3">
                                <div class="col-auto mb-3">
                                    <h1 class="page-header-title fw-600">
                                        <div class="page-header-icon"><i data-feather="alert-circle"></i></div>
                                        게시글 관리
                                    </h1>
                                </div>
                            </div>
                        </div>
                    </div>
                </header>

                <div class="container-xl px-4 mt-4">
                    <div class="card mb-3">
                        <div class="card-header text-center fw-600 text-black">글 내용</div>
                        <div class="card-body text-center">
                            <div class="table-responsive">
                                <table class="table mb-3">
                                    <tr class="border-bottom-table">
                                        <th class="text-center p-2 text-black">구분</th>
                                        <th class="text-center p-2 text-black" colspan="2">내용</th>
                                    </tr>
                                    <tr>
                                        <th class="text-center p-2">제목</th>
                                        <td class="text-center fw-light p-2">
                                            <%= result.getString("title") %>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th class="text-center p-2">등록일</th>
                                        <td class="text-center fw-light p-2">
                                            <%= result.getString("regdate") %>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th class="text-center p-2">내용</th>
                                        <td class="text-center fw-light p-2">
                                            <%= result.getString("content") %>
                                        </td>
                                    </tr>
                                </table>
							</div>
                           
                            <div class="no-print"> 
                                <button type="button" class="btn btn-outline-primary" onclick="location.href = 'board_edit.jsp?pno=<%= result.getString("boardid") %>';">수정</button>
                                <button type="button" data-bs-toggle="modal" data-bs-target="#deleteModal" class="btn btn-outline-primary">삭제</button>
                                    <!-- Modal-->
                                    <div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="deleteModalLabel">알림창</h5>
                                                    <button class="btn-close" type="button" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body" >
                                                정말 삭제하시겠습니까?
                                                </div>
                                                <div class="modal-footer p-1">
                                                    <button class="btn btn-text-primary me-2" type="button" onclick="location.href = 'board_delete.jsp?pno=<%= result.getString("boardid") %>';">YES</button>
                                                    <button class="btn btn-text-primary me-2" type="button" data-bs-dismiss="modal">NO</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                            </div>
                                    <%
                                        }
                                        result.close();
                                        }
                                        catch (Exception e) {
                                            out.println("[ordertbl] 테이블 조회에 문제가 있습니다. <hr>");
                                            out.println(e.toString());
                                            e.printStackTrace();
                                        }
                                        finally {
                                            if (pstmt != null) pstmt.close();
                                            if (con != null) con.close();
                                        }
                                    %>

                        </div> 
                    </div>      
                </div>
            </main>

        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
    <script src="js/scripts.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/simple-datatables@latest" crossorigin="anonymous"></script>
    <script src="js/datatables/datatables-simple-demo.js"></script>
</body>
</html>
