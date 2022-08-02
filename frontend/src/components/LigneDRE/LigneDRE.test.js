import {render, screen} from "@testing-library/react";
import LigneDRE from "./LigneDRE";
import {personnelItems} from "../../mocks/mockData";
import AuthContext from "../../context/AuthProvider";
import {BrowserRouter} from "react-router-dom";
import {TypeId} from "../../utils/const";

test("devrait retourner pour une ligne un element tr et 10 elements td", () => {
    render(
        <AuthContext.Provider value={{type: TypeId.Personnel, permissions: []}}>
            <BrowserRouter>
                <table>
                    <tbody>
                    <LigneDRE item={personnelItems[0]}/>
                    </tbody>
                </table>
            </BrowserRouter>
        </AuthContext.Provider>
    );
    expect(screen.getAllByRole("row")).toHaveLength(1);
    const td = screen.getAllByRole("cell");
    expect(td).toHaveLength(7);
});

test("devrait retourner pour une ligne un element tr et 8 elements td", () => {
    const {nomEnseignant, matriculeEnseignant, ...enseignantItem} = personnelItems[0];
    render(
        <AuthContext.Provider value={{type: TypeId.Enseignant, id: 1, permissions: []}}>
            <BrowserRouter>
                <table>
                    <tbody>
                    <LigneDRE item={enseignantItem}/>
                    </tbody>
                </table>
            </BrowserRouter>
        </AuthContext.Provider>
    );
    expect(screen.getAllByRole("row")).toHaveLength(1);
    expect(screen.getAllByRole("cell")).toHaveLength(7);
});

test("devrait retourner pour une ligne un element tr et 7 elements td", () => {
    const {matriculeEnseignant, nomEtudiant, codePermanentEtudiant, ...etudiantItem} = personnelItems[0];
    render(
        <AuthContext.Provider value={{type: TypeId.Etudiant, id: 1, permissions: []}}>
            <BrowserRouter>
                <table>
                    <tbody>
                    <LigneDRE item={etudiantItem}/>
                    </tbody>
                </table>
            </BrowserRouter>
        </AuthContext.Provider>
    );
    expect(screen.getAllByRole("row")).toHaveLength(1);
    expect(screen.getAllByRole("cell")).toHaveLength(5);
});