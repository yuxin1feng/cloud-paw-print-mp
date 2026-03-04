// index.js

// const BASE = 'http://192.168.58.1:8080'
const BASE = 'http://192.168.31.112:8080'
// const BASE = 'http://192.168.80.1:8080'

Page({

    data: {
        result: '',
        posts: []
    },

    onLoad() {
        this.loadPosts();
    },

    loadPosts() {
        wx.request({
            url: BASE + '/api/posts',
            method: 'GET',
            success: (res) => {
                this.setData({ posts: res.data || [] });
            },
            fail: (err) => {
                console.error(err);
            }
        });
    },

    checkStatus() {
        wx.request({
            url: BASE + '/api/status',
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
            url: BASE + '/api/login',
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
    },

    // 简单发帖（演示：通过输入 media_url 或使用占位）
    createDummyPost() {
        wx.request({
            url: BASE + '/api/posts',
            method: 'POST',
            header: {'content-type': 'application/json'},
            data: {
                userId: 1,
                pet_name: 'demo',
                media_url: '/images/placeholder.png',
                // caption: 'from mini program',
                caption:'first post'
            },
            success: (res) => {
                this.loadPosts(); // 刷新
                this.setData({ result: JSON.stringify(res.data) });
            }
        });
    },

    onLike(e) {
        const id = e.currentTarget.dataset.id;
        wx.request({
            url: `${BASE}/api/posts/${id}/like`,
            method: 'POST',
            header: {'content-type':'application/json'},
            data: { userId: 1 }, // 当前用户 id（演示）
            success: () => { this.loadPosts(); }
        });
    }

})
