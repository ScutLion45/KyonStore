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
import com.kyon.dao.OrderDao;
import com.kyon.daoImpl.OrderDaoImpl;
import com.kyon.pojo.Order;

@WebServlet("/user-load-order")
public class UserLoadOrder extends HttpServlet {
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
    	String uId = "";
    	int arg = 0;
    	if(req.getParameter("uId")!=null) {
    		uId = req.getParameter("uId");
    	}
    	if(req.getParameter("arg")!=null && req.getParameter("arg")!="") {
    		arg = Integer.parseInt(req.getParameter("arg"));
    	}
    	
    	// 处理请求信息
    	List<Order> lo = new ArrayList<Order>();
    	try {
    		lo = od.userLoadOrder(uId, arg);
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	

    	// 响应处理结果
    	resp.getWriter().write(new Gson().toJson(lo));
    	
    	
	}

}
