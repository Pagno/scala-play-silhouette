import React from 'react';
import classNames from 'classnames';
import config from 'config';
import { connect } from 'react-redux';
import Dashboard from "@material-ui/icons/Dashboard";
import { NavLink } from "react-router-dom";
import withStyles from "@material-ui/core/styles/withStyles";
import Drawer from "@material-ui/core/Drawer";
import Hidden from "@material-ui/core/Hidden";
import IconButton from "@material-ui/core/IconButton";
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import Divider from '@material-ui/core/Divider';
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import ListItemText from "@material-ui/core/ListItemText";
import SidebarStyle from "../../assets/jss/components/SidebarStyle";



class Sidebar extends React.Component {
  constructor(props) {
    super(props);

    this.mapMenu = this.mapMenu.bind(this);

    this.state = {
      open: true
    };
  }

  mapMenu(menu, classes) {
    return menu.map(m => {
      return <NavLink
        to={m.to}
        className={classes.item}
        activeClassName="active"
        key={m.key}
      >
        <ListItem button className={classes.itemLink}>
          <ListItemIcon className={classes.itemIcon}>
            <Dashboard />
          </ListItemIcon>
          <ListItemText
            primary={m.text}
            className={classes.itemText}
            disableTypography={true}
          />
        </ListItem>
      </NavLink>
    })
  }
  render() {
    const { classes, menu } = this.props;

    var brand = (
      <div className={classes.logo}>
        <a href="https://www.creative-tim.com" className={classes.logoLink}>
          <div className={classes.logoImage}>
            <img src={`${config.apiUrl}/assets/images/logo.png`} alt="logo" className={classes.img} />
          </div>
          asd
        </a>
      </div>
    );


    return (
      <Drawer
        variant="permanent"
        classes={{
          paper: classNames(classes.drawerPaper, !this.state.open && classes.drawerPaperClose),
        }}
        open={this.state.open}
      >
        <div className={classes.toolbarIcon}>
          <IconButton onClick={this.handleDrawerClose}>
            <ChevronLeftIcon />
          </IconButton>
        </div>
        <Divider />
        <List>{menu != undefined && this.mapMenu(menu, classes)}</List>
      </Drawer>
    );
  }
}

function mapStateToProps(state) {
  const { menu } = state.app;

  return {
    menu
  };
}

const connectedSidebar = connect(mapStateToProps)(Sidebar);
const sidebarStyled = withStyles(SidebarStyle)(connectedSidebar);
export { sidebarStyled as Sidebar }
