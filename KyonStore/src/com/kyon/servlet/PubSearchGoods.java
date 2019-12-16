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
import com.kyon.dao.PubDao;
import com.kyon.daoImpl.PubDaoImpl;
import com.kyon.pojo.Goods;

@WebServlet("/pub-search-goods")
public class PubSearchGoods extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PubDao pd = new PubDaoImpl();
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
    	String pUid = "";
    	int gType = 0;
    	String gPubTime = "";
    	int gState = 0;
    	String gName = "";
    	    	
    	if(req.getParameter("pUid")!=null)
        	pUid = req.getParameter("pUid");
    	if(req.getParameter("gType")!=null)
    		gType = Integer.parseInt(req.getParameter("gType"));
    	if(req.getParameter("gPubTime")!=null)
    		gPubTime = req.getParameter("gPubTime");
    	if(req.getParameter("gState")!=null)
    		gState = Integer.parseInt(req.getParameter("gState"));
    	if(req.getParameter("gName")!=null)
      	 	gName = req.getParameter("gName");
    	
    	System.out.println("["+pUid+","+gType+","+gPubTime+","+gState+","+gName+"]");
//    	System.out.println(name);
    	
    	// 处理请求信息
    	List<Goods> lg = new ArrayList<Goods>();
    	try {
			lg = pd.searchGoods(pUid, gType, gPubTime, gState, gName);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
    	// 响应处理结果
    	resp.getWriter().write(new Gson().toJson(lg));
	}
	
	
}
