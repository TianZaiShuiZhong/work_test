<template>
  <div v-if="product">
    <div class="product">
      <!-- 商品图片、名称、价格 -->
      <div class="product-image">
        <img :src="product.image" alt="">
      </div>
      <div class="product-info">
        <h1 class="product-name">{{ product.name }}</h1>
        <div class="product-cost">¥ {{ product.cost }}</div>
        <div class="product-add-cart" @click="handleAddCart">加入购物车</div>
      </div>
      <!-- 产品介绍 -->
      <div class="product-desc">
        <h2>产品介绍</h2>
        <img :src="product.imageDetail" alt="">
      </div>
    </div>
  </div>
</template>

<script>
import { getGoodsInfo, addCart } from "@/service/api";
import util from "@/util";

export default {
  data() {
    return {
      // 获取路由中的参数
      id: parseInt(this.$route.params.id),
      product: null,
      // count: 0, // 购买数量
    };
  },
  methods: {
    async getProduct() {
      try {
        const res = await getGoodsInfo({
          id: parseInt(this.$route.params.id),
        });
        if (res.code === 200) {
          const data = res.data;
          data.image = util.getImgUrl(data.image);
          data.imageDetail = util.getImgUrl(data.imageDetail);
          this.product = data;
        }
      } catch (error) {
        this.$message.error(error);
      }
    },
    async handleAddCart() {
      try {
        // this.count = ++this.count;
        const res = await addCart({
          goodsId: this.id,
          // goodsNumber: this.count,
        });
        if (res.code === 200) {
          this.$message.success("加入购物车成功");
          this.$store.commit("setCartNum", ++this.$store.state.cartNum);
        }
      } catch (error) {
        this.$message.error(error);
      }
    },
  },
  mounted() {
    // 初始化数据
    this.getProduct();
  },
};
</script>

<style scoped>
.product {
  margin: 32px;
  padding: 32px;
  background: #fff;
  border: 1px solid #dddee1;
  border-radius: 10px;
  overflow: hidden;
}
.product-image {
  width: 50%;
  height: 550px;
  float: left;
  text-align: center;
}
.product-image img {
  height: 100%;
}
.product-info {
  width: 50%;
  padding: 150px 0 250px;
  height: 150px;
  float: left;
  text-align: center;
}
.product-cost {
  color: #f2352e;
  margin: 8px 0;
}
.product-add-cart {
  display: inline-block;
  padding: 8px 64px;
  margin: 8px 0;
  background: #2d8cf0;
  color: #fff;
  border-radius: 4px;
  cursor: pointer;
}
.product-desc {
  clear: both;
  margin-top: 32px;
}
.product-desc img {
  width: 100%;
  border: 1px solid #dddee1;
  border-radius: 4px;
}
</style>