import { appConstants } from '../_constants';
import { appService } from '../_services';

export const appActions = {
    getMenu
};

function getMenu() {
    return dispatch => {

        appService.getMenu()
            .then(
                menu => {
                    dispatch(success(menu.data));
                },
                error => {
                    dispatch(failure(error));
                }
            );
    };

    function success(menu) {
        var a = menu.menu;
        return { type: appConstants.MENU_REQUEST, menu: a }
    }


    function failure(error) {
        return { type: appConstants.MENU_REQUEST, error }
    }
}