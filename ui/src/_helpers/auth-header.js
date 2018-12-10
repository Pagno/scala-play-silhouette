export function authHeader() {
    // return authorization header with jwt token
    let user = JSON.parse(localStorage.getItem('user'));
    if (user && user.token) {
        return {
            'X-Auth-Token': user.token,
            'Content-Type': 'application/json;charset=utf-8'
        };
    } else {
        return {};
    }
}