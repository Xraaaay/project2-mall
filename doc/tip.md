

## 小程序

### 选作 验证码注册重置

> 五分钟内有效，不可重复获取
>
> 使用过后失效
>
> 电话做正则验证

**实现方式：**

使用redisson整合spring-boot作为redis客户端，连接Linux中运行的redis服务器

将手机号作为key， 验证码作为value存入redis 设置存活时间



### 商品

#### 首页

**`搜索`**

新的搜索记录放前面

重复搜索放前面



点进去 `更多好物` 数据库中只有部分类目是有商品的

* 洗护--毛巾
* 杂货--文具
* 婴童--服饰
* 服装--内裤
* 志趣--魔兽
* 配件--功能箱包
* **居家--基本都有**
* 餐厨--锅具
* 饮食--糕点

`品牌制造商` ， 有商品的：

* MUJI制造商
* Ralph Lauren
* 罗莱制造商

`专题` ， 有商品的

* 设计师们推荐的应季好物



专题下留言--上传图片显示不出来		---> <span style='color:red;background:yellow;font-size:文字大小;font-family:字体;'>改前端代码</span>

* 第二次留言会重定向到登录 老师bug--<span style='color:red;background:yellow;font-size:文字大小;font-family:字体;'>不要复现</span>





## 后台

### 推广管理

#### 广告管理

/admin/ad/create	活动链接正则验证

#### 优惠券管理

/admin/coupon/create	对最低消费，满减金额，限领，数量，有效期做参数校验

#### 专题管理

/admin/topic/create    商品低价，阅读量（数字+k）参数校验

