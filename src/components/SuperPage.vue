<template>
  <el-container style="height: 97.6vh; border: 1px solid #eee">
    <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
      <el-menu default-active="1">
        <el-submenu index="1">
          <el-menu-item-group>
            <template v-slot:title>管理管理员</template>
            <el-menu-item index="1" @click="toValue(1)">管理</el-menu-item>
          </el-menu-item-group>
          <el-menu-item-group title="修改个人信息">
            <el-menu-item index="2" @click="toValue(2)">修改密码</el-menu-item>
            <el-menu-item index="3" @click="toValue(3)">修改用户名</el-menu-item>
            <el-menu-item index="4" @click="toValue(4)">修改邮箱</el-menu-item>
          </el-menu-item-group>
        </el-submenu>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header style="text-align: right; font-size: 25px; text-align: center">
        <span>超级管理员</span>
      </el-header>
      <SuperRootVue v-if="vue === 1" />
      <SuperUpdatePassVue v-else-if="vue === 2" style="margin-top: 12%"/>
      <SuperUpdateNameVue v-else-if="vue === 3" style="margin-top: 15%"/>
      <RootMailVue v-else-if="vue === 4" style="margin-top: 15%"/>
    </el-container> 
  </el-container>
</template>

<script>
import RootMailVue from './views/RootMail.vue';
import SuperRootVue from './views/SuperRoot.vue';
import SuperUpdateNameVue from './views/SuperUpdateName.vue';
import SuperUpdatePassVue from './views/SuperUpdatePass.vue';

export default {
  components: {
    SuperRootVue,//管理管理员
    SuperUpdatePassVue,//超级管理员修改密码
    SuperUpdateNameVue,//修改用户名
    RootMailVue,//修改邮箱
  },
  data() {
    return {
      vue: 1, // 初始展示审核文章组件
      rootName: "",//管理员名字
    };
  },
  computed: {
    
  },
  methods: {
    toValue(vue){
      this.vue = vue
    }
    
  },
  mounted() {
    
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
