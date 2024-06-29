<template>
  <el-container style="height: 97.6vh; border: 1px solid #eee">
    <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
      <el-menu default-active="1">
        <el-submenu index="1">
          <el-menu-item-group>
            <template v-slot:title>审核</template>
            <el-menu-item index="1" @click="toValue(1)">审核文章</el-menu-item>
            <el-menu-item index="2" @click="toValue(2)">审核记录</el-menu-item>
          </el-menu-item-group>
          <el-menu-item-group title="管理被举报文章">
            <el-menu-item index="3" @click="toValue(3)">管理</el-menu-item>
            <el-menu-item index="4" @click="toValue(4)">管理记录</el-menu-item>
          </el-menu-item-group>
          <el-menu-item-group title="修改账号信息">
            <el-menu-item index="5" @click="toValue(5)">修改密码</el-menu-item>
            <el-menu-item index="6" @click="toValue(6)">修改邮箱</el-menu-item>
          </el-menu-item-group>
        </el-submenu>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header style="text-align: right; font-size: 12px">
        <span>{{ rootName }}</span>
      </el-header>
      <ReviewArticleVue v-if="vue === 1" />
      <ReviewArticleRecords v-else-if="vue === 2" />
      <ReportArticle v-else-if="vue === 3"/>
      <ReportArticleRecords v-else-if="vue === 4"/>
      <UpdatePass v-else-if="vue === 5" style="margin-top: 10%"/> 
      <RootMailVue v-else-if="vue === 6" style="margin-top: 10%"/>
    </el-container> 
  </el-container>
</template>

<script>
import ReviewArticleVue from "./views/ReviewArticle.vue";
import RootMailVue from './views/RootMail.vue';
import ReviewArticleRecords from "./views/ReviewArticleRecords.vue";
import ReportArticle from "./views/ReportArticle.vue";
import ReportArticleRecords from "./views/ReportArticleRecords.vue";
import UpdatePass from "./views/UpdatePass.vue";
import {mapGetters } from 'vuex';
import axios from "axios";

export default {
  components: {
    ReviewArticleVue,//未审核
    ReviewArticleRecords,//审核记录
    ReportArticle,//被举报文章
    ReportArticleRecords,//被举报操作记录
    UpdatePass,//修改密码
    RootMailVue,//修改邮箱
  },
  data() {
    return {
      vue: 1, // 初始展示审核文章组件
      rootName: "",//管理员名字
    };
  },
  computed: {
    ...mapGetters(['getRoot']), // 将 Vuex 中的 getter 映射到组件的计算属性中
  },
  methods: {
    toValue(value) {
      this.vue = value;
    },
    async getRootName(){
      const rootId = this.getRoot
      const Id = {
        id : rootId
      }
      const resp = await axios.post("/api/getRootName", Id);
      this.rootName = resp.data;
    }
  },
  mounted() {
    this.getRootName();
  },
};
</script>

<style scoped>
.el-header {
  background-color: #b3c0d1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
}
</style>
