package com.yc.xfm.entity.info;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yc.xfm.entity.IdEntity;
import com.yc.xfm.entity.base.ComInfo;
import com.yc.xfm.entity.base.Department;
import com.yc.xfm.entity.base.Region;
/**
 * 重点人员信息登记表
 * @author Administrator
 *
 */
@Entity  
@Table(name = "info_important_person")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE) 
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class ImportantPerson extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**基本信息*/
	
	private String xm;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date csrq ;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date cfsj;//初访时间
	private String tel;//联系电话
	private String sfzh;//身份证号
	private int xb; //0女 1 男
	private int hyzk;//婚姻 1未婚 2 已婚
	private int sqsfhl;//诉求是否合理 1.有理 2.无理 3.部分有理4.诉求过高
	private int jkzk;//健康状况
	private int xfrs;//信访人数
	private String sjzj;//三级终结
	private int fldj;//分类定级
	
	/*带外键*/
	@ManyToOne
	@JsonIgnoreProperties({"creater","updater"})
	private Department ssbm;//事涉部门
	@ManyToOne
	@JsonIgnoreProperties({"creater","updater"})
	private Department ssqtbm;//事涉其他部门
	@ManyToOne
	@JsonIgnoreProperties({"creater","updater"})
	private ComInfo mz;//民族
	@ManyToOne
	@JsonIgnoreProperties({"creater","updater"})
	private ComInfo whcd;//文化程度
	@ManyToOne
	private Region wtfsdq;//问题发生地区
	@ManyToOne
	private Region wtfsdjd;//问题发生街道
	private String wtfsdjtdz;//问题发生地具体地址
	@ManyToOne
	private Region hjdzq;//户籍地址区
	@ManyToOne
	private Region hjdzjd;//户籍地址街道
	private String hjdzjtdz;//户籍地址具体地址
	@ManyToOne
	private Region sjdzq;//实际地址区
	@ManyToOne
	private Region sjdzjd;//实际地址街道
	private String sjdzjtdz;//实际地址具体地址
	private String ffqk;//非访情况
	private String sksflqk;//三跨三分离情况
	private String zywt;//所反映的主要问题
	private String ywdcclqk;//以往调查处理情况
	private String yfczqk;//依法处置情况
	private String bfjzqk;//帮扶救助情况
	
	/**相关人物信息*/
	//配偶
	private String poxm;//姓名
	private String posfzh;//身份证号
	@ManyToOne
	private ComInfo pozzmm;//政治面貌
	private String podh1;//电话1
	private String podh2;//电话2
	private String pozz;//住址
	private String pozwjzy;//工作单位及职务
	//子女
	private String znxm;
	private String znsfzh;
	@ManyToOne
	private ComInfo znzzmm;
	private String zndh1;
	private String zndh2;
	private String znzz;
	private String znzwjzy;
	//其他家属
	private String qtjsxm;
	private String qtjssfzh;
	@ManyToOne
	private ComInfo qtjszzmm;
	private String qtjsdh1;
	private String qtjsdh2;
	private String qtjszz;
	private String qtjszwjzy;
	//群访同行人
	private String qftxrxm;
	private String qftxrsfzh;
	@ManyToOne
	private ComInfo qftxrzzmm;
	private String qftxrdh1;
	private String qftxrdh2;
	private String qftxrzz;
	private String qftxrzwjzy;
	
	/**区包保*/
	//领导
	private String qbbldxm;//姓名
	private String qbbldzw;//职务
	private String qbblddh1;//电话
	private String qbblddh2;
	//部门
		/*包保领导*/
	private String bmbbldxm;
	private String bmbbldzw;
	private String bmbblddh1;
	private String bmbblddh2;
		/*包保负责人*/
	private String bmbbfzrxm;
	private String bmbbfzrzw;
	private String bmbbfzrdh1;
	private String bmbbfzrdh2;
		/*共同包保负责人*/
	private String bmbbgtbbfzrxm;
	private String bmbbgtbbfzrzw;
	private String bmbbgtbbfzrdh1;
	private String bmbbgtbbfzrdh2;
	//属地镇、街
		/*包保领导*/
	private String zjbbldxm;
	private String zjbbldzw;
	private String zjbblddh1;
	private String zjbblddh2;
		/*包保负责人*/
	private String zjbbfzrxm;
	private String zjbbfzrzw;
	private String zjbbfzrdh1;
	private String zjbbfzrdh2;
		/*共同包保负责人*/
	private String zjbbgtbbfzrxm;
	private String zjbbgtbbfzrzw;
	private String zjbbgtbbfzrdh1;
	private String zjbbgtbbfzrdh2;
	//公安
		/*领导*/
		private String galdxm;
		private String galdzw;
		private String galddh1;
		private String galddh2;
		/*民警*/
		private String mjldxm;
		private String mjldzw;
		private String mjlddh1;
		private String mjlddh2;
	//移交稳控
		private String yjwkzt;//移交稳控主体
			/*负责人*/
		private String yjwkfzrxm;
		private String yjwkfzrzw;
		private String yjwkfzrdh1;
		private String yjwkfzrdh2;
	/**照片路径*/
		private String sfzzm;//身份证正面
		private String sfzfm;//身份证反面
		private String jz1;//近照1
		private String jz2;//近照2
		private String qtsmwt;//其他需要说明的问题
		public String getXm() {
			return xm;
		}
		public void setXm(String xm) {
			this.xm = xm;
		}
		public Date getCsrq() {
			return csrq;
		}
		public void setCsrq(Date csrq) {
			this.csrq = csrq;
		}
		public Date getCfsj() {
			return cfsj;
		}
		public void setCfsj(Date cfsj) {
			this.cfsj = cfsj;
		}
		public String getTel() {
			return tel;
		}
		public void setTel(String tel) {
			this.tel = tel;
		}
		public String getSfzh() {
			return sfzh;
		}
		public void setSfzh(String sfzh) {
			this.sfzh = sfzh;
		}
		public int getXb() {
			return xb;
		}
		public void setXb(int xb) {
			this.xb = xb;
		}
		public int getHyzk() {
			return hyzk;
		}
		public void setHyzk(int hyzk) {
			this.hyzk = hyzk;
		}
		public int getSqsfhl() {
			return sqsfhl;
		}
		public void setSqsfhl(int sqsfhl) {
			this.sqsfhl = sqsfhl;
		}
		public int getJkzk() {
			return jkzk;
		}
		public void setJkzk(int jkzk) {
			this.jkzk = jkzk;
		}
		public int getXfrs() {
			return xfrs;
		}
		public void setXfrs(int xfrs) {
			this.xfrs = xfrs;
		}
		public String getSjzj() {
			return sjzj;
		}
		public void setSjzj(String sjzj) {
			this.sjzj = sjzj;
		}
		public int getFldj() {
			return fldj;
		}
		public void setFldj(int fldj) {
			this.fldj = fldj;
		}
		public Department getSsbm() {
			return ssbm;
		}
		public void setSsbm(Department ssbm) {
			this.ssbm = ssbm;
		}
		public Department getSsqtbm() {
			return ssqtbm;
		}
		public void setSsqtbm(Department ssqtbm) {
			this.ssqtbm = ssqtbm;
		}
		public ComInfo getMz() {
			return mz;
		}
		public void setMz(ComInfo mz) {
			this.mz = mz;
		}
		public ComInfo getWhcd() {
			return whcd;
		}
		public void setWhcd(ComInfo whcd) {
			this.whcd = whcd;
		}
		public Region getWtfsdq() {
			return wtfsdq;
		}
		public void setWtfsdq(Region wtfsdq) {
			this.wtfsdq = wtfsdq;
		}
		public Region getWtfsdjd() {
			return wtfsdjd;
		}
		public void setWtfsdjd(Region wtfsdjd) {
			this.wtfsdjd = wtfsdjd;
		}
		public String getWtfsdjtdz() {
			return wtfsdjtdz;
		}
		public void setWtfsdjtdz(String wtfsdjtdz) {
			this.wtfsdjtdz = wtfsdjtdz;
		}
		public Region getHjdzq() {
			return hjdzq;
		}
		public void setHjdzq(Region hjdzq) {
			this.hjdzq = hjdzq;
		}
		public Region getHjdzjd() {
			return hjdzjd;
		}
		public void setHjdzjd(Region hjdzjd) {
			this.hjdzjd = hjdzjd;
		}
		public String getHjdzjtdz() {
			return hjdzjtdz;
		}
		public void setHjdzjtdz(String hjdzjtdz) {
			this.hjdzjtdz = hjdzjtdz;
		}
		public Region getSjdzq() {
			return sjdzq;
		}
		public void setSjdzq(Region sjdzq) {
			this.sjdzq = sjdzq;
		}
		public Region getSjdzjd() {
			return sjdzjd;
		}
		public void setSjdzjd(Region sjdzjd) {
			this.sjdzjd = sjdzjd;
		}
		public String getSjdzjtdz() {
			return sjdzjtdz;
		}
		public void setSjdzjtdz(String sjdzjtdz) {
			this.sjdzjtdz = sjdzjtdz;
		}
		public String getFfqk() {
			return ffqk;
		}
		public void setFfqk(String ffqk) {
			this.ffqk = ffqk;
		}
		public String getSksflqk() {
			return sksflqk;
		}
		public void setSksflqk(String sksflqk) {
			this.sksflqk = sksflqk;
		}
		public String getZywt() {
			return zywt;
		}
		public void setZywt(String zywt) {
			this.zywt = zywt;
		}
		public String getYwdcclqk() {
			return ywdcclqk;
		}
		public void setYwdcclqk(String ywdcclqk) {
			this.ywdcclqk = ywdcclqk;
		}
		public String getYfczqk() {
			return yfczqk;
		}
		public void setYfczqk(String yfczqk) {
			this.yfczqk = yfczqk;
		}
		public String getBfjzqk() {
			return bfjzqk;
		}
		public void setBfjzqk(String bfjzqk) {
			this.bfjzqk = bfjzqk;
		}
		public String getPoxm() {
			return poxm;
		}
		public void setPoxm(String poxm) {
			this.poxm = poxm;
		}
		public String getPosfzh() {
			return posfzh;
		}
		public void setPosfzh(String posfzh) {
			this.posfzh = posfzh;
		}
		public ComInfo getPozzmm() {
			return pozzmm;
		}
		public void setPozzmm(ComInfo pozzmm) {
			this.pozzmm = pozzmm;
		}
		public String getPodh1() {
			return podh1;
		}
		public void setPodh1(String podh1) {
			this.podh1 = podh1;
		}
		public String getPodh2() {
			return podh2;
		}
		public void setPodh2(String podh2) {
			this.podh2 = podh2;
		}
		public String getPozz() {
			return pozz;
		}
		public void setPozz(String pozz) {
			this.pozz = pozz;
		}
		public String getPozwjzy() {
			return pozwjzy;
		}
		public void setPozwjzy(String pozwjzy) {
			this.pozwjzy = pozwjzy;
		}
		public String getZnxm() {
			return znxm;
		}
		public void setZnxm(String znxm) {
			this.znxm = znxm;
		}
		public String getZnsfzh() {
			return znsfzh;
		}
		public void setZnsfzh(String znsfzh) {
			this.znsfzh = znsfzh;
		}
		public ComInfo getZnzzmm() {
			return znzzmm;
		}
		public void setZnzzmm(ComInfo znzzmm) {
			this.znzzmm = znzzmm;
		}
		public String getZndh1() {
			return zndh1;
		}
		public void setZndh1(String zndh1) {
			this.zndh1 = zndh1;
		}
		public String getZndh2() {
			return zndh2;
		}
		public void setZndh2(String zndh2) {
			this.zndh2 = zndh2;
		}
		public String getZnzz() {
			return znzz;
		}
		public void setZnzz(String znzz) {
			this.znzz = znzz;
		}
		public String getZnzwjzy() {
			return znzwjzy;
		}
		public void setZnzwjzy(String znzwjzy) {
			this.znzwjzy = znzwjzy;
		}
		public String getQtjsxm() {
			return qtjsxm;
		}
		public void setQtjsxm(String qtjsxm) {
			this.qtjsxm = qtjsxm;
		}
		public String getQtjssfzh() {
			return qtjssfzh;
		}
		public void setQtjssfzh(String qtjssfzh) {
			this.qtjssfzh = qtjssfzh;
		}
		public ComInfo getQtjszzmm() {
			return qtjszzmm;
		}
		public void setQtjszzmm(ComInfo qtjszzmm) {
			this.qtjszzmm = qtjszzmm;
		}
		public String getQtjsdh1() {
			return qtjsdh1;
		}
		public void setQtjsdh1(String qtjsdh1) {
			this.qtjsdh1 = qtjsdh1;
		}
		public String getQtjsdh2() {
			return qtjsdh2;
		}
		public void setQtjsdh2(String qtjsdh2) {
			this.qtjsdh2 = qtjsdh2;
		}
		public String getQtjszz() {
			return qtjszz;
		}
		public void setQtjszz(String qtjszz) {
			this.qtjszz = qtjszz;
		}
		public String getQtjszwjzy() {
			return qtjszwjzy;
		}
		public void setQtjszwjzy(String qtjszwjzy) {
			this.qtjszwjzy = qtjszwjzy;
		}
		public String getQftxrxm() {
			return qftxrxm;
		}
		public void setQftxrxm(String qftxrxm) {
			this.qftxrxm = qftxrxm;
		}
		public String getQftxrsfzh() {
			return qftxrsfzh;
		}
		public void setQftxrsfzh(String qftxrsfzh) {
			this.qftxrsfzh = qftxrsfzh;
		}
		public ComInfo getQftxrzzmm() {
			return qftxrzzmm;
		}
		public void setQftxrzzmm(ComInfo qftxrzzmm) {
			this.qftxrzzmm = qftxrzzmm;
		}
		public String getQftxrdh1() {
			return qftxrdh1;
		}
		public void setQftxrdh1(String qftxrdh1) {
			this.qftxrdh1 = qftxrdh1;
		}
		public String getQftxrdh2() {
			return qftxrdh2;
		}
		public void setQftxrdh2(String qftxrdh2) {
			this.qftxrdh2 = qftxrdh2;
		}
		public String getQftxrzz() {
			return qftxrzz;
		}
		public void setQftxrzz(String qftxrzz) {
			this.qftxrzz = qftxrzz;
		}
		public String getQftxrzwjzy() {
			return qftxrzwjzy;
		}
		public void setQftxrzwjzy(String qftxrzwjzy) {
			this.qftxrzwjzy = qftxrzwjzy;
		}
		public String getQbbldxm() {
			return qbbldxm;
		}
		public void setQbbldxm(String qbbldxm) {
			this.qbbldxm = qbbldxm;
		}
		public String getQbbldzw() {
			return qbbldzw;
		}
		public void setQbbldzw(String qbbldzw) {
			this.qbbldzw = qbbldzw;
		}
		public String getQbblddh1() {
			return qbblddh1;
		}
		public void setQbblddh1(String qbblddh1) {
			this.qbblddh1 = qbblddh1;
		}
		public String getQbblddh2() {
			return qbblddh2;
		}
		public void setQbblddh2(String qbblddh2) {
			this.qbblddh2 = qbblddh2;
		}
		public String getBmbbldxm() {
			return bmbbldxm;
		}
		public void setBmbbldxm(String bmbbldxm) {
			this.bmbbldxm = bmbbldxm;
		}
		public String getBmbbldzw() {
			return bmbbldzw;
		}
		public void setBmbbldzw(String bmbbldzw) {
			this.bmbbldzw = bmbbldzw;
		}
		public String getBmbblddh1() {
			return bmbblddh1;
		}
		public void setBmbblddh1(String bmbblddh1) {
			this.bmbblddh1 = bmbblddh1;
		}
		public String getBmbblddh2() {
			return bmbblddh2;
		}
		public void setBmbblddh2(String bmbblddh2) {
			this.bmbblddh2 = bmbblddh2;
		}
		public String getBmbbfzrxm() {
			return bmbbfzrxm;
		}
		public void setBmbbfzrxm(String bmbbfzrxm) {
			this.bmbbfzrxm = bmbbfzrxm;
		}
		public String getBmbbfzrzw() {
			return bmbbfzrzw;
		}
		public void setBmbbfzrzw(String bmbbfzrzw) {
			this.bmbbfzrzw = bmbbfzrzw;
		}
		public String getBmbbfzrdh1() {
			return bmbbfzrdh1;
		}
		public void setBmbbfzrdh1(String bmbbfzrdh1) {
			this.bmbbfzrdh1 = bmbbfzrdh1;
		}
		public String getBmbbfzrdh2() {
			return bmbbfzrdh2;
		}
		public void setBmbbfzrdh2(String bmbbfzrdh2) {
			this.bmbbfzrdh2 = bmbbfzrdh2;
		}
		public String getBmbbgtbbfzrxm() {
			return bmbbgtbbfzrxm;
		}
		public void setBmbbgtbbfzrxm(String bmbbgtbbfzrxm) {
			this.bmbbgtbbfzrxm = bmbbgtbbfzrxm;
		}
		public String getBmbbgtbbfzrzw() {
			return bmbbgtbbfzrzw;
		}
		public void setBmbbgtbbfzrzw(String bmbbgtbbfzrzw) {
			this.bmbbgtbbfzrzw = bmbbgtbbfzrzw;
		}
		public String getBmbbgtbbfzrdh1() {
			return bmbbgtbbfzrdh1;
		}
		public void setBmbbgtbbfzrdh1(String bmbbgtbbfzrdh1) {
			this.bmbbgtbbfzrdh1 = bmbbgtbbfzrdh1;
		}
		public String getBmbbgtbbfzrdh2() {
			return bmbbgtbbfzrdh2;
		}
		public void setBmbbgtbbfzrdh2(String bmbbgtbbfzrdh2) {
			this.bmbbgtbbfzrdh2 = bmbbgtbbfzrdh2;
		}
		public String getZjbbldxm() {
			return zjbbldxm;
		}
		public void setZjbbldxm(String zjbbldxm) {
			this.zjbbldxm = zjbbldxm;
		}
		public String getZjbbldzw() {
			return zjbbldzw;
		}
		public void setZjbbldzw(String zjbbldzw) {
			this.zjbbldzw = zjbbldzw;
		}
		public String getZjbblddh1() {
			return zjbblddh1;
		}
		public void setZjbblddh1(String zjbblddh1) {
			this.zjbblddh1 = zjbblddh1;
		}
		public String getZjbblddh2() {
			return zjbblddh2;
		}
		public void setZjbblddh2(String zjbblddh2) {
			this.zjbblddh2 = zjbblddh2;
		}
		public String getZjbbfzrxm() {
			return zjbbfzrxm;
		}
		public void setZjbbfzrxm(String zjbbfzrxm) {
			this.zjbbfzrxm = zjbbfzrxm;
		}
		public String getZjbbfzrzw() {
			return zjbbfzrzw;
		}
		public void setZjbbfzrzw(String zjbbfzrzw) {
			this.zjbbfzrzw = zjbbfzrzw;
		}
		public String getZjbbfzrdh1() {
			return zjbbfzrdh1;
		}
		public void setZjbbfzrdh1(String zjbbfzrdh1) {
			this.zjbbfzrdh1 = zjbbfzrdh1;
		}
		public String getZjbbfzrdh2() {
			return zjbbfzrdh2;
		}
		public void setZjbbfzrdh2(String zjbbfzrdh2) {
			this.zjbbfzrdh2 = zjbbfzrdh2;
		}
		public String getZjbbgtbbfzrxm() {
			return zjbbgtbbfzrxm;
		}
		public void setZjbbgtbbfzrxm(String zjbbgtbbfzrxm) {
			this.zjbbgtbbfzrxm = zjbbgtbbfzrxm;
		}
		public String getZjbbgtbbfzrzw() {
			return zjbbgtbbfzrzw;
		}
		public void setZjbbgtbbfzrzw(String zjbbgtbbfzrzw) {
			this.zjbbgtbbfzrzw = zjbbgtbbfzrzw;
		}
		public String getZjbbgtbbfzrdh1() {
			return zjbbgtbbfzrdh1;
		}
		public void setZjbbgtbbfzrdh1(String zjbbgtbbfzrdh1) {
			this.zjbbgtbbfzrdh1 = zjbbgtbbfzrdh1;
		}
		public String getZjbbgtbbfzrdh2() {
			return zjbbgtbbfzrdh2;
		}
		public void setZjbbgtbbfzrdh2(String zjbbgtbbfzrdh2) {
			this.zjbbgtbbfzrdh2 = zjbbgtbbfzrdh2;
		}
		public String getGaldxm() {
			return galdxm;
		}
		public void setGaldxm(String galdxm) {
			this.galdxm = galdxm;
		}
		public String getGaldzw() {
			return galdzw;
		}
		public void setGaldzw(String galdzw) {
			this.galdzw = galdzw;
		}
		public String getGalddh1() {
			return galddh1;
		}
		public void setGalddh1(String galddh1) {
			this.galddh1 = galddh1;
		}
		public String getGalddh2() {
			return galddh2;
		}
		public void setGalddh2(String galddh2) {
			this.galddh2 = galddh2;
		}
		public String getMjldxm() {
			return mjldxm;
		}
		public void setMjldxm(String mjldxm) {
			this.mjldxm = mjldxm;
		}
		public String getMjldzw() {
			return mjldzw;
		}
		public void setMjldzw(String mjldzw) {
			this.mjldzw = mjldzw;
		}
		public String getMjlddh1() {
			return mjlddh1;
		}
		public void setMjlddh1(String mjlddh1) {
			this.mjlddh1 = mjlddh1;
		}
		public String getMjlddh2() {
			return mjlddh2;
		}
		public void setMjlddh2(String mjlddh2) {
			this.mjlddh2 = mjlddh2;
		}
		public String getYjwkzt() {
			return yjwkzt;
		}
		public void setYjwkzt(String yjwkzt) {
			this.yjwkzt = yjwkzt;
		}
		public String getYjwkfzrxm() {
			return yjwkfzrxm;
		}
		public void setYjwkfzrxm(String yjwkfzrxm) {
			this.yjwkfzrxm = yjwkfzrxm;
		}
		public String getYjwkfzrzw() {
			return yjwkfzrzw;
		}
		public void setYjwkfzrzw(String yjwkfzrzw) {
			this.yjwkfzrzw = yjwkfzrzw;
		}
		public String getYjwkfzrdh1() {
			return yjwkfzrdh1;
		}
		public void setYjwkfzrdh1(String yjwkfzrdh1) {
			this.yjwkfzrdh1 = yjwkfzrdh1;
		}
		public String getYjwkfzrdh2() {
			return yjwkfzrdh2;
		}
		public void setYjwkfzrdh2(String yjwkfzrdh2) {
			this.yjwkfzrdh2 = yjwkfzrdh2;
		}
		public String getSfzzm() {
			return sfzzm;
		}
		public void setSfzzm(String sfzzm) {
			this.sfzzm = sfzzm;
		}
		public String getSfzfm() {
			return sfzfm;
		}
		public void setSfzfm(String sfzfm) {
			this.sfzfm = sfzfm;
		}
		public String getJz1() {
			return jz1;
		}
		public void setJz1(String jz1) {
			this.jz1 = jz1;
		}
		public String getJz2() {
			return jz2;
		}
		public void setJz2(String jz2) {
			this.jz2 = jz2;
		}
		public String getQtsmwt() {
			return qtsmwt;
		}
		public void setQtsmwt(String qtsmwt) {
			this.qtsmwt = qtsmwt;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		
		
			
}
