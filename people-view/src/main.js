import Vue from 'vue'
import App from './App.vue'

import VuejsDialog from 'vuejs-dialog';

import "bootstrap";

import "bootstrap/dist/css/bootstrap.css";
import "vuejs-dialog/dist/vuejs-dialog.min.css";

import {
  library
} from "@fortawesome/fontawesome-svg-core";

import {
  faPlus,
  faMinus,
  faTrash,
  faCheck
} from "@fortawesome/free-solid-svg-icons"

library.add(faPlus, faMinus, faTrash, faCheck);

Vue.config.productionTip = false

Vue.use(VuejsDialog);

new Vue({
  render: h => h(App),
}).$mount('#app')