import {render, screen} from "@testing-library/react";
import TableListeDRE from "./TableListeDRE";
import {personnelItems} from "../../mocks/mockData";
import AuthContext from "../../context/AuthProvider";
import {BrowserRouter} from "react-router-dom";
import {TypeId} from "../../utils/const";

const enseignantItems = personnelItems.map(item => {
    const {nomEnseignant, matriculeEnseignant, ...enseignantItem} = item;
    return enseignantItem;
})

const etudiantItems = personnelItems.map(item => {
    const {matriculeEnseignant, nomEtudiant, codePermanentEtudiant, ...etudiantItem} = item;
    return etudiantItem;
})

const personnelColonnes = [
    {name: 'Soumise le', prop: 'dateHeureSoumission', active: false},
    {name: 'Étudiant', prop: 'nomEtudiant', active: false},
    {name: 'Enseignant', prop: 'nomEnseignant', active: false},
    {name: 'Cours', prop: 'sigleCours', active: false},
    {name: 'Session', prop: 'session', active: false},
    {name: 'Statut', prop: 'statutCourant', active: false}
]

const enseignantColonnes = [
    {name: 'Soumise le', prop: 'dateHeureSoumission', active: false},
    {name: 'Étudiant', prop: 'nomEtudiant', active: false},
    {name: 'Cours', prop: 'sigleCours', active: false},
    {name: 'Session', prop: 'session', active: false},
    {name: 'Statut', prop: 'statutCourant', active: false}
]

const etudiantColonnes = [
    {name: 'Soumise le', prop: 'dateHeureSoumission', active: false},
    {name: 'Enseignant', prop: 'nomEnseignant', active: false},
    {name: 'Cours', prop: 'sigleCours', active: false},
    {name: 'Session', prop: 'session', active: false},
    {name: 'Statut', prop: 'statutCourant', active: false}
]

const mockTrier = jest.fn();

test("devrait retourner pour une table 4 elements tr, 7 elements th et 21 elements td pour personnel", () => {
    render(
        <AuthContext.Provider value={{type: TypeId.Personnel, permissions: []}}>
            <BrowserRouter>
                <TableListeDRE
                    items={personnelItems}
                    colonnes={personnelColonnes}
                    trier={mockTrier}
                    AArchiver={[]}
                />
            </BrowserRouter>
        </AuthContext.Provider>
    );
    expect(screen.getAllByRole("row")).toHaveLength(4);
    expect(screen.getAllByRole("columnheader")).toHaveLength(7);
    expect(screen.getAllByRole("cell")).toHaveLength(21);
});

test("devrait retourner pour une table 4 elements tr, 6 elements th et 18 elements td pour enseignant", () => {
    render(
        <AuthContext.Provider value={{type: TypeId.Enseignant, id: 1, permissions: []}}>
            <BrowserRouter>
                <TableListeDRE
                    items={enseignantItems}
                    colonnes={enseignantColonnes}
                    trier={mockTrier}
                    AArchiver={[]}
                />
            </BrowserRouter>
        </AuthContext.Provider>
    );
    expect(screen.getAllByRole("row")).toHaveLength(4);
    expect(screen.getAllByRole("columnheader")).toHaveLength(6);
    expect(screen.getAllByRole("cell")).toHaveLength(21);
});

test("devrait retourner pour une table 4 elements tr, 6 elements th et 18 elements td pour etudiant", () => {
    render(
        <AuthContext.Provider value={{type: TypeId.Etudiant, id: 1, permissions: []}}>
            <BrowserRouter>
                <TableListeDRE
                    items={etudiantItems}
                    colonnes={etudiantColonnes}
                    trier={mockTrier}
                    AArchiver={[]}
                />
            </BrowserRouter>
        </AuthContext.Provider>
    );
    expect(screen.getAllByRole("row")).toHaveLength(4);
    expect(screen.getAllByRole("columnheader")).toHaveLength(6);
    expect(screen.getAllByRole("cell")).toHaveLength(15);
});

test("devrait contenir les valeurs des entetes a chaque element th", () => {
    render(
        <AuthContext.Provider value={{type: TypeId.Personnel, permissions: []}}>
            <BrowserRouter>
                <TableListeDRE
                    items={personnelItems}
                    colonnes={personnelColonnes}
                    trier={mockTrier}
                    AArchiver={[]}
                />
            </BrowserRouter>
        </AuthContext.Provider>
    );
    for (const colonne of personnelColonnes) {
        expect(screen.getByRole('columnheader', {name: colonne["name"]})).toBeInTheDocument();
    }
});
