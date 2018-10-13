package com.yc.core.util;

public class UploadConfig{
	public static final int MEMBER_ALBUM=100;//100 游客空间批量上传图片
	public static final int IMG_RECOMMEND=21;//11 商城推荐上传图片
	public static final int IMG_GOODS=22;//11 商城推荐上传图片
	public String path;
	public String dir;//文件夹
	public String dirParent;
	public long maxSize=0;//单位b
	public boolean delSource=false;
	public UploadConfig(){
		
	}
	public UploadConfig(int type){
		switch (type) {
			default:
				break;
		}
	}
}