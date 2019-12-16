package com.kyon.dao;

import java.util.List;

import com.kyon.pojo.*;

public interface PubDao {
	public List<Goods> searchGoods(String pUid, int gType, String gPubTime, int gState, String gName);
	public int editGoods(String gId, String gName, String gInfo, int gType, double gPrice, String gImg);
	public int offGoods(String gId);
	public int createGoods(String gId, String gName, String gInfo, int gType, double gPrice, String gImg, String pUid);
	public int editProfile(String pUid, String pPwd, String pInfo);
}
