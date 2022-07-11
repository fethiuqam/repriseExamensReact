import {render, screen, fireEvent, within} from "@testing-library/react";
import user from "@testing-library/user-event"
import FiltreListeDRE from "./FiltreListeDRE"
import AuthContext from "../../context/AuthProvider";

const STATUTS = [
    'ENREGISTREE',
    'SOUMISE',
    'EN_TRAITEMENT',
    'ACCEPTEE',
    'VALIDEE',
    'REJETEE',
    'ANNULEE',
    'COMPLETEE'
];

const mockFiltrer = jest.fn();

test("devrait rendre 3 champs de texte et 2 boutons pour le commis", () => {
    const {unmount} = render(
        <AuthContext.Provider value={{role: "commis", id: null}}>
            <FiltreListeDRE
                statuts={STATUTS}
                filtrer={mockFiltrer}
            />
        </AuthContext.Provider>
    );
    expect(screen.getAllByRole("textbox")).toHaveLength(3);
    expect(screen.getAllByRole("button")).toHaveLength(2);
    unmount();
});

test("devrait rendre 2 champs de texte et 2 boutons pour l'enseignat'", () => {
    const {unmount} = render(
        <AuthContext.Provider value={{role: "enseignant", id: 1}}>
            <FiltreListeDRE
                statuts={STATUTS}
                filtrer={mockFiltrer}
            />
        </AuthContext.Provider>
    );
    expect(screen.getAllByRole("textbox")).toHaveLength(2);
    expect(screen.getAllByRole("button")).toHaveLength(2);
    unmount();
});

test("devrait rendre 2 champs de texte et 2 boutons pour l'etudiant", () => {
    const {unmount} = render(
        <AuthContext.Provider value={{role: "etudiant", id: 1}}>
            <FiltreListeDRE
                statuts={STATUTS}
                filtrer={mockFiltrer}
            />
        </AuthContext.Provider>
    );
    expect(screen.getAllByRole("textbox")).toHaveLength(2);
    expect(screen.getAllByRole("button")).toHaveLength(2);
    unmount();
});


test("tous les champs de texte devraient etre vides", () => {
    const {unmount} = render(
        <AuthContext.Provider value={{role: "commis", id: null}}>
            <FiltreListeDRE
                statuts={STATUTS}
                filtrer={mockFiltrer}
            />
        </AuthContext.Provider>
    );
    const textboxes = screen.getAllByRole("textbox");
    textboxes.forEach(textbox => expect(textbox).toBeEmptyDOMElement());
    unmount();
});

test("tous les statuts devraient etre selectionnes au debut", () => {
    const {unmount} = render(
        <AuthContext.Provider value={{role: "commis", id: null}}>
            <FiltreListeDRE
                statuts={STATUTS}
                filtrer={mockFiltrer}
            />
        </AuthContext.Provider>
    );
    const filtreStatut = screen.getByTestId("filtre-statut");
    expect(filtreStatut).toHaveTextContent("ENREGISTREESOUMISEEN_TRAITEMENTACCEPTEEVALIDEEREJETEEANNULEECOMPLETEE");
    unmount();
});

test("devrait enlever puis ajouter une option au filtre statut en cliquant sur une option", () => {
    const {unmount} = render(
        <AuthContext.Provider value={{role: "commis", id: null}}>
            <FiltreListeDRE
                statuts={STATUTS}
                filtrer={mockFiltrer}
            />
        </AuthContext.Provider>
    );
    const filtreStatut = screen.getByTestId("filtre-statut");
    fireEvent.mouseDown(screen.getAllByRole("button")[0]);
    fireEvent.click(within(screen.getByRole('listbox')).getByText(/ENREGISTREE/i));
    expect(filtreStatut).toHaveTextContent("SOUMISEEN_TRAITEMENTACCEPTEEVALIDEEREJETEEANNULEECOMPLETEE");
    fireEvent.click(within(screen.getByRole('listbox')).getByText(/ENREGISTREE/i));
    expect(filtreStatut).toHaveTextContent("SOUMISEEN_TRAITEMENTACCEPTEEVALIDEEREJETEEANNULEECOMPLETEEENREGISTREE");
    unmount();
});

test("devrait reinitialiser les champs avec le bouton reinitialiser", () => {
    const {unmount} = render(
        <AuthContext.Provider value={{role: "commis", id: null}}>
            <FiltreListeDRE
                statuts={STATUTS}
                filtrer={mockFiltrer}
            />
        </AuthContext.Provider>
    );
    const textboxes = screen.getAllByRole("textbox");
    textboxes.forEach(textbox => user.type(textbox, "texte"));
    textboxes.forEach(textbox => expect(textbox).toHaveValue("texte"));
    fireEvent.click(screen.getByRole("button", {name: "Réinitialiser le filtre"}));
    textboxes.forEach(textbox => expect(textbox).toBeEmptyDOMElement());
    unmount();
});

test("devrait reinitialiser le filtre statut avec le bouton reinitialiser", () => {
    const {unmount} = render(
        <AuthContext.Provider value={{role: "commis", id: null}}>
            <FiltreListeDRE
                statuts={STATUTS}
                filtrer={mockFiltrer}
            />
        </AuthContext.Provider>
    );
    const filtreStatut = screen.getByTestId("filtre-statut");
    const boutonReinitialiser = screen.getByRole("button", {name: "Réinitialiser le filtre"});
    fireEvent.mouseDown(screen.getAllByRole("button")[0]);
    fireEvent.click(within(screen.getByRole('listbox')).getByText(/ENREGISTREE/i));
    fireEvent.click(within(screen.getByRole('listbox')).getByText(/REJETEE/i));
    expect(filtreStatut).toHaveTextContent("SOUMISEEN_TRAITEMENTACCEPTEEVALIDEEANNULEECOMPLETEE");
    fireEvent.click(boutonReinitialiser);
    expect(filtreStatut).toHaveTextContent("ENREGISTREESOUMISEEN_TRAITEMENTACCEPTEEVALIDEEREJETEEANNULEECOMPLETEE");
    unmount();
});