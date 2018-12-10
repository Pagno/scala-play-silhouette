import axios from 'axios';
import config from 'config';
import { authHeader } from '.';
import { history } from '../_helpers';

export const API = axios.create({
    baseURL: `${config.apiUrl}`,
    headers: authHeader()
});

// Response
API.interceptors.response.use(
    (response) => {
        return response
    },
    (error) => {
        const originalRequest = error.config
            // token expired
        if (error.response.status == 401) { // && error.response.data.error == "token_expired") {
            console.log("ERROR API: " + error);

            history.push('/login');

            //originalRequest._retry = true
            /*
            store.dispatch('refreshToken').then((response) => {
                // console.log(response)
                let token = response.data.token
                let headerAuth = 'Bearer ' + response.data.token
                store.dispatch('saveToken', token)
                axios.defaults.headers['Authorization'] = headerAuth
                originalRequest.headers['Authorization'] = headerAuth
                return axios(originalRequest)
            }).catch((error) => {
                store.dispatch('logout').then(() => {
                    router.push({ name: 'login' })
                }).catch(() => {
                    router.push({ name: 'login' })
                })
            })
            */
        }
        return Promise.reject(error)
    }
);