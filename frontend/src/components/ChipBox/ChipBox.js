import { Box, Chip } from "@mui/material";

/**
 * Une boîte qui affiche des Chips.
 * @see https://mui.com/material-ui/react-chip/
 * @prop labels: string[]
 */
export default function ChipBox({ labels }) {
  return (
    <Box
      sx={{
        display: "flex",
        flexWrap: "wrap",
        gap: 0.5,
      }}
    >
      {labels?.map((label, index) => (
        <Chip key={index} label={label} />
      ))}
    </Box>
  );
}
