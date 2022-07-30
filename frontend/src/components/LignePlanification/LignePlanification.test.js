import {render, screen} from "@testing-library/react";
import {mockCoursGroupes} from "../../mocks/mockData";
import LignePlanification from "./LignePlanification";

const mockFonction = jest.fn();

const mockCourGroupePlanifie = mockCoursGroupes[0];
const mockCourGroupeNonPlanifie = mockCoursGroupes[1];

test("devrait retourner pour une ligne un element tr et 4 elements td", () => {
    render(
                <table>
                    <tbody>
                    <LignePlanification
                        item={mockCourGroupePlanifie}
                        modifierPlanification={mockFonction}
                        annulerPlanification={mockFonction}
                    />
                    </tbody>
                </table>
    );
    expect(screen.getAllByRole("row")).toHaveLength(1);
    const td = screen.getAllByRole("cell");
    expect(td).toHaveLength(4);
});

test("devrait retourner deux boutons modifier et annuler pour un coursGroupe planifie", () => {
    render(
        <table>
            <tbody>
            <LignePlanification
                item={mockCourGroupePlanifie}
                modifierPlanification={mockFonction}
                annulerPlanification={mockFonction}
            />
            </tbody>
        </table>
    );
    expect(screen.getAllByRole("button")).toHaveLength(2);
    expect(screen.getByRole("button", {name: "Modifier"})).toBeInTheDocument();
    expect(screen.getByRole("button", {name: "Annuler"})).toBeInTheDocument();
});

test("devrait retourner un bouton planifier pour un coursGroupe non planifie", () => {
    render(
        <table>
            <tbody>
            <LignePlanification
                item={mockCourGroupeNonPlanifie}
                modifierPlanification={mockFonction}
                annulerPlanification={mockFonction}
            />
            </tbody>
        </table>
    );
    expect(screen.getAllByRole("button")).toHaveLength(1);
    expect(screen.getByRole("button", {name: "Planifier"})).toBeInTheDocument();
});