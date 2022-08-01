import {render, screen, fireEvent, within} from "@testing-library/react";
import user from "@testing-library/user-event"
import FiltreListeDRE from "./FiltreListeDRE"
import AuthContext from "../../context/AuthProvider";
import {STATUTS} from "../../utils/const";

const mockFiltrer = jest.fn();

test("devrait rendre 3 champs de texte et 2 boutons pour le personnel", () => {
    render(
        <AuthContext.Provider value={{type: "personnel", id: null}}>
            <FiltreListeDRE
                statuts={STATUTS}
                filtrer={mockFiltrer}
            />
        </AuthContext.Provider>
    );
    expect(screen.getAllByRole("textbox")).toHaveLength(3);
    expect(screen.getAllByRole("button")).toHaveLength(2);
});

test("devrait rendre 2 champs de texte et 2 boutons pour l'enseignat'", () => {
    render(
        <AuthContext.Provider value={{type: "enseignant", id: 1}}>
            <FiltreListeDRE
                statuts={STATUTS}
                filtrer={mockFiltrer}
            />
        </AuthContext.Provider>
    );
    expect(screen.getAllByRole("textbox")).toHaveLength(2);
    expect(screen.getAllByRole("button")).toHaveLength(2);
});

test("devrait rendre 2 champs de texte et 2 boutons pour l'etudiant", () => {
    render(
        <AuthContext.Provider value={{type: "etudiant", id: 1}}>
            <FiltreListeDRE
                statuts={STATUTS}
                filtrer={mockFiltrer}
            />
        </AuthContext.Provider>
    );
    expect(screen.getAllByRole("textbox")).toHaveLength(2);
    expect(screen.getAllByRole("button")).toHaveLength(2);
});


test("tous les champs de texte devraient etre vides", () => {
    render(
        <AuthContext.Provider value={{type: "personnel", id: null}}>
            <FiltreListeDRE
                statuts={STATUTS}
                filtrer={mockFiltrer}
            />
        </AuthContext.Provider>
    );
    const textboxes = screen.getAllByRole("textbox");
    textboxes.forEach(textbox => expect(textbox).toBeEmptyDOMElement());
});

test("tous les statuts devraient etre selectionnes au debut", () => {
    render(
        <AuthContext.Provider value={{type: "personnel", id: null}}>
            <FiltreListeDRE
                statuts={STATUTS}
                filtrer={mockFiltrer}
            />
        </AuthContext.Provider>
    );
    const filtreStatut = screen.getByTestId("filtre-statut");
    expect(filtreStatut).toHaveTextContent("EnregistréeSoumiseEn traitementAcceptéeRejetéeAnnuléePlanifiéeAbsenceComplétée");
});

test("devrait enlever puis ajouter une option au filtre statut en cliquant sur une option", () => {
    render(
        <AuthContext.Provider value={{type: "personnel", id: null}}>
            <FiltreListeDRE
                statuts={STATUTS}
                filtrer={mockFiltrer}
            />
        </AuthContext.Provider>
    );
    const filtreStatut = screen.getByTestId("filtre-statut");
    fireEvent.mouseDown(screen.getAllByRole("button")[0]);
    fireEvent.click(within(screen.getByRole('listbox')).getByText(/Enregistrée/i));
    expect(filtreStatut).toHaveTextContent("SoumiseEn traitementAcceptéeRejetéeAnnuléePlanifiéeAbsenceComplétée");
    fireEvent.click(within(screen.getByRole('listbox')).getByText(/Enregistrée/i));
    expect(filtreStatut).toHaveTextContent("SoumiseEn traitementAcceptéeRejetéeAnnuléePlanifiéeAbsenceComplétéeRetournéeEnregistrée");
});

test("devrait reinitialiser les champs avec le bouton reinitialiser", () => {
    render(
        <AuthContext.Provider value={{type: "personnel", id: null}}>
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
});

test("devrait reinitialiser le filtre statut avec le bouton reinitialiser", () => {
    render(
        <AuthContext.Provider value={{type: "personnel", id: null}}>
            <FiltreListeDRE
                statuts={STATUTS}
                filtrer={mockFiltrer}
            />
        </AuthContext.Provider>
    );
    const filtreStatut = screen.getByTestId("filtre-statut");
    const boutonReinitialiser = screen.getByRole("button", {name: "Réinitialiser le filtre"});
    fireEvent.mouseDown(screen.getAllByRole("button")[0]);
    fireEvent.click(within(screen.getByRole('listbox')).getByText(/Enregistrée/i));
    fireEvent.click(within(screen.getByRole('listbox')).getByText(/Rejetée/i));
    expect(filtreStatut).toHaveTextContent("SoumiseEn traitementAcceptéeAnnuléePlanifiéeAbsenceComplétée");
    fireEvent.click(boutonReinitialiser);
    expect(filtreStatut).toHaveTextContent("EnregistréeSoumiseEn traitementAcceptéeRejetéeAnnuléePlanifiéeAbsenceComplétée");
});
