import {render, screen} from "@testing-library/react";
import LigneDRE from "./LigneDRE";
import {personnelItems} from "../../mocks/mockData";
import AuthContext from "../../context/AuthProvider";

test("devrait retourner pour une ligne un element tr et 10 elements td", () => {
    const {unmount} = render(
        <AuthContext.Provider value={{type: "personnel", id: null}}>
            <table>
                <tbody>
                <LigneDRE item={personnelItems[0]}/>
                </tbody>
            </table>
        </AuthContext.Provider>
    );
    expect(screen.getAllByRole("row")).toHaveLength(1);
    const td = screen.getAllByRole("cell");
    expect(td).toHaveLength(8);
    unmount();
});

test("devrait contenir les valeurs des proprietes de l'objet item a chaque element td", () => {
    const {unmount} = render(
        <AuthContext.Provider value={{type: "personnel", id: null}}>
            <table>
                <tbody>
                <LigneDRE item={personnelItems[0]}/>
                </tbody>
            </table>
        </AuthContext.Provider>
    );
    const {id, dateHeureSoumission, ...itemTest} = personnelItems[0];
    for (const key in itemTest) {
        expect(screen.getByText(itemTest[key], {exact: false, insensitive: true})).toBeInTheDocument();
    }
    unmount();
});

test("devrait retourner pour une ligne un element tr et 8 elements td", () => {
    const {nomEnseignant, matriculeEnseignant, ...enseignantItem} = personnelItems[0];
    const {unmount} = render(
        <AuthContext.Provider value={{type: "enseignant", id: 1}}>
            <table>
                <tbody>
                <LigneDRE item={enseignantItem}/>
                </tbody>
            </table>
        </AuthContext.Provider>
    );
    expect(screen.getAllByRole("row")).toHaveLength(1);
    expect(screen.getAllByRole("cell")).toHaveLength(6);
    unmount();
});

test("devrait retourner pour une ligne un element tr et 7 elements td", () => {
    const {matriculeEnseignant, nomEtudiant, codePermanentEtudiant, ...etudiantItem} = personnelItems[0];
    const {unmount} = render(
        <AuthContext.Provider value={{type: "etudiant", id: 1}}>
            <table>
                <tbody>
                <LigneDRE item={etudiantItem}/>
                </tbody>
            </table>
        </AuthContext.Provider>
    );
    expect(screen.getAllByRole("row")).toHaveLength(1);
    expect(screen.getAllByRole("cell")).toHaveLength(6);
    unmount();
});