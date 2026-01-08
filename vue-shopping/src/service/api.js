import { get, post } from './request' // 导入统一封装好的请求模块

// 商城管理端
// 用户登录
export const login = data => post('/a/auth/login', data);
// 注销登录
export const logout = data => post('/a/auth/logout', data);
// 用户管理
// 添加用户
export const addUser = data => post('/a/user/add', data);
// 修改用户
export const updateUser = data => post('/a/user/update', data);
// 用户列表
export const getUserList = data => get('/a/user/list', data);
// 用户信息
export const getUserInfo = data => get('/a/user/get', data);
// 删除用户
export const deleteUser = data => post('/a/user/delete', data);
// 商品管理
// 商品列表
export const getGoodsList = data => get('/a/goods/list', data);
// 添加商品
export const addGoods = data => post('/a/goods/add', data);
// 修改商品
export const updateGoods = data => post('/a/goods/update', data);
// 删除商品
export const deleteGoods = data => post('/a/goods/delete', data);

// 商城用户端
// 用户注册
export const register = data => post('/user/add', data);
// 用户登录
export const shoppingLogin = data => post('/auth/login', data);
// 注销登录
export const shoppingLogout = data => post('/auth/logout', data);
// 商城列表
export const getShoppingGoodsList = data => get('/goods/list', data);
// 商品信息
export const getGoodsInfo = data => get('/goods/get', data);
// 购物车列表
export const getCartList = data => get('/cart/list', data);
// 添加购物车
export const addCart = data => post('/cart/add', data);
// 删除购物车
export const deleteCart = data => post('/cart/delete', data);
// 修改购物车
export const updateCart = data => post('/cart/update', data);
// 购物车结算
export const buyorder = data => post('/order/add', data);

// 文件上传
export const uploadFile = data => post('/file/upload', data);
