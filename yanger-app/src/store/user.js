const state = {
    //保存用户登录的基本信息
    user: localStorage.getItem('$user') ? JSON.parse(localStorage.getItem('$user')) : {}
}

const getters = {
    //获取登录信息
    getUser: state => {
        return state.user;
    }
}

const actions = {
    setUser({commit}, item) {
        commit('setUser', item);
    }
}

const mutations = {
    //保存登录信息
    setUser(state, item) {
        state.user = item;
        localStorage.setItem('$user', JSON.stringify(item));
    },
    //注销登录
    clearUser(state) {
        state.user = {};
        localStorage.removeItem('$user');
    }
}

export default {
    state,
    getters,
    actions,
    mutations
}