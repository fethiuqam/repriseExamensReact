import {render, screen} from "@testing-library/react";
import FiltreListeDRE from "./FiltreListeDRE"
import AuthContext from "../../context/AuthProvider";
import {statutsUtilisteurs} from "../../utils/utils";
import {TypeId} from "../../utils/const";

const mockSetFiltre = jest.fn();

const filtreInitial = {
    statuts: statutsUtilisteurs(TypeId.Personnel),
    etudiant: '',
    enseignant: '',
    cours: ''
}

test("devrait rendre 3 champs de texte et 2 boutons pour le personnel", () => {
    render(
        <AuthContext.Provider value={{type: TypeId.Personnel, id: null}}>
            <FiltreListeDRE
                filtre={filtreInitial}
                setFiltre={mockSetFiltre}
            />
        </AuthContext.Provider>
    );
    expect(screen.getAllByRole("textbox")).toHaveLength(3);
    expect(screen.getAllByRole("button")).toHaveLength(2);
});

test("devrait rendre 2 champs de texte et 2 boutons pour l'enseignat'", () => {
    render(
        <AuthContext.Provider value={{type:TypeId.Enseignant, id: 1}}>
            <FiltreListeDRE
                filtre={filtreInitial}
                setFiltre={mockSetFiltre}
            />
        </AuthContext.Provider>
    );
    expect(screen.getAllByRole("textbox")).toHaveLength(2);
    expect(screen.getAllByRole("button")).toHaveLength(2);
});

test("devrait rendre 2 champs de texte et 2 boutons pour l'etudiant", () => {
    render(
        <AuthContext.Provider value={{type: TypeId.Etudiant, id: 1}}>
            <FiltreListeDRE
                filtre={filtreInitial}
                setFiltre={mockSetFiltre}
            />
        </AuthContext.Provider>
    );
    expect(screen.getAllByRole("textbox")).toHaveLength(2);
    expect(screen.getAllByRole("button")).toHaveLength(2);
});


test("tous les champs de texte devraient etre vides", () => {
    render(
        <AuthContext.Provider value={{type: TypeId.Personnel, id: null}}>
            <FiltreListeDRE
                filtre={filtreInitial}
                setFiltre={mockSetFiltre}
            />
        </AuthContext.Provider>
    );
    const textboxes = screen.getAllByRole("textbox");
    textboxes.forEach(textbox => expect(textbox).toBeEmptyDOMElement());
});

test("tous les statuts devraient etre selectionnes au debut", () => {
    render(
        <AuthContext.Provider value={{type: TypeId.Personnel, id: null}}>
            <FiltreListeDRE
                filtre={filtreInitial}
                setFiltre={mockSetFiltre}
            />
        </AuthContext.Provider>
    );
    const filtreStatut = screen.getByTestId("filtre-statut");
    expect(filtreStatut).toHaveTextContent("SoumiseEn traitementAcceptéeRejetéeAnnuléePlanifiéeAbsenceComplétéeRetournée");
});
