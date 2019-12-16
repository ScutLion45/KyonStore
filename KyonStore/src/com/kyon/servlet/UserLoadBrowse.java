package com.kyon.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.kyon.dao.UserDao;
import com.kyon.daoImpl.UserDaoImpl;
import com.kyon.pojo.Browse;

@WebServlet("/user-load-browse")
public class UserLoadBrowse extends HttpServlet {
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
    	if(req.getParameter("uId")!=null) {
    		uId = req.getParameter("uId");
    	}
    	
    	// ����������Ϣ
		List<Browse> lb = new ArrayList<Browse>();
		try {
			lb = ud.loadBrowse(uId);
		} catch (Exception e) {
			e.printStackTrace();
		}

    	// ��Ӧ������
    	resp.getWriter().write(new Gson().toJson(lb));	
	}

}
