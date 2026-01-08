import Vue from 'vue'
import Vuex from 'vuex'

// 安装 Vuex（必须在创建 Store 实例前执行）
Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    // 当前用户账号（优先从 localStorage 读取，无则为 null）
    username: window.localStorage.getItem('username') || null,
    // 登录状态（字符串类型，需转为布尔值使用，如：`!!state.loginStatus`）
    loginStatus: window.localStorage.getItem('loginStatus') || 'false',
    // 用户 token（无则为 null）
    token: window.localStorage.getItem('token') || null,
    // 登录类型（如：'password'/'sms'，无则为 null）
    loginType: window.localStorage.getItem('loginType') || null,
    // 购物车数量（默认 0，处理 localStorage 空值或非数字情况）
    cartNum: parseInt(window.localStorage.getItem('cartNum')) || 0,
  },
  getters: {},
  mutations: {
    // 设置用户名（同步修改 state + localStorage）
    setUsername(state, username) {
      state.username = username
      // 若 username 为空（如退出登录），清除 localStorage；否则存储
      if (username) {
        window.localStorage.setItem('username', username)
      } else {
        window.localStorage.removeItem('username')
      }
    },
    // 设置登录状态（flag 为布尔值，转字符串存储）
    setLoginStatus(state, flag) {
      state.loginStatus = String(flag) // 统一存储为字符串（如 'true'/'false'）
      window.localStorage.setItem('loginStatus', String(flag))
    },
    // 设置 token（token 通常为字符串，空值时清除 localStorage）
    setToken(state, token) {
      state.token = token
      if (token) {
        window.localStorage.setItem('token', token)
      } else {
        window.localStorage.removeItem('token')
      }
    },
    // 设置登录类型（type 为具体类型字符串，空值时清除）
    setLoginType(state, type) {
      state.loginType = type
      if (type) {
        window.localStorage.setItem('loginType', type)
      } else {
        window.localStorage.removeItem('loginType')
      }
    },
    // 设置购物车数量（num 为数字，确保非负）
    setCartNum(state, num) {
      const newNum = Math.max(0, parseInt(num) || 0) // 防止负数或非数字
      state.cartNum = newNum
      window.localStorage.setItem('cartNum', newNum.toString())
    },
  },
  actions: {}, // 后续可扩展异步操作（如接口请求后提交 mutation）
  modules: {}, // 后续可按模块拆分状态（如 user、cart 模块）
})