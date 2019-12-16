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
import com.kyon.pojo.Goods;

@WebServlet("/load-latest-goods")
public class UserLoadLatestGoods extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserDao ud = new UserDaoImpl();
	protected void service(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		// 设置请求编码格式
		req.setCharacterEncoding("utf-8");
		// 设置响应编码格式和响应头
    	resp.setCharacterEncoding("utf-8");
    	resp.setContentType("text/html;charset=utf-8");
//    	resp.setHeader("Access-Control-Allow-Origin", "*");
    	
    	// 获取请求信息   
    	int gType = 0;
    	if(req.getParameter("gType")!=null)
    		gType = Integer.parseInt(req.getParameter("gType"));
    	
    	// 处理请求信息
    	List<Goods> lg = new ArrayList<Goods>();
    	try {
    		lg = ud.loadLatestGoods(gType);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	

    	// 响应处理结果
    	resp.getWriter().write(new Gson().toJson(lg));
	}

}
