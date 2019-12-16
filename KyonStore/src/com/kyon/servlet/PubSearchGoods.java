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
		// ������������ʽ
		req.setCharacterEncoding("utf-8");
		// ������Ӧ�����ʽ����Ӧͷ
    	resp.setCharacterEncoding("utf-8");
    	resp.setContentType("text/html;charset=utf-8");
//    	resp.setHeader("Access-Control-Allow-Origin", "*");
    	
    	// ��ȡ������Ϣ
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
    	
    	// ����������Ϣ
    	List<Goods> lg = new ArrayList<Goods>();
    	try {
			lg = pd.searchGoods(pUid, gType, gPubTime, gState, gName);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
    	// ��Ӧ������
    	resp.getWriter().write(new Gson().toJson(lg));
	}
	
	
}
