package com.kyon.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kyon.dao.PubDao;
import com.kyon.daoImpl.PubDaoImpl;

@WebServlet("/pub-edit-profile")
public class PubEditProfile extends HttpServlet {
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
    	String pUid = "";
    	String pPwd = "";
    	String pInfo = "";
    	
    	if(req.getParameter("pUid")!=null)
    		pUid = req.getParameter("pUid");
    	if(req.getParameter("pPwd")!=null)
    		pPwd = req.getParameter("pPwd");
    	if(req.getParameter("pInfo")!=null)
    		pInfo = req.getParameter("pInfo");
    	
    	System.out.println("pub_edit_profile["+pUid+","+pPwd+","+pInfo+"]");
    	
    	// ����������Ϣ
    	int success = 0;
    	try {
    		int flag = pd.editProfile(pUid, pPwd, pInfo);
    		System.out.println("flag="+flag);
    		if(flag==1)
    			success = 1;
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	// ��Ӧ������
    	String str="{\"success\":"+success+"}";
    	resp.getWriter().write(str);
    	
	}

}
