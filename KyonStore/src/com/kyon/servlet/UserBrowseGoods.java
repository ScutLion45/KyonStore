package com.kyon.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kyon.dao.UserDao;
import com.kyon.daoImpl.UserDaoImpl;

@WebServlet("/browse-goods")
public class UserBrowseGoods extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserDao ud = new UserDaoImpl();
	protected void service(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		// ������������ʽ
		req.setCharacterEncoding("utf-8");
		// ������Ӧ�����ʽ����Ӧͷ
    	resp.setCharacterEncoding("utf-8");
    	resp.setContentType("text/html;charset=utf-8");
//    	resp.setHeader("Access-Control-Allow-Origin", "*");
    	
    	// ��ȡ������Ϣ 
    	String uId = "";
    	String gId = "";
    	if(req.getParameter("uId")!=null)
    		uId = req.getParameter("uId");
    	if(req.getParameter("gId")!=null)
    		gId = req.getParameter("gId");
    	
    	// ����������Ϣ
    	int success = 0;
    	try {
    		success = ud.browseGoods(uId, gId);
    		System.out.println(success);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	// ��Ӧ������
    	String str="{\"success\":"+success+"}";
    	resp.getWriter().write(str);
	}

}
