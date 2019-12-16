// 用户端
// 登录-注册-登出
var login_reg_logout_apis = {
  api_ : { 请求url: '/check-name', // √
    // login.jsp
    // 注册时name不能重复
    请求参数: {
      name: 'str',
      arg: 1,       // '(1-用户|2-发行方)'
    },
    响应格式: {
      exist: '(0-不存在|1-存在)'
    }
  },

  api_ : { 请求url: '/check-umail', // √
    // login.jsp
    // 注册时umail不能重复
    请求参数: {
      umail: 'str'
    },
    响应格式: {
      exist: '(0-不存在|1-存在)'
    }
  },

  api_ : { 请求url: '/login', // √
    // login.jsp
    // 如果登录成功，后台将Gson(user)写入session
    请求参数: {
      name: 'str',
      pwd: 'str',
      arg: 1,       // '(1-用户登录|2-发行方登录)'
    },
    响应格式: {
      // 如果密码匹配，返回user
      // 否则返回null
    }
  },

  api_ : { 请求url: '/reg', // √
    // login.jsp
    请求参数: {
      name: 'str',
      pwd: 'str',
      attach: 'str', // 'umail | pinfo(空串)'
      arg: 1,        // '(1-用户注册|2-发行方注册)'
    },
    响应格式: {
      success: '(0-失败|1-成功)'
    }
  }
}

// 首页
var home_apis = {
  // home.html
  api_ : { 请求url: '/load-latest-goods', // √
    // 加载指定类别的上架中最新商品，按页显示，每页5条
    请求参数: {
      uid: 'str',
      gtype: 'int',       // 默认1
      //page: 'int'         // 1开始的整数，每页5条 limit((page-1)*5,5)，下同
    },
    响应格式: [   // 商品卡片列表
      { gId: 'g001',
        gName: 'A-TEEN-1 EP.1 不平凡，其实是不想平凡',
        gInfo: '',
        gType: 3,
        gPubTime: '2019-08-31 13:10:09',
        gPrice: 45.00, gview: 1, gsell: 1,
        gState: 1,
        gImg: 
        pub: {    // 发行商信息
          pUid: 'playlist1',
          pName: 'PLAYLIST',
          pPwd: '',
          pInfo: 'playlist_official'
        }
      }
    ]
  }
}

// 搜索页
var search_apis = {  
  api_ : { 请求url: '/search-goods', // √
    请求参数: {
      // uid: 'str',
      gName: 'str',           // 商品名称，默认空串
      gType: 'int',           // 商品类型，默认1
      gPubTime: 'str',        // 上架时间，默认空串
    },
    响应格式: {
      // 同'/load-latest-goods'
    }
  },
  
  // 调pub_search_goods查发行方的商品 // √
}

// 商品详情页
var goods_apis = {  
  api_ : { 请求url: '/browse-goods', // √
    // 更新/插入商品浏览记录（如果用户已登录）
    请求参数: {
      uId: 'str',
      gId: 'str'
    },
    响应格式: {
      success: '(0-失败|1-成功)'
    }
  },
  
  // 加入购物车：/user-create-order?arg=1 => 无需考虑是否在购物车，直接新加order// 判断商品是否已经在购物车，如果是，调修改订单
  // 立即购买：  /user-create-order?arg=2 => 无需考虑是否在购物车
  api_ : { 请求url: '/user-create-order',
    请求参数: {
      oNum: 'int',
      gId: 'str',
      gPrice: 'double',
      uId: 'str',
      arg: '(1-加入购物车 | 2-立即购买)'
    },
    响应格式: {
      success: '(0-失败|1-成功)'
    }
  },
  
}

// 购物车页
var shoopingcart_apis = {
  api_ : { 请求url: '/user-load-order',
    // 加载购物车（包括有效和失效商品）
    请求参数: {
      uId: 'str',
      arg: 1
    },
    响应格式: {
      // orderList( ostate=1/4 )
    }
  },
  
  api_ : { 请求url: '/user-edit-order',
    // 用户从购物车修改余额
    请求参数: {
      oId: 'str',
      oNum: 'int'
    },
    响应格式: {
      success: '(0-失败|1-成功)'
    }
  },
  
  api_ : { 请求url: '/user-remove-order',
    // 用户从购物车删除商品
    请求参数: {
      oId: 'str'
    },
    响应格式: {
      success: '(0-失败|1-成功)'
    }
  },
  
  api_ : { 请求url: '/update-order',
    // 用户从购物车结算=>判断余额
    // 发行方发货
    请求参数: {
      oId: 'str',
      arg: 2
    },
    响应格式: {
      success: '(0-失败|1-成功)'
    }
  },
  
}

// 个人页
var my_apis = {
  // 个人资料 ---------------------------------------------------
  api_ : { 请求url: '/user-edit-profile', // √
    // 用户修改个人信息
    请求参数: {
      uId: 'str',
      uMail: 'str',
      uPwd: 'str',
      uBalance: 'double'
    },
    响应格式: {
      success: '(0-失败|1-成功)'
    }
  },
  
  // 我的足迹 --------------------------------------------------
  api_ : { 请求url: '/user-load-browse', // √
    // 用户加载浏览记录
    请求参数: {
      uId: 'str'
    },
    响应格式: {
      // browseList
    }
  },
  
  // 历史订单 --------------------------------------------------
  api_ : { 请求url: '/user-load-order',
    // 加载历史订单（包括有效和失效商品）
    请求参数: {
      uId: 'str',
      arg: 2  // 历史订单
    },
    响应格式: {
      // orderList( ostate=2/3 )
    }
  },
}


