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

@WebServlet("/pub-load-order")
public class PubLoadOrder extends HttpServlet {
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
    	String pUid = "";
    	if(req.getParameter("pUid")!=null) {
    		pUid = req.getParameter("pUid");
    	}
    	
    	// ����������Ϣ
    	List<Order> lo = new ArrayList<Order>();
    	try {
    		lo = od.pubLoadOrder(pUid);
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	

    	// ��Ӧ������
    	resp.getWriter().write(new Gson().toJson(lo));
    	
    	
	}

}
