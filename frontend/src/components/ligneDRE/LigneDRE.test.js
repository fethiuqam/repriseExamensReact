import {render, screen} from "@testing-library/react";
import LigneDRE from "./LigneDRE";
import {commisItems} from "../../mocks/mockData";
import AuthContext from "../../context/AuthProvider";

test("devrait retourner pour une ligne un element tr et 10 elements td", () => {
    const {unmount} = render(
        <AuthContext.Provider value={{role: "commis", id: null}}>
            <table>
                <tbody>
                <LigneDRE item={commisItems[0]}/>
                </tbody>
            </table>
        </AuthContext.Provider>
    );
    expect(screen.getAllByRole("row")).toHaveLength(1);
    const td = screen.getAllByRole("cell");
    expect(td).toHaveLength(7);
    unmount();
});

test("devrait contenir les valeurs des proprietes de l'objet item a chaque element td", () => {
    const {unmount} = render(
        <AuthContext.Provider value={{role: "commis", id: null}}>
            <table>
                <tbody>
                <LigneDRE item={commisItems[0]}/>
                </tbody>
            </table>
        </AuthContext.Provider>
    );
    const {id, dateHeureSoumission, ...itemTest} = commisItems[0];
    for (const key in itemTest) {
        expect(screen.getByText(itemTest[key], {exact: false, insensitive: true})).toBeInTheDocument();
    }
    unmount();
});

test("devrait retourner pour une ligne un element tr et 8 elements td", () => {
    const {nomEnseignant, matriculeEnseignant, ...enseignantItem} = commisItems[0];
    const {unmount} = render(
        <AuthContext.Provider value={{role: "enseignant", id: 1}}>
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
    const {matriculeEnseignant, nomEtudiant, codePermanentEtudiant, ...etudiantItem} = commisItems[0];
    const {unmount} = render(
        <AuthContext.Provider value={{role: "etudiant", id: 1}}>
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