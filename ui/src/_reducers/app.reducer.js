import { appConstants } from '../_constants';

export function app(state = {}, action) {
    switch (action.type) {
        case appConstants.MENU_REQUEST:
            return {
                menu: action.menu
            };
        default:
            return state
    }
}