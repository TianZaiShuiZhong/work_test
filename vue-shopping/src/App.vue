<template>
  <div>
    <!-- 商城顶部导航 -->
    <template v-if="show && loginType === 'shopping'">
      <div class="header">
        <router-link to="/shopping/list">
          <div class="header-title">
            <img src="./assets/logo.png" class="header-logo">
            <span>商城首页</span>
          </div>
        </router-link>
        <div class="header-menu">
          <router-link to="/shopping/cart" class="header-menu-cart">
            购物车
            <span v-if="cartNum">{{ cartNum }}</span>
          </router-link>
        </div>
        <div class="header-menu">{{ user }}</div>
        <div class="header-menu">
          <router-link to="/shopping/login/logout" class="header-menu-cart">退出登录</router-link>
        </div>
        <div class="header-menu">
          <router-link to="/admin/login/logout" class="header-menu-cart">管理后台</router-link>
        </div>
      </div>
    </template>

    <!-- 商家端顶部导航 -->
    <template v-else-if="show && loginType === 'admin'">
      <div class="header">
        <router-link to="/admin/list">
          <div class="header-title">
            
            <span>商城管理后台</span>
          </div>
        </router-link>
        <div class="header-menu">{{ user }}</div>
        <div class="header-menu">
          <router-link to="/admin/login/logout" class="header-menu-cart">退出登录</router-link>
        </div>
        <div class="header-menu">
          <router-link to="/shopping/login/logout" class="header-menu-cart">购物商城</router-link>
        </div>
        <div class="header-menu">
          <router-link to="/admin/list" class="header-menu-cart">商品管理</router-link>
        </div>
        <div class="header-menu">
          <router-link to="/admin/user" class="header-menu-cart">用户管理</router-link>
        </div>
      </div>
    </template>

    <!-- 挂载所有路由 -->
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  computed: {
    // 直接从 Vuex 读取，保持响应式
    user() {
      return this.$store.state.username;
    },
    cartNum() {
      return this.$store.state.cartNum;
    },
    // loginStatus 在 store 中是字符串，转成布尔值避免 'false' 也为真
    show() {
      return this.$store.state.loginStatus === 'true';
    },
    loginType() {
      return this.$store.state.loginType;
    },
  },
};
</script>

<style scoped>
/* 样式部分可以根据需要添加 */
</style>