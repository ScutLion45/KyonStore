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
		// ������������ʽ
		req.setCharacterEncoding("utf-8");
		// ������Ӧ�����ʽ����Ӧͷ
    	resp.setCharacterEncoding("utf-8");
    	resp.setContentType("text/html;charset=utf-8");
//    	resp.setHeader("Access-Control-Allow-Origin", "*");
    	
    	// ��ȡ������Ϣ
    	String gId = "";
    	if(req.getParameter("gId")!=null)
        	gId = req.getParameter("gId"); 
    	
    	System.out.println("pub_off_goods["+gId+"]");
    	
    	// ����������Ϣ
    	int gs = 0;
    	try {
    		gs = pd.offGoods(gId);
    		System.out.println("gState: "+gs);
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	// ��Ӧ������
    	String str="{\"gState\":"+gs+"}";
    	resp.getWriter().write(str);
	}

}
