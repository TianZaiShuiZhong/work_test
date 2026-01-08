<template>
  <div class="cart">
    <div class="cart-header">
      <div class="cart-header-title">购物清单</div>
    </div>
    <div class="cart-content">
      <el-table :data="cartList" style="width: 100%">
        <el-table-column prop="goods" label="商品信息" min-width="200" align="center">
          <template slot-scope="{ row }">
            <div style="display: flex; flex-direction: row; align-items: center">
              <el-image
                style="width: 100px; height: 100px"
                :src="getUrl(row.goods.image)"
                :preview-src-list="[getUrl(row.goods.image)]"
              ></el-image>
              <span style="margin-left: 24px">{{ row.goods.name }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="cost" label="单价" min-width="120" align="center">
          <template slot-scope="{ row }">
            <span style="color: brown">￥{{ row.goods.cost }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="goodsNumber" label="数量" min-width="120" align="center">
          <template slot-scope="scope">
            <el-input-number
              v-model="scope.row.goodsNumber"
              @change="handleChange(scope.row.goodsNumber, scope.$index)"
              :min="1"
              :max="999"
            ></el-input-number>
          </template>
        </el-table-column>
        <el-table-column prop="sales" label="小计" min-width="120" align="center">
          <template slot-scope="{ row }">
            ￥{{ row.goods.cost * row.goodsNumber }}
          </template>
        </el-table-column>
        <el-table-column align="center" fixed="right" label="操作" width="120">
          <template slot-scope="scope">
            <el-button
              @click.native.prevent="handleDelete(scope.$index)"
              type="danger"
              size="small"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="cart-footer" v-show="cartList.length">
      <div class="cart-footer-desc">共计<span>{{ countAll }}</span></div>
      <div class="cart-footer-desc">应付总额<span>{{ costAll }}</span></div>
      <div class="cart-footer-desc">
        <div class="cart-control-order" @click="handleorder">现在结算</div>
      </div>
    </div>
  </div>
</template>

<script>
import baseURL from "@/config/index.js";
import { getCartList, updateCart, deleteCart, buyorder } from "../../service/api";

export default {
  name: "cart",
  data() {
    return {
      promotion: 0,
      promotionCode: "",
      productList: [],
      cartList: [],
      currentCount: 0, // 当前购物车的商品总数
    };
  },
  watch: {
    // 监听 cartList 变化，同步更新 Vuex 中的购物车数量
    cartList: {
      handler(val) {
        let count = 0;
        val.forEach((item) => {
          count += item.goodsNumber;
        });
        this.$store.commit("setCartNum", count);
      },
      deep: true,
    },
  },
  computed: {
    // 设置字典对象，方便查询
    productDictList() {
      const dict = {};
      this.productList.forEach((item) => {
        dict[item.id] = item;
      });
      return dict;
    },
    // 购物车商品总数
    countAll() {
      let count = 0;
      this.cartList.forEach((item) => {
        count += item.goodsNumber;
      });
      return count;
    },
    // 购物车商品总价
    costAll() {
      let cost = 0;
      this.cartList.forEach((item) => {
        cost += item.goods.cost * item.goodsNumber;
      });
      return cost;
    },
  },
  created() {
    // 请求接口获取购物列表清单
    this.getMyCartList();
  },
  methods: {
    // 图片处理
    getUrl(url) {
      if (!url) return "";
      if (url.startsWith("http")) return url;
      return url.startsWith("/") ? baseURL.baseUrl + url : baseURL.baseUrl + "/" + url;
    },
    // 获取购物车列表
    async getMyCartList() {
      try {
        const res = await getCartList({});
        this.cartList = res.data;
      } catch (error) {
        this.$message.error(error);
      }
    },
    // 通知 Vuex，完成下单
    async handleorder() {
      try {
        let params = [];
        this.cartList.forEach((item) => {
          params.push(item.id);
        });
        console.log(params);
        const res = await buyorder(params);
        if (res.code === 200) {
          this.$message.success("下单成功");
          this.$store.commit("setCartNum", 0);
          window.location.href = "/shopping/list";
        }
      } catch (error) {
        this.$message.error(error);
      }
    },
    // 修改购物车数量
    async handleChange(newCount, index) {
      try {
        const res = await updateCart({
          id: this.cartList[index].id,
          goodsId: this.cartList[index].goods.id,
          goodsNumber: newCount,
        });
        if (res.code === 200) {
          this.getMyCartList();
        }
      } catch (error) {
        this.$message.error(error);
      }
    },
    // 删除购物车中的商品
    async handleDelete(index) {
      this.$confirm("确定删除此条数据？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          try {
            const res = await deleteCart([this.cartList[index].id]);
            if (res.code === 200) {
              this.cartList.splice(index, 1);
              this.$message.success("删除成功");
            }
          } catch (error) {
            this.$message.error(error);
          }
        })
        .catch(() => {
          // 用户取消
        });
    },
  },
};
</script>

<style scoped>
.cart {
  margin: 32px;
  background: #fff;
  border: 1px solid #dddee1;
  border-radius: 10px;
}
.cart-header-title {
  padding: 16px 32px;
  border-bottom: 1px solid #dddee1;
  border-radius: 10px 10px 0 0;
  background: #f8f8f9;
}
.cart-header-main {
  padding: 8px 32px;
  overflow: hidden;
  border-bottom: 1px solid #dddee1;
  background: #eee;
  overflow: hidden;
}
.cart-empty {
  text-align: center;
  padding: 32px;
}
.cart-header-main div {
  text-align: center;
  float: left;
  font-size: 14px;
}
div.cart-info {
  width: 60%;
  text-align: left;
}
.cart-price {
  width: 10%;
}
.cart-count {
  width: 10%;
}
.cart-cost {
  width: 10%;
}
.cart-delete {
  width: 10%;
}
.cart-content-main {
  padding: 0 32px;
  height: 60px;
  line-height: 60px;
  text-align: center;
  border-bottom: 1px dashed #e9eaec;
  overflow: hidden;
}
.cart-content-main div {
  float: left;
}
.cart-content-main img {
  width: 40px;
  height: 40px;
  position: relative;
  top: 10px;
}
.cart-control-minus,
.cart-control-add {
  display: inline-block;
  margin: 0 4px;
  width: 24px;
  height: 24px;
  line-height: 22px;
  text-align: center;
  background: #f8f8f9;
  border-radius: 50%;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.2);
  cursor: pointer;
}
.cart-control-delete {
  cursor: pointer;
  color: #2d8cf0;
}
.cart-promotion {
  padding: 16px 32px;
}
.cart-control-promotion,
.cart-control-order {
  display: inline-block;
  padding: 8px 32px;
  border-radius: 6px;
  background: #2d8cf0;
  color: #fff;
  cursor: pointer;
}
.cart-control-promotion {
  padding: 2px 6px;
  font-size: 12px;
  border-radius: 3px;
}
.cart-footer {
  padding: 32px;
  text-align: right;
}
.cart-footer-desc {
  display: inline-block;
  padding: 0 16px;
}
.cart-footer-desc span {
  color: #f2352e;
  font-size: 20px;
}
</style>