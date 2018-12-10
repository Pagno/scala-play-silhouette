import { connect } from "react-redux";
import { withRouter } from "react-router";
import React from 'react';
import { userService } from '../_services';
import { Route, Redirect } from 'react-router-dom';


export const PrivateRoute = ({ component: Component, ...rest }) => (
    <Route { ...rest } 
        render={(props) => ( userService.isAuthenticated() ? 
        ( <Component {...props} /> )
        :
        <Redirect to={{ pathname: '/login', state: { from: props.location } }} />
        )}
    />
)
/**
     <Route
        {...rest}
        render={props =>
            isAuthenticated ? (
                <Component {...props} />
            ) : (
                <Redirect
                    to={{
                        pathname: "/login",
                        state: { from: props.location }
                    }}
                />
            )
        }
    />
 */
/*
export const PrivateRoute = ({ component: Component, ...rest }) => (
    <Route {...rest} render={props => (
        localStorage.getItem('user') && userService.isAuthenticated() ? 
            <Component {...props} />
            : <Redirect to={{ pathname: '/login', state: { from: props.location } }} />
    )} />
)
/*
export default withRouter(
    connect(state => ({
        isAuthenticated: true // logic for verifying
    }))(ProtectedRoute)
);
*/