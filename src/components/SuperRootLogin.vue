<template>
  <h2>超级管理员登录</h2>
  账号：<input type="text" v-model.lazy="username"><br>
  密码：<input type="password" v-model.lazy="password"><br>
  <button @click="login">登录</button>
</template>
<script>
export default {
  data() {
    return {
      username:"",
      password:""
    };
  },
  methods:{
    login(){
      this.$store.commit('updateSuperRoot', this.username);
      this.$store.commit('updateRoot', '');
      this.$store.commit('updateUserSetter', '');
      this.$router.push('/SuperRoot');
    }
  }
}
</script>
<style  scoped>

</style>