<template>
  <div class="password-change-container">
    <div class="input-group" :class="{ 'input-error': isEmpty(currentPassword) }">
      <label>原密码：</label>
      <input type="password" v-model="currentPassword">
    </div>
    <div class="input-group" :class="{ 'input-error': isEmpty(newPassword) }">
      <label>新密码：</label>
      <input type="password" v-model="newPassword">
    </div>
    <div class="input-group" :class="{ 'input-error': isEmpty(confirmPassword) || newPassword !== confirmPassword }">
      <label>确认密码：</label>
      <input type="password" v-model="confirmPassword">
    </div>
    <div class="error-message" v-if="errorMessage">{{ errorMessage }}</div>
    <div class="button-group">
      <button @click="validatePasswords">确认</button>
      <button @click="resetForm">重置</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      currentPassword: '',
      newPassword: '',
      confirmPassword: '',
      errorMessage: ''
    };
  },
  methods: {
    isEmpty(value) {
      return !value.trim();
    },
    async validatePasswords() {
      if (this.isEmpty(this.currentPassword) || this.isEmpty(this.newPassword) || this.isEmpty(this.confirmPassword)) {
        this.errorMessage = '所有字段均不能为空';
      } else if (this.newPassword !== this.confirmPassword) {
        this.errorMessage = '新密码和确认密码不匹配';
      } else if(this.currentPassword === this.newPassword){
        this.errorMessage = '新密码和旧密码相同';
      }else {
        const pwd = {
          id : 1,
          oldPwd : this.currentPassword,
          newPwd : this.newPassword
        }
        const resp = await axios.post('/api/superUpdatePass', pwd)
        if(resp.data.code === 0){
          this.$message({
            message: '修改成功',
            type: 'success'
          });
          this.$store.commit("updateSuperRoot", "");
          this.$router.push('/RootLogin')
        }else{
          this.$message({
            message: resp.data.message,
          })
        }
      }
    },
    resetForm() {
      this.currentPassword = '';
      this.newPassword = '';
      this.confirmPassword = '';
      this.errorMessage = '';
    }
  }
};
</script>

<style scoped>
.password-change-container {
  width: 30%;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 10px;
  background-color: #f9f9f9;
}

.input-group {
  margin-bottom: 15px;
}

.input-group.input-error input {
  border-color: red;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

input {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.error-message {
  color: red;
  margin-bottom: 15px;
}

.button-group {
  display: flex;
  justify-content: space-between;
}

button {
  width: 48%;
  padding: 10px;
  border: none;
  border-radius: 5px;
  background-color: #007BFF;
  color: white;
  cursor: pointer;
  font-size: 16px;
}

button:hover {
  background-color: #0056b3;
}
</style>
