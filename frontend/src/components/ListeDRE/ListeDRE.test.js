import {fireEvent, render, screen, within} from '@testing-library/react'
import {mockServer} from '../../mocks/server'
import ListeDRE from './ListeDRE'
import AuthContext from "../../context/AuthProvider";
import user from "@testing-library/user-event";
import { BrowserRouter } from 'react-router-dom';

beforeAll(() => mockServer.listen({
    onUnhandledRequest: 'error'
}))

afterEach(() => mockServer.resetHandlers())

afterAll(() => mockServer.close())

test('devrait rendre une barre de progression avant chargement', () => {
    render(
        <AuthContext.Provider value={{type: "personnel", id: null}}>
            <BrowserRouter>
                <ListeDRE/>
            </BrowserRouter>
        </AuthContext.Provider>
    );
    expect(screen.getByRole("progressbar")).toBeInTheDocument();
})

test('devrait rendre 4 lignes du tableau apres chargement', async () => {
    render(
        <AuthContext.Provider value={{type: "personnel", id: null}}>
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
            <AuthContext.Provider value={{type: "personnel", id: null}}>
                <BrowserRouter>
                    <ListeDRE/>
                </BrowserRouter>
            </AuthContext.Provider>
        );
        expect( await screen.findAllByRole("row")).toHaveLength(4);
        const select = screen.getAllByRole("button")[0];
        fireEvent.mouseDown(select);
        fireEvent.click(within(screen.getByRole('listbox')).getByText(/ACCEPTEE/i));
        user.keyboard('{esc}');
        expect( screen.getAllByRole("row")).toHaveLength(3);
    })

test('devrait enlever les lignes ne correspondant pas au filtre etudiant',
    async () => {
        render(
            <AuthContext.Provider value={{type: "personnel", id: null}}>
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
            <AuthContext.Provider value={{type: "personnel", id: null}}>
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
            <AuthContext.Provider value={{type: "personnel", id: null}}>
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
