import React from 'react';
import withStyles from "@material-ui/core/styles/withStyles";
import Typography from '@material-ui/core/Typography';
import SimpleTable from './SimpleTable/SimpleTable';
import DashboardStyle from "../../assets/jss/components/DashboardStyle";

class Dashboard extends React.Component {
  render() {
    const { classes } = this.props;

    return (
      <main className={classes.content}>
        <div className={classes.appBarSpacer} />
        <Typography variant="display1" gutterBottom>
          Products
        </Typography>
        <div className={classes.tableContainer}>
          <SimpleTable />
        </div>
      </main>
    );
  }
}

export default withStyles(DashboardStyle)(Dashboard);
