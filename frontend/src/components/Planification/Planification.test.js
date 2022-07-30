import {mockServer} from "../../mocks/server";
import {render, screen} from "@testing-library/react";
import Planification from "./Planification";

beforeAll(() => mockServer.listen({
    onUnhandledRequest: 'error'
}))

afterEach(() => mockServer.resetHandlers())

afterAll(() => mockServer.close())

test('devrait rendre une barre de progression avant chargement', () => {
    render(<Planification/>);
    expect(screen.getByRole("progressbar")).toBeInTheDocument();
})

test('devrait rendre 3 lignes du tableau apres chargement', async () => {
    render(<Planification/>);
    expect(await screen.findAllByRole("row")).toHaveLength(3);
})