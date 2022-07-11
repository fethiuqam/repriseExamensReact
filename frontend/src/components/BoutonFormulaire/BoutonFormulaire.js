import { styled } from "@mui/material/styles";
import MuiButton from "@mui/material/Button";

const Button = styled(MuiButton)(({contained}) => ({
  backgroundColor: contained && '#0076B0',
  color: contained && 'oldlace'
}));


export default Button