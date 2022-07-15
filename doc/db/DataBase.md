 # 

## market_ad 广告表

| 字段名      | 类型     | 长度 | not null | 注释                                 |
| ----------- | -------- | ---- | -------- | ------------------------------------ |
| id          | int      | 11   | TRUE     |                                      |
| name        | varchar  | 63   | TRUE     | 广告标题                             |
| link        | varchar  | 255  | TRUE     | 所广告的商品页面或者活动页面链接地址 |
| url         | varchar  | 255  | TRUE     | 广告宣传图片                         |
| position    | tinyint  | 3    | FALSE    | 广告位置：1则是首页                  |
| content     | varchar  | 255  | FALSE    | 活动内容                             |
| start_time  | datetime | 0    | FALSE    | 广告开始时间                         |
| end_time    | datetime | 0    | FALSE    | 广告结束时间                         |
| enabled     | tinyint  | 1    | FALSE    | 是否启动                             |
| add_time    | datetime | 0    | FALSE    | 创建时间                             |
| update_time | datetime | 0    | FALSE    | 更新时间                             |
| deleted     | tinyint  | 1    | FALSE    | 逻辑删除                             |



## market_address 收货地址表

| id             | int      | 11   | TRUE  |                    |
| -------------- | -------- | ---- | ----- | ------------------ |
| name           | varchar  | 63   | TRUE  | 收货人名称         |
| user_id        | int      | 11   | TRUE  | 用户表的用户ID     |
| province       | varchar  | 63   | TRUE  | 行政区域表的省ID   |
| city           | varchar  | 63   | TRUE  | 行政区域表的市ID   |
| county         | varchar  | 63   | TRUE  | 行政区域表的区县ID |
| address_detail | varchar  | 127  | TRUE  | 详细收货地址       |
| area_code      | char     | 6    | FALSE | 地区编码           |
| postal_code    | char     | 6    | FALSE | 邮政编码           |
| tel            | varchar  | 20   | TRUE  | 手机号码           |
| is_default     | tinyint  | 1    | TRUE  | 是否默认地址       |
| add_time       | datetime | 0    | FALSE | 创建时间           |
| update_time    | datetime | 0    | FALSE | 更新时间           |
| deleted        | tinyint  | 1    | FALSE | 逻辑删除           |



## market_admin 管理员表

| id              | int      | 11   | TRUE  |                    |
| --------------- | -------- | ---- | ----- | ------------------ |
| username        | varchar  | 63   | TRUE  | 管理员名称         |
| password        | varchar  | 63   | TRUE  | 管理员密码         |
| last_login_ip   | varchar  | 63   | FALSE | 最近一次登录IP地址 |
| last_login_time | datetime | 0    | FALSE | 最近一次登录时间   |
| avatar          | varchar  | 255  | FALSE | 头像图片           |
| add_time        | datetime | 0    | FALSE | 创建时间           |
| update_time     | datetime | 0    | FALSE | 更新时间           |
| deleted         | tinyint  | 1    | FALSE | 逻辑删除           |
| role_ids        | varchar  | 127  | FALSE | 角色列表           |



## market_aftersale 售后表

| id           | int      | 11   | TRUE  |                                                              |
| ------------ | -------- | ---- | ----- | ------------------------------------------------------------ |
| aftersale_sn | varchar  | 63   | FALSE | 售后编号                                                     |
| order_id     | int      | 11   | TRUE  | 订单ID                                                       |
| user_id      | int      | 11   | TRUE  | 用户ID                                                       |
| type         | smallint | 6    | FALSE | 售后类型，0是未收货退款，1是已收货（无需退货）退款，2用户退货退款 |
| reason       | varchar  | 31   | FALSE | 退款原因                                                     |
| amount       | decimal  | 10   | FALSE | 退款金额                                                     |
| pictures     | varchar  | 1023 | FALSE | 退款凭证图片链接数组                                         |
| comment      | varchar  | 511  | FALSE | 退款说明                                                     |
| status       | smallint | 6    | FALSE | 售后状态，0是可申请，1是用户已申请，2是管理员审核通过，3是管理员退款成功，4是管理员审核拒绝，5是用户已取消 |
| handle_time  | datetime | 0    | FALSE | 管理员操作时间                                               |
| add_time     | datetime | 0    | FALSE | 添加时间                                                     |
| update_time  | datetime | 0    | FALSE | 更新时间                                                     |
| deleted      | tinyint  | 1    | FALSE | 逻辑删除                                                     |



## market_brand 品牌商表

| id          | int      | 11   | TRUE  |                                  |
| ----------- | -------- | ---- | ----- | -------------------------------- |
| name        | varchar  | 255  | TRUE  | 品牌商名称                       |
| desc        | varchar  | 255  | TRUE  | 品牌商简介                       |
| pic_url     | varchar  | 255  | TRUE  | 品牌商页的品牌商图片             |
| sort_order  | tinyint  | 3    | FALSE |                                  |
| floor_price | decimal  | 10   | FALSE | 品牌商的商品低价，仅用于页面展示 |
| add_time    | datetime | 0    | FALSE | 创建时间                         |
| update_time | datetime | 0    | FALSE | 更新时间                         |
| deleted     | tinyint  | 1    | FALSE | 逻辑删除                         |



## market_cart 购物车商品表

| 字段           | 类型     | 长度 | 小数 | not null | 注释                             |
| -------------- | -------- | ---- | ---- | -------- | -------------------------------- |
| id             | int      | 11   | 0    | TRUE     |                                  |
| user_id        | int      | 11   | 0    | FALSE    | 用户表的用户ID                   |
| goods_id       | int      | 11   | 0    | FALSE    | 商品表的商品ID                   |
| goods_sn       | varchar  | 63   | 0    | FALSE    | 商品编号                         |
| goods_name     | varchar  | 127  | 0    | FALSE    | 商品名称                         |
| product_id     | int      | 11   | 0    | FALSE    | 商品货品表的货品ID               |
| price          | decimal  | 10   | 2    | FALSE    | 商品货品的价格                   |
| number         | smallint | 5    | 0    | FALSE    | 商品货品的数量                   |
| specifications | varchar  | 1023 | 0    | FALSE    | 商品规格值列表，采用JSON数组格式 |
| checked        | tinyint  | 1    | 0    | FALSE    | 购物车中商品是否选择状态         |
| pic_url        | varchar  | 255  | 0    | FALSE    | 商品图片或者商品货品图片         |
| add_time       | datetime | 0    | 0    | FALSE    | 创建时间                         |
| update_time    | datetime | 0    | 0    | FALSE    | 更新时间                         |
| deleted        | tinyint  | 1    | 0    | FALSE    | 逻辑删除                         |



## market_category 类目表

| id          | int      | 11   | TRUE  |                            |
| ----------- | -------- | ---- | ----- | -------------------------- |
| name        | varchar  | 63   | TRUE  | 类目名称                   |
| keywords    | varchar  | 1023 | TRUE  | 类目关键字，以JSON数组格式 |
| desc        | varchar  | 255  | FALSE | 类目广告语介绍             |
| pid         | int      | 11   | TRUE  | 父类目ID                   |
| icon_url    | varchar  | 255  | FALSE | 类目图标                   |
| pic_url     | varchar  | 255  | FALSE | 类目图片                   |
| level       | varchar  | 255  | FALSE |                            |
| sort_order  | tinyint  | 3    | FALSE | 排序                       |
| add_time    | datetime | 0    | FALSE | 创建时间                   |
| update_time | datetime | 0    | FALSE | 更新时间                   |
| deleted     | tinyint  | 1    | FALSE | 逻辑删除                   |



## market_collect 收藏表

| id          | int      | 11   | TRUE  |                                                          |
| ----------- | -------- | ---- | ----- | -------------------------------------------------------- |
| user_id     | int      | 11   | TRUE  | 用户表的用户ID                                           |
| value_id    | int      | 11   | TRUE  | 如果type=0，则是商品ID；如果type=1，则是专题ID           |
| type        | tinyint  | 3    | TRUE  | 收藏类型，如果type=0，则是商品ID；如果type=1，则是专题ID |
| add_time    | datetime | 0    | FALSE | 创建时间                                                 |
| update_time | datetime | 0    | FALSE | 更新时间                                                 |
| deleted     | tinyint  | 1    | FALSE | 逻辑删除                                                 |



## market_comment 评论表

| id            | int      | 11   | TRUE  |                                                              |
| ------------- | -------- | ---- | ----- | ------------------------------------------------------------ |
| value_id      | int      | 11   | TRUE  | 如果type=0，则是商品评论；如果是type=1，则是专题评论。       |
| type          | tinyint  | 3    | TRUE  | 评论类型，如果type=0，则是商品评论；如果是type=1，则是专题评论； |
| content       | varchar  | 1023 | FALSE | 评论内容                                                     |
| admin_content | varchar  | 511  | FALSE | 管理员回复内容                                               |
| user_id       | int      | 11   | TRUE  | 用户表的用户ID                                               |
| has_picture   | tinyint  | 1    | FALSE | 是否含有图片                                                 |
| pic_urls      | varchar  | 1023 | FALSE | 图片地址列表，采用JSON数组格式                               |
| star          | smallint | 6    | FALSE | 评分， 1-5                                                   |
| add_time      | datetime | 0    | FALSE | 创建时间                                                     |
| update_time   | datetime | 0    | FALSE | 更新时间                                                     |
| deleted       | tinyint  | 1    | FALSE | 逻辑删除                                                     |



## market_coupon 优惠券信息及规则表

| id          | int      | 11   | 0    | TRUE  |                                                              |
| ----------- | -------- | ---- | ---- | ----- | ------------------------------------------------------------ |
| name        | varchar  | 63   | 0    | TRUE  | 优惠券名称                                                   |
| desc        | varchar  | 127  | 0    | FALSE | 优惠券介绍，通常是显示优惠券使用限制文字                     |
| tag         | varchar  | 63   | 0    | FALSE | 优惠券标签，例如新人专用                                     |
| total       | int      | 11   | 0    | TRUE  | 优惠券数量，如果是0，则是无限量                              |
| discount    | decimal  | 10   | 2    | FALSE | 优惠金额，                                                   |
| min         | decimal  | 10   | 2    | FALSE | 最少消费金额才能使用优惠券。                                 |
| limit       | smallint | 6    | 0    | FALSE | 用户领券限制数量，如果是0，则是不限制；默认是1，限领一张.    |
| type        | smallint | 6    | 0    | FALSE | 优惠券赠送类型，如果是0则通用券，用户领取；如果是1，则是注册赠券；如果是2，则是优惠券码兑换； |
| status      | smallint | 6    | 0    | FALSE | 优惠券状态，如果是0则是正常可用；如果是1则是过期; 如果是2则是下架。 |
| goods_type  | smallint | 6    | 0    | FALSE | 商品限制类型，如果0则全商品，如果是1则是类目限制，如果是2则是商品限制。 |
| goods_value | varchar  | 1023 | 0    | FALSE | 商品限制值，goods_type如果是0则空集合，如果是1则是类目集合，如果是2则是商品集合。 |
| code        | varchar  | 63   | 0    | FALSE | 优惠券兑换码                                                 |
| time_type   | smallint | 6    | 0    | FALSE | 有效时间限制，如果是0，则基于领取时间的有效天数days；如果是1，则start_time和end_time是优惠券有效期； |
| days        | smallint | 6    | 0    | FALSE | 基于领取时间的有效天数days。                                 |
| start_time  | datetime | 0    | 0    | FALSE | 使用券开始时间                                               |
| end_time    | datetime | 0    | 0    | FALSE | 使用券截至时间                                               |
| add_time    | datetime | 0    | 0    | FALSE | 创建时间                                                     |
| update_time | datetime | 0    | 0    | FALSE | 更新时间                                                     |
| deleted     | tinyint  | 1    | 0    | FALSE | 逻辑删除                                                     |



## market_coupon_user 优惠券用户使用表

| id          | int      | 11   | TRUE  |                                                              |
| ----------- | -------- | ---- | ----- | ------------------------------------------------------------ |
| user_id     | int      | 11   | TRUE  | 用户ID                                                       |
| coupon_id   | int      | 11   | TRUE  | 优惠券ID                                                     |
| status      | smallint | 6    | FALSE | 使用状态, 如果是0则未使用；如果是1则已使用；如果是2则已过期；如果是3则已经下架； |
| used_time   | datetime | 0    | FALSE | 使用时间                                                     |
| start_time  | datetime | 0    | FALSE | 有效期开始时间                                               |
| end_time    | datetime | 0    | FALSE | 有效期截至时间                                               |
| order_id    | int      | 11   | FALSE | 订单ID                                                       |
| add_time    | datetime | 0    | FALSE | 创建时间                                                     |
| update_time | datetime | 0    | FALSE | 更新时间                                                     |
| deleted     | tinyint  | 1    | FALSE | 逻辑删除                                                     |



## market_feedback 意见反馈表

| id          | int      | 11   | TRUE  |                                |
| ----------- | -------- | ---- | ----- | ------------------------------ |
| user_id     | int      | 11   | TRUE  | 用户表的用户ID                 |
| username    | varchar  | 63   | TRUE  | 用户名称                       |
| mobile      | varchar  | 20   | TRUE  | 手机号                         |
| feed_type   | varchar  | 63   | TRUE  | 反馈类型                       |
| content     | varchar  | 1023 | TRUE  | 反馈内容                       |
| status      | int      | 3    | TRUE  | 状态                           |
| has_picture | tinyint  | 1    | FALSE | 是否含有图片                   |
| pic_urls    | varchar  | 1023 | FALSE | 图片地址列表，采用JSON数组格式 |
| add_time    | datetime | 0    | FALSE | 创建时间                       |
| update_time | datetime | 0    | FALSE | 更新时间                       |
| deleted     | tinyint  | 1    | FALSE | 逻辑删除                       |



## market_footprint 用户浏览足迹表

| id          | int      | 11   | TRUE  |                |
| ----------- | -------- | ---- | ----- | -------------- |
| user_id     | int      | 11   | TRUE  | 用户表的用户ID |
| goods_id    | int      | 11   | TRUE  | 浏览商品ID     |
| add_time    | datetime | 0    | FALSE | 创建时间       |
| update_time | datetime | 0    | FALSE | 更新时间       |
| deleted     | tinyint  | 1    | FALSE | 逻辑删除       |

## market_goods 商品基本信息表

| id            | int      | 11   | 0    | TRUE  |                                                |
| ------------- | -------- | ---- | ---- | ----- | ---------------------------------------------- |
| goods_sn      | varchar  | 63   | 0    | TRUE  | 商品编号                                       |
| name          | varchar  | 127  | 0    | TRUE  | 商品名称                                       |
| category_id   | int      | 11   | 0    | FALSE | 商品所属类目ID                                 |
| brand_id      | int      | 11   | 0    | FALSE |                                                |
| gallery       | varchar  | 1023 | 0    | FALSE | 商品宣传图片列表，采用JSON数组格式             |
| keywords      | varchar  | 255  | 0    | FALSE | 商品关键字，采用逗号间隔                       |
| brief         | varchar  | 255  | 0    | FALSE | 商品简介                                       |
| is_on_sale    | tinyint  | 1    | 0    | FALSE | 是否上架                                       |
| sort_order    | smallint | 4    | 0    | FALSE |                                                |
| pic_url       | varchar  | 255  | 0    | FALSE | 商品页面商品图片                               |
| share_url     | varchar  | 255  | 0    | FALSE | 商品分享海报                                   |
| is_new        | tinyint  | 1    | 0    | FALSE | 是否新品首发，如果设置则可以在新品首发页面展示 |
| is_hot        | tinyint  | 1    | 0    | FALSE | 是否人气推荐，如果设置则可以在人气推荐页面展示 |
| unit          | varchar  | 31   | 0    | FALSE | 商品单位，例如件、盒                           |
| counter_price | decimal  | 10   | 2    | FALSE | 专柜价格                                       |
| retail_price  | decimal  | 10   | 2    | FALSE | 零售价格                                       |
| detail        | text     | 0    | 0    | FALSE | 商品详细介绍，是富文本格式                     |
| add_time      | datetime | 0    | 0    | FALSE | 创建时间                                       |
| update_time   | datetime | 0    | 0    | FALSE | 更新时间                                       |
| deleted       | tinyint  | 1    | 0    | FALSE | 逻辑删除                                       |

## market_goods_attribute 商品参数表

| id          | int      | 11   | TRUE  |                |
| ----------- | -------- | ---- | ----- | -------------- |
| goods_id    | int      | 11   | TRUE  | 商品表的商品ID |
| attribute   | varchar  | 255  | TRUE  | 商品参数名称   |
| value       | varchar  | 255  | TRUE  | 商品参数值     |
| add_time    | datetime | 0    | FALSE | 创建时间       |
| update_time | datetime | 0    | FALSE | 更新时间       |
| deleted     | tinyint  | 1    | FALSE | 逻辑删除       |

## market_goods_product 商品货品表

| id             | int      | 11   | 0    | TRUE  |                                  |
| -------------- | -------- | ---- | ---- | ----- | -------------------------------- |
| goods_id       | int      | 11   | 0    | TRUE  | 商品表的商品ID                   |
| specifications | varchar  | 1023 | 0    | TRUE  | 商品规格值列表，采用JSON数组格式 |
| price          | decimal  | 10   | 2    | TRUE  | 商品货品价格                     |
| number         | int      | 11   | 0    | TRUE  | 商品货品数量                     |
| url            | varchar  | 125  | 0    | FALSE | 商品货品图片                     |
| add_time       | datetime | 0    | 0    | FALSE | 创建时间                         |
| update_time    | datetime | 0    | 0    | FALSE | 更新时间                         |
| deleted        | tinyint  | 1    | 0    | FALSE | 逻辑删除                         |

## market_goods_specification 商品规格表

| id            | int      | 11   | TRUE  |                |
| ------------- | -------- | ---- | ----- | -------------- |
| goods_id      | int      | 11   | TRUE  | 商品表的商品ID |
| specification | varchar  | 255  | TRUE  | 商品规格名称   |
| value         | varchar  | 255  | TRUE  | 商品规格值     |
| pic_url       | varchar  | 255  | TRUE  | 商品规格图片   |
| add_time      | datetime | 0    | FALSE | 创建时间       |
| update_time   | datetime | 0    | FALSE | 更新时间       |
| deleted       | tinyint  | 1    | FALSE | 逻辑删除       |

## market_groupon 团购活动表

| id                | int      | 11   | TRUE  |                                                              |
| ----------------- | -------- | ---- | ----- | ------------------------------------------------------------ |
| order_id          | int      | 11   | TRUE  | 关联的订单ID                                                 |
| groupon_id        | int      | 11   | FALSE | 如果是开团用户，则groupon_id是0；如果是参团用户，则groupon_id是团购活动ID |
| rules_id          | int      | 11   | TRUE  | 团购规则ID，关联market_groupon_rules表ID字段                 |
| user_id           | int      | 11   | TRUE  | 用户ID                                                       |
| share_url         | varchar  | 255  | FALSE | 团购分享图片地址                                             |
| creator_user_id   | int      | 11   | TRUE  | 开团用户ID                                                   |
| creator_user_time | datetime | 0    | FALSE | 开团时间                                                     |
| status            | smallint | 6    | FALSE | 团购活动状态，开团未支付则0，开团中则1，开团失败则2          |
| add_time          | datetime | 0    | TRUE  | 创建时间                                                     |
| update_time       | datetime | 0    | FALSE | 更新时间                                                     |
| deleted           | tinyint  | 1    | FALSE | 逻辑删除                                                     |

## market_groupon_rules 团购规则表

| id              | int      | 11   | TRUE  |                                                             |
| --------------- | -------- | ---- | ----- | ----------------------------------------------------------- |
| goods_id        | int      | 11   | TRUE  | 商品表的商品ID                                              |
| goods_name      | varchar  | 127  | TRUE  | 商品名称                                                    |
| pic_url         | varchar  | 255  | FALSE | 商品图片或者商品货品图片                                    |
| discount        | decimal  | 63   | TRUE  | 优惠金额                                                    |
| discount_member | int      | 11   | TRUE  | 达到优惠条件的人数                                          |
| expire_time     | datetime | 0    | FALSE | 团购过期时间                                                |
| status          | smallint | 6    | FALSE | 团购规则状态，正常上线则0，到期自动下线则1，管理手动下线则2 |
| add_time        | datetime | 0    | TRUE  | 创建时间                                                    |
| update_time     | datetime | 0    | FALSE | 更新时间                                                    |
| deleted         | tinyint  | 1    | FALSE | 逻辑删除                                                    |

## market_issue 常见问题表

| id          | int      | 11   | TRUE  |          |
| ----------- | -------- | ---- | ----- | -------- |
| question    | varchar  | 255  | FALSE | 问题标题 |
| answer      | varchar  | 255  | FALSE | 问题答案 |
| add_time    | datetime | 0    | FALSE | 创建时间 |
| update_time | datetime | 0    | FALSE | 更新时间 |
| deleted     | tinyint  | 1    | FALSE | 逻辑删除 |

## market_keyword 关键字表

| id          | int      | 11   | TRUE  |                  |
| ----------- | -------- | ---- | ----- | ---------------- |
| keyword     | varchar  | 127  | TRUE  | 关键字           |
| url         | varchar  | 255  | TRUE  | 关键字的跳转链接 |
| is_hot      | tinyint  | 1    | TRUE  | 是否是热门关键字 |
| is_default  | tinyint  | 1    | TRUE  | 是否是默认关键字 |
| sort_order  | int      | 11   | TRUE  | 排序             |
| add_time    | datetime | 0    | FALSE | 创建时间         |
| update_time | datetime | 0    | FALSE | 更新时间         |
| deleted     | tinyint  | 1    | FALSE | 逻辑删除         |

## market_log 操作日志表

| id          | int      | 11   | TRUE  |                                      |
| ----------- | -------- | ---- | ----- | ------------------------------------ |
| admin       | varchar  | 45   | FALSE | 管理员                               |
| ip          | varchar  | 45   | FALSE | 管理员地址                           |
| type        | int      | 11   | FALSE | 操作分类                             |
| action      | varchar  | 45   | FALSE | 操作动作                             |
| status      | tinyint  | 1    | FALSE | 操作状态                             |
| result      | varchar  | 127  | FALSE | 操作结果，或者成功消息，或者失败消息 |
| comment     | varchar  | 255  | FALSE | 补充信息                             |
| add_time    | datetime | 0    | FALSE | 创建时间                             |
| update_time | datetime | 0    | FALSE | 更新时间                             |
| deleted     | tinyint  | 1    | FALSE | 逻辑删除                             |

## market_notice 通知表

| id          | int      | 11   | TRUE  |                                              |
| ----------- | -------- | ---- | ----- | -------------------------------------------- |
| title       | varchar  | 63   | FALSE | 通知标题                                     |
| content     | varchar  | 511  | FALSE | 通知内容                                     |
| admin_id    | int      | 11   | FALSE | 创建通知的管理员ID，如果是系统内置通知则是0. |
| add_time    | datetime | 0    | FALSE | 创建时间                                     |
| update_time | datetime | 0    | FALSE | 更新时间                                     |
| deleted     | tinyint  | 1    | FALSE | 逻辑删除                                     |

## market_notice_admin 通知管理员表

| id           | int      | 11   | TRUE  |                                  |
| ------------ | -------- | ---- | ----- | -------------------------------- |
| notice_id    | int      | 11   | FALSE | 通知ID                           |
| notice_title | varchar  | 63   | FALSE | 通知标题                         |
| admin_id     | int      | 11   | FALSE | 接收通知的管理员ID               |
| read_time    | datetime | 0    | FALSE | 阅读时间，如果是NULL则是未读状态 |
| add_time     | datetime | 0    | FALSE | 创建时间                         |
| update_time  | datetime | 0    | FALSE | 更新时间                         |
| deleted      | tinyint  | 1    | FALSE | 逻辑删除                         |

## market_order 订单表

| id               | int      | 11   | 0    | TRUE  |                                                              |
| ---------------- | -------- | ---- | ---- | ----- | ------------------------------------------------------------ |
| user_id          | int      | 11   | 0    | TRUE  | 用户表的用户ID                                               |
| order_sn         | varchar  | 63   | 0    | TRUE  | 订单编号                                                     |
| order_status     | smallint | 6    | 0    | TRUE  | 订单状态                                                     |
| aftersale_status | smallint | 6    | 0    | FALSE | 售后状态，0是可申请，1是用户已申请，2是管理员审核通过，3是管理员退款成功，4是管理员审核拒绝，5是用户已取消 |
| consignee        | varchar  | 63   | 0    | TRUE  | 收货人名称                                                   |
| mobile           | varchar  | 63   | 0    | TRUE  | 收货人手机号                                                 |
| address          | varchar  | 127  | 0    | TRUE  | 收货具体地址                                                 |
| message          | varchar  | 512  | 0    | TRUE  | 用户订单留言                                                 |
| goods_price      | decimal  | 10   | 2    | TRUE  | 商品总费用                                                   |
| freight_price    | decimal  | 10   | 2    | TRUE  | 配送费用                                                     |
| coupon_price     | decimal  | 10   | 2    | TRUE  | 优惠券减免                                                   |
| integral_price   | decimal  | 10   | 2    | TRUE  | 用户积分减免                                                 |
| groupon_price    | decimal  | 10   | 2    | TRUE  | 团购优惠价减免                                               |
| order_price      | decimal  | 10   | 2    | TRUE  | 订单费用， = goods_price + freight_price - coupon_price      |
| actual_price     | decimal  | 10   | 2    | TRUE  | 实付费用， = order_price - integral_price                    |
| pay_id           | varchar  | 63   | 0    | FALSE | 微信付款编号                                                 |
| pay_time         | datetime | 0    | 0    | FALSE | 微信付款时间                                                 |
| ship_sn          | varchar  | 63   | 0    | FALSE | 发货编号                                                     |
| ship_channel     | varchar  | 63   | 0    | FALSE | 发货快递公司                                                 |
| ship_time        | datetime | 0    | 0    | FALSE | 发货开始时间                                                 |
| refund_amount    | decimal  | 10   | 2    | FALSE | 实际退款金额，（有可能退款金额小于实际支付金额）             |
| refund_type      | varchar  | 63   | 0    | FALSE | 退款方式                                                     |
| refund_content   | varchar  | 127  | 0    | FALSE | 退款备注                                                     |
| refund_time      | datetime | 0    | 0    | FALSE | 退款时间                                                     |
| confirm_time     | datetime | 0    | 0    | FALSE | 用户确认收货时间                                             |
| comments         | smallint | 6    | 0    | FALSE | 待评价订单商品数量                                           |
| end_time         | datetime | 0    | 0    | FALSE | 订单关闭时间                                                 |
| add_time         | datetime | 0    | 0    | FALSE | 创建时间                                                     |
| update_time      | datetime | 0    | 0    | FALSE | 更新时间                                                     |
| deleted          | tinyint  | 1    | 0    | FALSE | 逻辑删除                                                     |

## market_order_goods 订单商品表

| id             | int      | 11   | 0    | TRUE  |                                                              |
| -------------- | -------- | ---- | ---- | ----- | ------------------------------------------------------------ |
| order_id       | int      | 11   | 0    | TRUE  | 订单表的订单ID                                               |
| goods_id       | int      | 11   | 0    | TRUE  | 商品表的商品ID                                               |
| goods_name     | varchar  | 127  | 0    | TRUE  | 商品名称                                                     |
| goods_sn       | varchar  | 63   | 0    | TRUE  | 商品编号                                                     |
| product_id     | int      | 11   | 0    | TRUE  | 商品货品表的货品ID                                           |
| number         | smallint | 5    | 0    | TRUE  | 商品货品的购买数量                                           |
| price          | decimal  | 10   | 2    | TRUE  | 商品货品的售价                                               |
| specifications | varchar  | 1023 | 0    | TRUE  | 商品货品的规格列表                                           |
| pic_url        | varchar  | 255  | 0    | TRUE  | 商品货品图片或者商品图片                                     |
| comment        | int      | 11   | 0    | FALSE | 订单商品评论，如果是-1，则超期不能评价；如果是0，则可以评价；如果其他值，则是comment表里面的评论ID。 |
| add_time       | datetime | 0    | 0    | FALSE | 创建时间                                                     |
| update_time    | datetime | 0    | 0    | FALSE | 更新时间                                                     |
| deleted        | tinyint  | 1    | 0    | FALSE | 逻辑删除                                                     |

## market_permission 权限表

| id          | int      | 11   | TRUE  |          |
| ----------- | -------- | ---- | ----- | -------- |
| role_id     | int      | 11   | FALSE | 角色ID   |
| permission  | varchar  | 63   | FALSE | 权限     |
| add_time    | datetime | 0    | FALSE | 创建时间 |
| update_time | datetime | 0    | FALSE | 更新时间 |
| deleted     | tinyint  | 1    | FALSE | 逻辑删除 |

## market_region 行政区域表

| id   | int     | 11   | TRUE |                                                              |
| ---- | ------- | ---- | ---- | ------------------------------------------------------------ |
| pid  | int     | 11   | TRUE | 行政区域父ID，例如区县的pid指向市，市的pid指向省，省的pid则是0 |
| name | varchar | 120  | TRUE | 行政区域名称                                                 |
| type | tinyint | 3    | TRUE | 行政区域类型，如如1则是省， 如果是2则是市，如果是3则是区县   |
| code | int     | 11   | TRUE | 行政区域编码                                                 |

## market_role 角色表

| id          | int      | 11   | TRUE  |          |
| ----------- | -------- | ---- | ----- | -------- |
| name        | varchar  | 63   | TRUE  | 角色名称 |
| desc        | varchar  | 1023 | FALSE | 角色描述 |
| enabled     | tinyint  | 1    | FALSE | 是否启用 |
| add_time    | datetime | 0    | FALSE | 创建时间 |
| update_time | datetime | 0    | FALSE | 更新时间 |
| deleted     | tinyint  | 1    | FALSE | 逻辑删除 |

## market_search_history 搜索历史表

| id          | int      | 11   | TRUE  |                         |
| ----------- | -------- | ---- | ----- | ----------------------- |
| user_id     | int      | 11   | TRUE  | 用户表的用户ID          |
| keyword     | varchar  | 63   | TRUE  | 搜索关键字              |
| from        | varchar  | 63   | TRUE  | 搜索来源，如pc、wx、app |
| add_time    | datetime | 0    | FALSE | 创建时间                |
| update_time | datetime | 0    | FALSE | 更新时间                |
| deleted     | tinyint  | 1    | FALSE | 逻辑删除                |

## market_storage 文件存储表

| id          | int      | 11   | TRUE  |                |
| ----------- | -------- | ---- | ----- | -------------- |
| key         | varchar  | 63   | TRUE  | 文件的唯一索引 |
| name        | varchar  | 255  | TRUE  | 文件名         |
| type        | varchar  | 20   | TRUE  | 文件类型       |
| size        | int      | 11   | TRUE  | 文件大小       |
| url         | varchar  | 255  | FALSE | 文件访问链接   |
| add_time    | datetime | 0    | FALSE | 创建时间       |
| update_time | datetime | 0    | FALSE | 更新时间       |
| deleted     | tinyint  | 1    | FALSE | 逻辑删除       |

## market_system 系统配置表

| id          | int      | 11   | TRUE  |            |
| ----------- | -------- | ---- | ----- | ---------- |
| key_name    | varchar  | 255  | TRUE  | 系统配置名 |
| key_value   | varchar  | 255  | TRUE  | 系统配置值 |
| add_time    | datetime | 0    | FALSE | 创建时间   |
| update_time | datetime | 0    | FALSE | 更新时间   |
| deleted     | tinyint  | 1    | FALSE | 逻辑删除   |

## market_topic 专题表

| id          | int      | 11   | 0    | TRUE  |                                |
| ----------- | -------- | ---- | ---- | ----- | ------------------------------ |
| title       | varchar  | 255  | 0    | TRUE  | 专题标题                       |
| subtitle    | varchar  | 255  | 0    | FALSE | 专题子标题                     |
| content     | text     | 0    | 0    | FALSE | 专题内容，富文本格式           |
| price       | decimal  | 10   | 2    | FALSE | 专题相关商品最低价             |
| read_count  | varchar  | 255  | 0    | FALSE | 专题阅读量                     |
| pic_url     | varchar  | 255  | 0    | FALSE | 专题图片                       |
| sort_order  | int      | 11   | 0    | FALSE | 排序                           |
| goods       | varchar  | 1023 | 0    | FALSE | 专题相关商品，采用JSON数组格式 |
| add_time    | datetime | 0    | 0    | FALSE | 创建时间                       |
| update_time | datetime | 0    | 0    | FALSE | 更新时间                       |
| deleted     | tinyint  | 1    | 0    | FALSE | 逻辑删除                       |

## market_user 用户表

| id              | int      | 11   | TRUE  |                                      |
| --------------- | -------- | ---- | ----- | ------------------------------------ |
| username        | varchar  | 63   | TRUE  | 用户名称                             |
| password        | varchar  | 63   | TRUE  | 用户密码                             |
| gender          | tinyint  | 3    | TRUE  | 性别：0 未知， 1男， 1 女            |
| birthday        | date     | 0    | FALSE | 生日                                 |
| last_login_time | datetime | 0    | FALSE | 最近一次登录时间                     |
| last_login_ip   | varchar  | 63   | TRUE  | 最近一次登录IP地址                   |
| user_level      | tinyint  | 3    | FALSE | 0 普通用户，1 VIP用户，2 高级VIP用户 |
| nickname        | varchar  | 63   | TRUE  | 用户昵称或网络名称                   |
| mobile          | varchar  | 20   | TRUE  | 用户手机号码                         |
| avatar          | varchar  | 255  | TRUE  | 用户头像图片                         |
| weixin_openid   | varchar  | 63   | TRUE  | 微信登录openid                       |
| session_key     | varchar  | 100  | TRUE  | 微信登录会话KEY                      |
| status          | tinyint  | 3    | TRUE  | 0 可用, 1 禁用, 2 注销               |
| add_time        | datetime | 0    | FALSE | 创建时间                             |
| update_time     | datetime | 0    | FALSE | 更新时间                             |
| deleted         | tinyint  | 1    | FALSE | 逻辑删除                             |



