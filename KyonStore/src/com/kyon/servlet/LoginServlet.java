package com.kyon.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.kyon.dao.LoginDao;
import com.kyon.daoImpl.LoginDaoImpl;
import com.kyon.pojo.Publisher;
import com.kyon.pojo.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	LoginDao ld = new LoginDaoImpl();
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// ������������ʽ
		req.setCharacterEncoding("utf-8");
		// ������Ӧ�����ʽ����Ӧͷ
    	resp.setCharacterEncoding("utf-8");
    	resp.setContentType("text/html;charset=utf-8");
//    	resp.setHeader("Access-Control-Allow-Origin", "*");
    	
    	// ��ȡ������Ϣ
    	String name = "";
    	String pwd = "";
    	int arg = 0;
    	if(req.getParameter("name")!=null)
        	name = req.getParameter("name");
    	if(req.getParameter("pwd")!=null)
        	pwd = req.getParameter("pwd");
    	if(req.getParameter("arg")!=null)
    		arg = Integer.parseInt(req.getParameter("arg"));

    	// HttpSession hs = req.getSession();
    	// ����������Ϣ
    	if(arg==1) {
    		User u = null;
    		try {
    			u = ld.checkUPwd(name, pwd);
    		} catch(Exception e) {
    			e.printStackTrace();
    		}
    		System.out.println("�û���¼["+name+","+pwd+"]");
    		// ��Ӧ������
    		resp.getWriter().write(new Gson().toJson(u));
    	} else if(arg==2) {
    		Publisher p = null;
    		try {
    			p = ld.checkPPwd(name, pwd);
    		} catch(Exception e) {
    			e.printStackTrace();
    		}
    		System.out.println("���з���¼["+name+","+pwd+"]");
    		// ��Ӧ������
    		resp.getWriter().write(new Gson().toJson(p));
    	}
    	
    	// ��Ӧ������
    	// String str="{\"success\":"+success+"}";
    	// resp.getWriter().write(str);
    	
	}
	

}
