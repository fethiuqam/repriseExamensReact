import {render, screen} from "@testing-library/react";
import TableListeDRE from "./TableListeDRE";
import {personnelItems} from "../../mocks/mockData";
import AuthContext from "../../context/AuthProvider";

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
    const {unmount} = render(
        <AuthContext.Provider value={{type: "personnel", id: null}}>
            <TableListeDRE
                items={personnelItems}
                colonnes={personnelColonnes}
                trier={mockTrier}
            />
        </AuthContext.Provider>
    );
    expect(screen.getAllByRole("row")).toHaveLength(4);
    expect(screen.getAllByRole("columnheader")).toHaveLength(7);
    expect(screen.getAllByRole("cell")).toHaveLength(21);
    unmount();
});

test("devrait retourner pour une table 4 elements tr, 6 elements th et 18 elements td pour enseignant", () => {
    const {unmount} = render(
        <AuthContext.Provider value={{type: "enseignant", id: 1}}>
            <TableListeDRE
                items={enseignantItems}
                colonnes={enseignantColonnes}
                trier={mockTrier}
            />
        </AuthContext.Provider>
    );
    expect(screen.getAllByRole("row")).toHaveLength(4);
    expect(screen.getAllByRole("columnheader")).toHaveLength(6);
    expect(screen.getAllByRole("cell")).toHaveLength(18);
    unmount();
});

test("devrait retourner pour une table 4 elements tr, 6 elements th et 18 elements td pour etudiant", () => {
    const {unmount} = render(
        <AuthContext.Provider value={{type: "etudiant", id: 1}}>
            <TableListeDRE
                items={etudiantItems}
                colonnes={etudiantColonnes}
                trier={mockTrier}
            />
        </AuthContext.Provider>
    );
    expect(screen.getAllByRole("row")).toHaveLength(4);
    expect(screen.getAllByRole("columnheader")).toHaveLength(6);
    expect(screen.getAllByRole("cell")).toHaveLength(18);
    unmount();
});

test("devrait contenir les valeurs des entetes a chaque element th", () => {
    const {unmount} = render(
        <AuthContext.Provider value={{type: "personnel", id: null}}>
            <TableListeDRE
                items={personnelItems}
                colonnes={personnelColonnes}
                trier={mockTrier}
            />
        </AuthContext.Provider>
    );
    for (const colonne of personnelColonnes) {
        expect(screen.getByRole('columnheader', {name: colonne["name"]})).toBeInTheDocument();
    }
    unmount();
});
