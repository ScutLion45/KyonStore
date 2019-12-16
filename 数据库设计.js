// database: KYONSTORE

// 用户与发行方

var user = {           // 用户表，[用户]权限有登录、注销、浏览与购买商品
  uid: '',             // 用户ID（后台生成），主键，非空
  umail: '',           // 用户邮箱，收货用，不可重复，非空
  uname: 'kyon45',     // 用户昵称，不可重复，非空（登录用）
  upwd: '123456',      // 密码，非空
  ubalance: 'float',   // 余额，非负
}

var publisher = {      // 发行方表，[发行方]权限有管理商品（上架、下架、发货、查看商品日志）
  puid: '',            // 发行方ID（后台生成），主键，非空
  pname: 'PLAYLIST',   // 发行方名称，不可重复，非空（登录用）
  ppwd: '123',         // 密码，非空
  pinfo: '',           // 发行方信息，可空
}

// 商品

var goods = {
  gid: 'gXXX',         // 商品ID，主键，非空
  gname: 'A-TEEN-1 EP.1 不平凡，其实是不想平凡',                       // 商品名称，非空
  ginfo: '',           // 商品介绍
  gtype: '(1-MOVIE | 2-DRAMA | 3-EPISODE | 4-ALBUM | 5-SINGLE | ...)',  // 商品类型，非空
  gpubtime: '2019-08-31 13:10:09',                                     // 发行时间，非空
  gprice: 'float',     // 商品单价，非空
  gbrowse: 'int',        // 商品浏览数
  gsell: 'int',        // 商品销售量
  gstate: '(1-上架中 | 2-已下架)',                                     // 商品状态，非空
  gimg: 'str'          // 商品图片后缀格式（.jpg | .png）
  pub: '',            // 发行方ID，外键，非空
}

// 订单（购买日志）

var order = {
  oid: 'xxxx',         // 订单ID（后台生成），主键，非空
  ostate: '(1-加入购物车 | 2-付款（等待发货） | 3-收货 | 4-失效)',    // 订单状态，非空
  otime: '2019-11-31 16:10:02',                                // 购买时间，非空
  onum: 'int'                                                  // 购买数量，>1，非空
  goodsid: '',         // 商品ID，外键，非空
  userid: '',          // 购买者用户ID，外键，非空
}

// 浏览记录（日志）

var browse = {
  bid: 'int',          // 自增
  btime: '2019-09-31 11:10:09'     // 浏览时间，非空
  goodsid: '',         // 商品ID，外键，非空
  userid: '',          // 浏览者用户ID，外键，非空
}

// -----------------------------------------------------------------------------------------------
