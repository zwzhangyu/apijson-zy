import Vue from 'vue';
import {router} from './router';
import store from './store';
import App from './App';
import axios from 'axios';
// 引入和使用elementUI
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import VueResource from 'vue-resource';
import './config/http'
Vue.use(ElementUI)
Vue.use(VueResource);

  new Vue({
    el: '#app',
    router,
    store,
    template:'<App/>',
    components:{ App },
  })



