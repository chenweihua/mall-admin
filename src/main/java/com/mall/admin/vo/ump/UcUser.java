package com.mall.admin.vo.ump;

import java.util.Date;

/**
 * @author Administrator
 *
 */
public class UcUser{
    private Integer id;

    private String mobilephone;

    private String password;

    private String token;

    private Integer collegeid;

    private String realname;

    private String username;

    private Date createtime;

    private Date updatetime;

    private String email;

    private Byte sex;

    private Date birthday;

    private Byte phonevalidate;

    private Byte emailvalidate;

    private Integer source;

    private Byte isdel;
    
	//产品id
	public String productId;
	//随机id
	public String randomId;
	//根据随机id生成的随机token，现在的app的要求是token不能固定
	public String randomToken;
	//随机token的生成时间
	private Date tokencreatetime;
	
	
	//
	private String headimgurl;//头像
	private Integer cityId;//城市id
	private Integer areaId;//区域id
	private String openId;
	
	
//	public volatile transient UserStatDO userStat;
    
    

    public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone == null ? null : mobilephone.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public Integer getCollegeid() {
        return collegeid;
    }

    public void setCollegeid(Integer collegeid) {
        this.collegeid = collegeid;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Byte getPhonevalidate() {
        return phonevalidate;
    }

    public void setPhonevalidate(Byte phonevalidate) {
        this.phonevalidate = phonevalidate;
    }

    public Byte getEmailvalidate() {
        return emailvalidate;
    }

    public void setEmailvalidate(Byte emailvalidate) {
        this.emailvalidate = emailvalidate;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Byte getIsdel() {
        return isdel;
    }

    public void setIsdel(Byte isdel) {
        this.isdel = isdel;
    }


    public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getRandomId() {
		return randomId;
	}

	public void setRandomId(String randomId) {
		this.randomId = randomId;
	}

	public String getRandomToken() {
		return randomToken;
	}

	public void setRandomToken(String randomToken) {
		this.randomToken = randomToken;
	}

	public Date getTokencreatetime() {
        return tokencreatetime;
    }

    public void setTokencreatetime(Date tokencreatetime) {
        this.tokencreatetime = tokencreatetime;
    }
}