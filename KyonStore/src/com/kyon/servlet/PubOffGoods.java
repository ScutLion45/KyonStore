package com.kyon.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kyon.dao.PubDao;
import com.kyon.daoImpl.PubDaoImpl;

@WebServlet("/pub-off-goods")
public class PubOffGoods extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PubDao pd = new PubDaoImpl();
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 设置请求编码格式
		req.setCharacterEncoding("utf-8");
		// 设置响应编码格式和响应头
    	resp.setCharacterEncoding("utf-8");
    	resp.setContentType("text/html;charset=utf-8");
//    	resp.setHeader("Access-Control-Allow-Origin", "*");
    	
    	// 获取请求信息
    	String gId = "";
    	if(req.getParameter("gId")!=null)
        	gId = req.getParameter("gId"); 
    	
    	System.out.println("pub_off_goods["+gId+"]");
    	
    	// 处理请求信息
    	int gs = 0;
    	try {
    		gs = pd.offGoods(gId);
    		System.out.println("gState: "+gs);
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	// 响应处理结果
    	String str="{\"gState\":"+gs+"}";
    	resp.getWriter().write(str);
	}

}
