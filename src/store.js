// store.js
import { createStore } from 'vuex';

const store = createStore({
  state: {
    superRoot:'',//超级管理员账号
    root : '',//管理员账号
    userSetter:''//用户账号
  },
  
  mutations: {
    updateSuperRoot(state, newValue) {
      state.superRoot = newValue;
    },
    updateRoot(state, newValue) {
      state.root = newValue;
    },
    updateUserSetter(state, newValue) {
      state.userSetter = newValue;
    },
  },
  getters: {
    getSuperRoot: (state) => {
      return state.superRoot;
    },
    getRoot: (state) => {
      return state.root;
    },
    getUserSetter: (state) => {
      return state.userSetter;
    },
  }
});

export default store;
