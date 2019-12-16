// 发行方端
// 登录-注册：见用户端

// 商品管理
var goods_manage_apis = {
  api_ : { 请求url: '/pub-search-goods',
    请求参数: {
      pUid: 'str',
      gType: 'int',           // 商品类型，0表示查全部
      gPubTime: 'str',        // （yyyy-MM）上架年+月，默认空串
      gState: 'int',          // 商品状态，0表示查全部
      gName: 'str',           // 商品名
    },
    响应格式: {
      // 同'/load-latest-goods'
    }
  },
  
  api_ : { 请求url: '/pub-edit-goods',
    请求参数: {
      gId: 'str',
      gName: 'int',
      gInfo: 'str',
      gType: 'int',
      gPrice: 'double',   // ！！！联动修改order中ostate=1的gprice
      files: 'fileItem'  //图片
    },
    响应格式: {
      success: '(0-失败|1-成功)'
    }
  },
  
  api_ : { 请求url: '/pub-off-goods',
    请求参数: {
      gId: 'str'
    },
    响应格式: {
      gState: '( 1 | other )'
    }
  },

  api_ : { 请求url: '/pub-create-goods',
    请求参数: {
      gName: 'str',
      gInfo: 'str',
      gType: 'int',
      gPrice: 'double',
      pUid: 'str'
      files: 'fileItem'  //图片
    },
    响应格式: {
      success: '(0-失败|1-成功)'
    }
  },

  api_ : { 请求url: '/pub-edit-profile',
    请求参数: {
      pUid: 'str',
      pPwd: 'str',
      pInfo: ''
    },
    响应格式: {
      success: '(0-失败|1-成功)'
    }
  },

  api_ : { 请求url: '/pub-load-order',
    请求参数: {
      pUid: 'str',
    },
    响应格式: {
      // 同/user-load-order
    }
  }
  
  // update-order
}