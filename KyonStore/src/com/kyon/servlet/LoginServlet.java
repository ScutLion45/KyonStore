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
		// 设置请求编码格式
		req.setCharacterEncoding("utf-8");
		// 设置响应编码格式和响应头
    	resp.setCharacterEncoding("utf-8");
    	resp.setContentType("text/html;charset=utf-8");
//    	resp.setHeader("Access-Control-Allow-Origin", "*");
    	
    	// 获取请求信息
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
    	// 处理请求信息
    	if(arg==1) {
    		User u = null;
    		try {
    			u = ld.checkUPwd(name, pwd);
    		} catch(Exception e) {
    			e.printStackTrace();
    		}
    		System.out.println("用户登录["+name+","+pwd+"]");
    		// 响应处理结果
    		resp.getWriter().write(new Gson().toJson(u));
    	} else if(arg==2) {
    		Publisher p = null;
    		try {
    			p = ld.checkPPwd(name, pwd);
    		} catch(Exception e) {
    			e.printStackTrace();
    		}
    		System.out.println("发行方登录["+name+","+pwd+"]");
    		// 响应处理结果
    		resp.getWriter().write(new Gson().toJson(p));
    	}
    	
    	// 响应处理结果
    	// String str="{\"success\":"+success+"}";
    	// resp.getWriter().write(str);
    	
	}
	

}
