package com.kyon.dao;

import java.util.List;

import com.kyon.pojo.*;

public interface UserDao {
	public List<Goods> loadLatestGoods(int gType);
	public List<Goods> searchGoods(int gType, String gPubTime, String gName);
	public int browseGoods(String uId, String gId);
	public int editProfile(String uId, String uMail, String uPwd, double uBalance);
	public List<Browse> loadBrowse(String uId);
}
