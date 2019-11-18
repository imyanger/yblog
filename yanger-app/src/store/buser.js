const state = {
    //保存用户登录的基本信息
    buser: localStorage.getItem('$buser') ? JSON.parse(localStorage.getItem('$buser')) : {}
}

const getters = {
    //获取登录信息
    getBuser: state => {
        return state.buser;
    }
}

const actions = {
    setBuser({commit}, item) {
        commit('setBuser', item);
    }
}

const mutations = {
    //保存登录信息
    setBuser(state, item) {
        state.buser = item;
        localStorage.setItem('$buser', JSON.stringify(item));
    },
    //注销登录
    clearBuser(state) {
        state.buser = {};
        localStorage.removeItem('$buser');
    }
}

export default {
    state,
    getters,
    actions,
    mutations
}