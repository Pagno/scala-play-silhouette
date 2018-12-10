import React from 'react';
import classNames from "classnames";
import { loadCSS } from 'fg-loadcss/src/loadCSS';
import withStyles from "@material-ui/core/styles/withStyles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import IconButton from "@material-ui/core/IconButton";
import MenuIcon from '@material-ui/icons/Menu';
import Search from '@material-ui/icons/Search';
import Typography from '@material-ui/core/Typography';
import Input from '@material-ui/core/Input';
import Icon from '@material-ui/core/Icon';
import InputAdornment from '@material-ui/core/InputAdornment';
import HeaderStyle from "../../assets/jss/components/HeaderStyle";

class Header extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      open: true
    };
  }

  componentDidMount() {
    loadCSS(
      'https://use.fontawesome.com/releases/v5.1.0/css/all.css',
      document.querySelector('#insertion-point-jss'),
    );
  }

  render() {
    const { classes } = this.props;

    return (
      <AppBar
        position="absolute"
        className={classNames(classes.appBar, this.state.open && classes.appBarShift)}
      >
        <Toolbar disableGutters={!this.state.open} className={classes.toolbar}>
          <IconButton
            color="inherit"
            aria-label="Open drawer"
            onClick={this.handleDrawerOpen}
            className={classNames(
              classes.menuButton,
              this.state.open && classes.menuButtonHidden,
            )}
          >
            <MenuIcon />
          </IconButton>
          <Typography variant="title" color="inherit" noWrap className={classes.title}>
            Dashboard
          </Typography>
          <Input
            disableUnderline={true}
            placeholder="Search"
            className={classes.input}
            startAdornment={
              <InputAdornment position="start">
                <Search color="action" />
              </InputAdornment>
            }
          />
          <IconButton color="inherit">
            <Icon className={classNames(classes.icon, 'fas fa-sign-out-alt')} />
          </IconButton>
        </Toolbar>
      </AppBar>
    );
  }
}

//const connectedHomePage = connect(mapStateToProps)(HomePage);

/*const HeaderStyled =  withStyles(HeaderStyle)(Header);
export {HeaderStyled as Header}*/

export default withStyles(HeaderStyle)(Header);