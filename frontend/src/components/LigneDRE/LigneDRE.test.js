import {render, screen} from "@testing-library/react";
import LigneDRE from "./LigneDRE";
import {personnelItems} from "../../mocks/mockData";
import AuthContext from "../../context/AuthProvider";
import {BrowserRouter} from "react-router-dom";

test("devrait retourner pour une ligne un element tr et 10 elements td", () => {
    render(
        <AuthContext.Provider value={{type: "personnel", id: null}}>
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
    expect(td).toHaveLength(8);
});

test("devrait retourner pour une ligne un element tr et 8 elements td", () => {
    const {nomEnseignant, matriculeEnseignant, ...enseignantItem} = personnelItems[0];
    render(
        <AuthContext.Provider value={{type: "enseignant", id: 1}}>
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
    expect(screen.getAllByRole("cell")).toHaveLength(6);
});

test("devrait retourner pour une ligne un element tr et 7 elements td", () => {
    const {matriculeEnseignant, nomEtudiant, codePermanentEtudiant, ...etudiantItem} = personnelItems[0];
    render(
        <AuthContext.Provider value={{type: "etudiant", id: 1}}>
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
    expect(screen.getAllByRole("cell")).toHaveLength(6);
});