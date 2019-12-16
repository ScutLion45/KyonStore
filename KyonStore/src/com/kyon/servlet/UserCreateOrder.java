package com.kyon.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kyon.dao.OrderDao;
import com.kyon.daoImpl.OrderDaoImpl;

@WebServlet("/user-create-order")
public class UserCreateOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	OrderDao od = new OrderDaoImpl();
	protected void service(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		// ������������ʽ
		req.setCharacterEncoding("utf-8");
		// ������Ӧ�����ʽ����Ӧͷ
    	resp.setCharacterEncoding("utf-8");
    	resp.setContentType("text/html;charset=utf-8");
//    	resp.setHeader("Access-Control-Allow-Origin", "*");
    	
    	// ��ȡ������Ϣ
    	int oNum = 0;
    	String gId = "";
    	double gPrice = 0.0;
    	String uId = "";
    	int arg = 0;
    	if(req.getParameter("oNum")!=null && req.getParameter("oNum")!="") {
    		oNum = Integer.parseInt(req.getParameter("oNum"));
    	}
    	if(req.getParameter("gId")!=null) {
    		gId = req.getParameter("gId");
    	}
    	if(req.getParameter("gPrice")!=null) {
    		gPrice = Double.parseDouble(req.getParameter("gPrice"));
    	}
    	if(req.getParameter("uId")!=null) {
    		uId = req.getParameter("uId");
    	}
    	if(req.getParameter("arg")!=null && req.getParameter("arg")!="") {
    		arg = Integer.parseInt(req.getParameter("arg"));
    	}
    	
    	// ����������Ϣ
    	int success = 0;
    	try {
    		success = od.userCreateOrder(oNum, gId, gPrice, uId, arg);
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	// ��Ӧ������
    	String str="{\"success\":"+success+"}";
    	resp.getWriter().write(str);
    	
    	
	}

}
