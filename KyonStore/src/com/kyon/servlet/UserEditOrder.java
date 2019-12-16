package com.kyon.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kyon.dao.OrderDao;
import com.kyon.daoImpl.OrderDaoImpl;

@WebServlet("/user-edit-order")
public class UserEditOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	OrderDao od = new OrderDaoImpl();
	protected void service(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		// 设置请求编码格式
		req.setCharacterEncoding("utf-8");
		// 设置响应编码格式和响应头
    	resp.setCharacterEncoding("utf-8");
    	resp.setContentType("text/html;charset=utf-8");
//    	resp.setHeader("Access-Control-Allow-Origin", "*");
    	
    	// 获取请求信息
    	String oId = "";
    	int oNum = 0;
    	if(req.getParameter("oId")!=null) {
    		oId = req.getParameter("oId");
    	}
    	if(req.getParameter("oNum")!=null && req.getParameter("oNum")!="") {
    		oNum = Integer.parseInt(req.getParameter("oNum"));
    	}
    	
    	// 处理请求信息
    	int success = 0;
    	try {
    		success = od.userEditOrder(oId, oNum);
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	// 响应处理结果
    	String str="{\"success\":"+success+"}";
    	resp.getWriter().write(str);
    	
	}

}
