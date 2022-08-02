import {render, screen} from "@testing-library/react";
import TablePlanification from "./TablePlanification";
import {mockCoursGroupes} from "../../mocks/mockData";

const mockFonction = jest.fn();

const colonnes = [
    {name: 'Cours', prop: 'cours.sigle', active: false},
    {name: 'Session', prop: 'session', active: false},
    {name: 'Enseignant', prop: 'enseignant.prenom', active: false},
];

test('devrait rendre 3 lignes du tableau', async () => {
    render(<TablePlanification
        items={mockCoursGroupes}
        colonnes={colonnes}
        modifierPlanification={mockFonction}
        annulerPlanification={mockFonction}
    />);
    expect(await screen.findAllByRole("row")).toHaveLength(3);
})