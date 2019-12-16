package com.kyon.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kyon.dao.UserDao;
import com.kyon.daoImpl.UserDaoImpl;

@WebServlet("/user-edit-profile")
public class UserEditProfile extends HttpServlet {
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
    	String uMail = "";
    	String uPwd = "";
    	double uBalance = 0.0;
    	if(req.getParameter("uId")!=null)
    		uId = req.getParameter("uId");
    	if(req.getParameter("uMail")!=null)
    		uMail = req.getParameter("uMail");
    	if(req.getParameter("uPwd")!=null)
    		uPwd = req.getParameter("uPwd");
    	if(req.getParameter("uBalance")!=null)
    		uBalance = Double.parseDouble(req.getParameter("uBalance"));
    	
    	System.out.println("user_edit_profile["+uId+","+uMail+","+uPwd+","+uBalance+"]");
    	
    	// ����������Ϣ
    	int success = 0;
    	try {
    		int flag = ud.editProfile(uId, uMail, uPwd, uBalance);
    		System.out.println("flag="+flag);
    		if(flag==1)
    			success = 1;
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	// ��Ӧ������
    	String str="{\"success\":"+success+"}";
    	resp.getWriter().write(str);
	}

}
