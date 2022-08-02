import {fireEvent, render, screen, within} from '@testing-library/react'
import {mockServer} from '../../mocks/server'
import ListeDRE from './ListeDRE'
import AuthContext from "../../context/AuthProvider";
import user from "@testing-library/user-event";
import {BrowserRouter} from 'react-router-dom';
import {TypeId} from "../../utils/const";

beforeAll(() => mockServer.listen({
    onUnhandledRequest: 'error'
}))

afterEach(() => mockServer.resetHandlers())

afterAll(() => mockServer.close())

test('devrait rendre une barre de progression avant chargement', () => {
    render(
        <AuthContext.Provider value={{type: TypeId.Personnel, permission: []}}>
            <BrowserRouter>
                <ListeDRE/>
            </BrowserRouter>
        </AuthContext.Provider>
    );
    expect(screen.getByRole("progressbar")).toBeInTheDocument();
})

test('devrait rendre 4 lignes du tableau apres chargement', async () => {
    render(
        <AuthContext.Provider value={{type: TypeId.Personnel, permissions: []}}>
            <BrowserRouter>
                <ListeDRE/>
            </BrowserRouter>
        </AuthContext.Provider>
    );
    expect(await screen.findAllByRole("row")).toHaveLength(4);
})

test('devrait enlever une ligne en deselectionnant un statut du filtre',
    async () => {
        render(
            <AuthContext.Provider value={{type: TypeId.Personnel, permissions: []}}>
                <BrowserRouter>
                    <ListeDRE/>
                </BrowserRouter>
            </AuthContext.Provider>
        );
        expect(await screen.findAllByRole("row")).toHaveLength(4);
        const select = screen.getAllByRole("button")[0];
        fireEvent.mouseDown(select);
        fireEvent.click(within(screen.getByRole('listbox')).getByText(/Acceptée/i));
        user.keyboard('{esc}');
        expect(screen.getAllByRole("row")).toHaveLength(3);
    })

test('devrait enlever les lignes ne correspondant pas au filtre etudiant',
    async () => {
        render(
            <AuthContext.Provider value={{type: TypeId.Personnel, permissions: []}}>
                <BrowserRouter>
                    <ListeDRE/>
                </BrowserRouter>
            </AuthContext.Provider>
        );
        expect(await screen.findAllByRole("row")).toHaveLength(4);
        const filtreEtudiant = screen.getByLabelText(/Par étudiant/i);
        fireEvent.change(filtreEtudiant, {target: {value: "jean"}});
        expect(screen.getAllByRole("row")).toHaveLength(2);
    })

test('devrait enlever les lignes ne correspondant pas au filtre enseignant',
    async () => {
        render(
            <AuthContext.Provider value={{type: TypeId.Personnel, permissions: []}}>
                <BrowserRouter>
                    <ListeDRE/>
                </BrowserRouter>
            </AuthContext.Provider>
        );
        expect(await screen.findAllByRole("row")).toHaveLength(4);
        const filtreEnseignant = screen.getByLabelText(/Par enseignant/i);
        fireEvent.change(filtreEnseignant, {target: {value: "melanie"}});
        expect(screen.getAllByRole("row")).toHaveLength(3);
    })

test('devrait enlever les lignes ne correspondant pas au filtre cours',
    async () => {
        render(
            <AuthContext.Provider value={{type: TypeId.Personnel, permissions: []}}>
                <BrowserRouter>
                    <ListeDRE/>
                </BrowserRouter>
            </AuthContext.Provider>
        );
        expect(await screen.findAllByRole("row")).toHaveLength(4);
        const filtreCours = screen.getByLabelText(/Par cours/i);
        fireEvent.change(filtreCours, {target: {value: "1120"}});
        expect(screen.getAllByRole("row")).toHaveLength(2);
    })

test("devrait enlever puis ajouter une option au filtre statut en cliquant sur une option", () => {
    render(
        <AuthContext.Provider value={{type: TypeId.Personnel, permissions: []}}>
            <BrowserRouter>
                <ListeDRE/>
            </BrowserRouter>
        </AuthContext.Provider>
    );
    const filtreStatut = screen.getByTestId("filtre-statut");
    fireEvent.mouseDown(screen.getAllByRole("button")[0]);
    fireEvent.click(within(screen.getByRole('listbox')).getByText(/Soumise/i));
    expect(filtreStatut).toHaveTextContent("En traitementAcceptéeRejetéeAnnuléePlanifiéeAbsenceComplétéeRetournée");
    fireEvent.click(within(screen.getByRole('listbox')).getByText(/Soumise/i));
    expect(filtreStatut).toHaveTextContent("En traitementAcceptéeRejetéeAnnuléePlanifiéeAbsenceComplétéeRetournéeArchivéeSoumise");
});

test("devrait reinitialiser les champs avec le bouton reinitialiser", () => {
    render(
        <AuthContext.Provider value={{type: TypeId.Personnel, permissions: []}}>
            <BrowserRouter>
                <ListeDRE/>
            </BrowserRouter>
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
        <AuthContext.Provider value={{type: TypeId.Personnel, permissions: []}}>
            <BrowserRouter>
                <ListeDRE/>
            </BrowserRouter>
        </AuthContext.Provider>
    );
    const filtreStatut = screen.getByTestId("filtre-statut");
    const boutonReinitialiser = screen.getByRole("button", {name: "Réinitialiser le filtre"});
    fireEvent.mouseDown(screen.getAllByRole("button")[0]);
    fireEvent.click(within(screen.getByRole('listbox')).getByText(/Soumise/i));
    fireEvent.click(within(screen.getByRole('listbox')).getByText(/Rejetée/i));
    expect(filtreStatut).toHaveTextContent("En traitementAcceptéeAnnuléePlanifiéeAbsenceComplétéeRetournée");
    fireEvent.click(boutonReinitialiser);
    expect(filtreStatut).toHaveTextContent("SoumiseEn traitementAcceptéeRejetéeAnnuléePlanifiéeAbsenceComplétéeRetournée");
});