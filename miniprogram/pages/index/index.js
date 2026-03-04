// index.js
Page({

    data: {
        result: ''
    },

    checkStatus() {
        wx.request({
            url: 'http://192.168.58.1:8080/api/status',
            method: 'GET',
            success: (res) => {
                this.setData({
                    result: JSON.stringify(res.data)
                })
            }
        })
    },

    login() {
        wx.request({
            url: 'http://192.168.58.1:8080/api/login',
            method: 'POST',
            header: {
                'content-type': 'application/json'
            },
            data: {
                openid: 'demo-openid-001',
                nickname: 'TestUser'
            },
            success: (res) => {
                this.setData({
                    result: JSON.stringify(res.data)
                })
            }
        })
    }

})
