import React from 'react';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';

const style = {
    flexGrow: 1
}
const Navbar = () => {
    return (
        <div>
            <AppBar position="static" style={{backgroundColor:"#1e72bd"}} >
                <Toolbar>
                    <IconButton edge="start" aria-label="Menu">
                        <MenuIcon />
                    </IconButton>
                    <Typography variant="h6" style={style}>
                        ROLE
                    </Typography>
                    <Button >Login</Button>
                </Toolbar>
            </AppBar>
        </div>
    )
}
export default Navbar;
