import config from 'config';
import { userAction } from '../_actions';
import { API } from '../_helpers';

export const appService = {
    getMenu,
    getMenu_old
};

function getMenu_old() {
    const requestOptions = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json;charset=utf-8',
            'Acctep': 'application/json;'
        }
    };

    return fetch(`${config.apiUrl}/menu`, requestOptions)
        .then(handleResponse)
        .then(menu => {
            return menu;
        });
}

function getMenu() {

    return API.get('menu')
        // .then(handleResponse)
        .then(menu => {
            return menu;
        });
}

function handleResponse(response) {

    if (!response.statusText === 'OK') {
        if (response.status === 401) {
            // auto logout if 401 response returned from api
            userAction.logout();
            location.reload(true);
        }

        const error = (data && data.message) || response.statusText;
        return Promise.reject(error);
    }

    return response.data;
}