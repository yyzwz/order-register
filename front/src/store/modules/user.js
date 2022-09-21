import Cookies from 'js-cookie';

const user = {
    state: {},
    mutations: {
        logout () {
            Cookies.remove('userInfo');
            localStorage.clear();
        }
    }
};

export default user;
