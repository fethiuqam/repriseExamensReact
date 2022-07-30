import { useState } from "react";
import { useGridApiContext } from "@mui/x-data-grid";
import { FormControl, MenuItem, Select } from "@mui/material";

/**
 * Permet de choisir une ou plusieurs valeurs parmi une liste, dans une cellule DataGrid.
 *
 * @see https://mui.com/x/react-data-grid/
 */
export default function GridMultiSelect({
  /**
   * L'id de la rangée
   * @type number
   */
  id,
  /**
   * L'identifiant de la colonne.
   * @type string
   */
  field,
  /**
   * La valeur de la cellule.
   * @type Array<string | Object>
   * T doit avoir une propriété id.
   */
  value,
  /**
   * Les items parmi lesquels choisir.
   * Doit être passé par référence.
   * @type Array<string | Object>
   */
  items,
  /**
   * Fournir seulement si value est de type Array<Object>.
   * Le nom du champs de l'objet à afficher dans les choix.
   * @type string
   */
  label,
  /**
   * Fonction d'affichage de la valeur.
   * @type: function(items: Array<string | Object>): JSX.Element
   */
  renderValue,
}) {
  const [itemsChoisis, setItemsChoisis] = useState(
    items.filter(
      (item) =>
        value &&
        value?.some((choisi) =>
          typeof choisi === "object" ? choisi.id === item.id : choisi === item
        )
    )
  );

  const gridApi = useGridApiContext();

  return (
    <FormControl sx={{ width: "100%" }}>
      <Select
        multiple
        value={itemsChoisis}
        onChange={({ target: { value } }) => {
          setItemsChoisis(typeof value === "string" ? value.split(",") : value);
          gridApi.current.setEditCellValue({
            id,
            field,
            value,
          });
        }}
        renderValue={renderValue}
      >
        {items.map((item) => (
          <MenuItem
            key={typeof item === "object" ? item.id : item}
            value={item}
          >
            {typeof item === "object" ? item[label] : item}
          </MenuItem>
        ))}
      </Select>
    </FormControl>
  );
}
