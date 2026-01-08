<template>
  <div>
    <!-- <div style="text-align: right; margin-bottom: 10px;">
      <el-button type="danger" size="small" @click="logout">退出登录</el-button>
    </div> -->
    <div class="list-control">
      <div class="list-control-filter">
        <span>品牌:</span>
        <span 
          class="list-control-filter-item" 
          :class="{ on: item === brand }" 
          v-for="(item, index) in brands" 
          :key="index" 
          @click="handleFilterBrand(item)"
        >
          {{ item }}
        </span>
      </div>
      <div class="list-control-filter">
        <span>颜色:</span>
        <span 
          class="list-control-filter-item" 
          :class="{ on: item === color }" 
          v-for="(item, index) in colors" 
          :key="index" 
          @click="handleFilterColor(item)"
        >
          {{ item }}
        </span>
      </div>
      <div class="list-control-order">
        <span>排序:</span>
        <span 
          class="list-control-order-item" 
          :class="{ on: sort === '' }" 
          @click="handleSortDefault"
        >
          默认
        </span>
        <span 
          class="list-control-order-item" 
          :class="{ on: sort === 'sales' }" 
          @click="handleSortSales('sales')"
        >
          销量
          <template v-if="sort === 'sales'">↓</template>
        </span>
        <span 
          class="list-control-order-item" 
          :class="{ on: sort.indexOf('cost') > -1 }" 
          @click="handleSortCost('cost')"
        >
          价格
          <template v-if="sort === 'cost'">↑</template>
        </span>
      </div>
    </div>
    <Product v-for="item in list" :info="item" :key="item.id"></Product>
    <div class="product-not-found" v-show="!list.length">暂无相关商品</div>
  </div>
</template>

<script>
import { getShoppingGoodsList } from "@/service/api";
import util from "@/util";
import Product from "../../components/product.vue";

export default {
  components: { Product },
  data() {
    return {
      brand: "",
      color: "",
      sort: "",
      list: [],
      brands: [],
      colors: [],
    };
  },
  created() {
    this.getProductList();
    this.getBrandOrColor();
  },
  methods: {
    logout() {
      this.$router.push('/shopping/login/logout');
    },
    async getProductList() {
      const res = await getShoppingGoodsList({
        state: 1,
        brand: this.brand || "",
        color: this.color || "",
        sort: this.sort || "",
        page: 1,
        size: 100,
      });
      if (res.code === 200) {
        const data = res.data;
        if (data && data.length) {
          data.map((item) => {
            item.image = util.getImgUrl(item.image);
            item.imageDetail = util.getImgUrl(item.imageDetail);
          });
          this.list = data;
        } else {
          this.list = [];
        }
      }
    },
    async getBrandOrColor() {
      try {
        const res = await getShoppingGoodsList({
          state: 1,
          page: 1,
          size: 100,
        });
        if (res.data && res.data.length) {
          const brands = res.data.map((item) => item.brand);
          this.brands = util.getFilterArray(brands);
          const colors = res.data.map((item) => item.color);
          this.colors = util.getFilterArray(colors);
        }
      } catch (error) {
        this.$message.error(error);
      }
    },
    handleFilterBrand(brand) {
      if (this.brand === brand) {
        this.brand = "";
      } else {
        this.brand = brand;
      }
      this.getProductList();
    },
    handleFilterColor(color) {
      if (this.color === color) {
        this.color = "";
      } else {
        this.color = color;
      }
      this.getProductList();
    },
    handleSortDefault() {
      this.sort = "";
      this.getProductList();
    },
    handleSortSales(sales) {
      if (this.sort === sales) {
        this.sort = "";
      } else {
        this.sort = sales;
      }
      this.getProductList();
    },
    handleSortCost(cost) {
      if (this.sort === cost) {
        this.sort = "";
      } else {
        this.sort = cost;
      }
      this.getProductList();
    },
  },
};
</script>

<style scoped>
.list-control {
  background-color: #f8f8f9;
  border-radius: 8px;
  padding: 16px;
  margin-bottom: 16px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
.list-control-filter {
  margin-bottom: 16px;
}
.list-control-filter-item,
.list-control-order-item {
  cursor: pointer;
  margin-right: 8px;
  padding: 4px 8px;
  border: 1px solid #dddee1;
  border-radius: 4px;
  background-color: #fff;
}
.list-control-filter-item.on,
.list-control-order-item.on {
  background-color: #2d8cf0;
  border-color: #2d8cf0;
  color: #fff;
}
.product-not-found {
  text-align: center;
  padding: 32px;
  color: #999;
}
</style>