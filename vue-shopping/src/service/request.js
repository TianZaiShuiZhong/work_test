import axios from 'axios';
import store from '../store';
import config from '../config/index.js'; // 引入环境配置文件

axios.defaults.headers.post['Content-Type'] = 'application/json'; // 跨域设置
axios.defaults.withCredentials = true;
// 这里设置了 POST 请求的默认请求头为 application/json，并设置了跨域请求时携带凭证（cookies 等）。

// 创建 axios 实例
const service = axios.create({
  baseURL: config.baseUrl, // api 的 base_url
  timeout: 5000, // 请求超时时间
});

// 请求拦截器
service.interceptors.request.use(
  config => {
    // 可以在这里添加请求头等信息
    if (store.state.token) {
      config.headers['Authorization'] = `Bearer ${store.state.token}`;
    }
    // 如果是 FormData，让浏览器自动设置 Content-Type
    if (config.data instanceof FormData) {
      delete config.headers['Content-Type'];
    }
    return config;
  },
  error => {
    // 请求错误处理
    console.log(error); // for debug
    return Promise.reject(error);
  }
);

// 响应拦截器
service.interceptors.response.use(
  response => {
    // 后端统一返回 { code, msg, data }
    const res = response.data;
    if (res && (res.msg === 'Token无效' || res.msg === '权限不足')) {
      try {
        // 清理登录状态
        store.commit('setLoginStatus', false);
        store.commit('setToken', null);
        store.commit('setLoginType', null);
        store.commit('setUsername', null);
        window.localStorage.clear();
      } catch (e) {
        console.error(e);
      }
      // 根据当前路径跳到对应登录页
      const path = window.location.pathname || '';
      if (path.startsWith('/admin')) {
        window.location.href = '/admin/login/logout';
      } else {
        window.location.href = '/shopping/login/logout';
      }
      return Promise.reject(res);
    }
    return Promise.resolve(response);
  },
  error => {
    console.log(error); // 打印错误信息
    return Promise.reject(error);
  }
);

// 封装 get 方法
export function get(url, params) {
  return new Promise((resolve, reject) => {
    service
      .get(url, {
        params: params,
      })
      .then((res) => {
        resolve(res.data);
      })
      .catch((err) => {
        reject(err.data);
      });
  });
}

// 封装 post 方法
export function post(url, data) {
  return new Promise((resolve, reject) => {
    service.post(url, data)
      .then((res) => {
        resolve(res.data);
      })
      .catch((err) => {
        reject(err.data);
      });
  });
}
