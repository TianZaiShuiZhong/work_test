import Vue from 'vue'
import VueRouter from 'vue-router'
Vue.use(VueRouter)

const routes = [
  {
    path: '/shopping/list',
    meta: {
      title: '商品列表',
    },
    component: (resolve) => require(['../views/shopping/list.vue'], resolve),
  },
  {
    path: '/shopping/product/:id',
    meta: {
      title: '商品详情',
    },
    component: (resolve) => require(['../views/shopping/product.vue'], resolve),
  },
  {
    path: '/shopping/cart',
    meta: {
      title: '购物车',
    },
    component: (resolve) => require(['../views/shopping/cart.vue'], resolve),
  },
  {
    path: '/shopping/login/:loginStatus',
    meta: {
      title: '登录注册',
    },
    component: (resolve) => require(['../views/shopping/login.vue'], resolve),
  },
  {
    path: '*',
    redirect: '/shopping/login/login',
  },
  // 商家端
  {
    path: '/admin/list',
    meta: {
      title: '商品管理',
    },
    component: (resolve) => require(['../views/admin/list.vue'], resolve),
  },
  {
    path: '/admin/user',
    meta: {
      title: '用户管理',
    },
    component: (resolve) => require(['../views/admin/user.vue'], resolve),
  },
  {
    path: '/admin/login/:loginstatus',
    meta: {
      title: '登录',
    },
    component: (resolve) => require(['../views/admin/login.vue'], resolve),
  },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

export default router;
