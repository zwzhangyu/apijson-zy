//vuex的使用：先安装vuex，npm install vuex --save
import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

const state = {
    collapse: false,
    routesList: [],
    buttonList: new Set(),
};

// const commonApi= {
//     dev:{
//         proxyTable:{
//             "/": {
//                 target: "http://127.0.0.1:8080/", //设置调用的接口域名和端口
//                 changeOrigin: true
//             }
//         }
//     }}
const mutations = {
    setCollapse(state, collapse) {
        state.collapse = collapse;
    },
    setRouteList(state, list) {
        state.routesList = list;
    },
};
const actions = {};
export default new Vuex.Store({
    state,
    actions,
    mutations,
    modules: {}
})
