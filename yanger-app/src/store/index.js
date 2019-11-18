import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

import user from './user';

import buser from './buser';

export default new Vuex.Store({
    modules: {
        user,
        buser
    }
})
