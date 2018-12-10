import React from 'react';
import { PrivateRoute } from '../_components';
import CssBaseline from '@material-ui/core/CssBaseline';
import { connect } from 'react-redux';
import withStyles from "@material-ui/core/styles/withStyles";
import { appActions } from '../_actions';
import { Sidebar } from './Sidebar/Sidebar';
import Header from './Header/Header';
import { Section } from './Section/Section';
import Dashboard from './Dashboard/Dashboard';

import HomePageStyle from "../assets/jss/layouts/HomePageStyle";

class HomePage extends React.Component {
    constructor(props) {
        super(props);
        this.props.dispatch(appActions.getMenu());
    }

    componentDidMount() {
        //this.props.dispatch(userActions.getAll());
    }

    handleDeleteUser(id) {
        return (e) => this.props.dispatch(userActions.delete(id));
    }

    render() {
        const { classes, menu } = this.props;
        //        const { menu } = this.state;

        return (
            <React.Fragment>
                <CssBaseline />
                <div className={classes.root}>
                    <Header />
                    <Sidebar menu={menu} />
                    <Dashboard />
                </div>
            </React.Fragment>
        );
    }
}

function mapStateToProps(state) {
    const { users, authentication } = state;
    const { user } = authentication;
    const { menu } = state;

    return {
        user,
        users,
        menu
    };
}

const connectedHomePage = connect(mapStateToProps)(HomePage);
const connectedHomePageStyled = withStyles(HomePageStyle)(connectedHomePage)
export { connectedHomePageStyled as HomePage }