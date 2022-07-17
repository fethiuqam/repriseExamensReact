import {render, screen, fireEvent, within} from "@testing-library/react";
import JugerDRE from "./JugerDRE";

const mockActualiserDRE = jest.fn();

test("devrait retourner deux boutons actives pour decision null au commis", () => {
    render(
        <JugerDRE
            idDRE={1}
            juge="commis"
            decisionCourante={null}
            actualiserDRE={mockActualiserDRE}
        />
    );
    expect(screen.getAllByRole("button")).toHaveLength(2);
    expect(screen.getByRole("button", {name: "Accepter la demande"})).not.toBeDisabled();
    expect(screen.getByRole("button", {name: "Rejeter la demande"})).not.toBeDisabled();
});

test("devrait retourner deux boutons dont un desactive pour decision rejetee commis au commis", () => {
    render(
        <JugerDRE
            idDRE={1}
            juge="commis"
            decisionCourante="REJETEE_COMMIS"
            actualiserDRE={mockActualiserDRE}
        />
    );
    expect(screen.getAllByRole("button")).toHaveLength(2);
    expect(screen.getByRole("button", {name: "Accepter la demande"})).toBeDisabled();
    expect(screen.getByRole("button", {name: "Annuler le rejet"})).not.toBeDisabled();
});

test("devrait retourner deux boutons desactives pour decision acceptee commis au commis", () => {
    render(
        <JugerDRE
            idDRE={1}
            juge="commis"
            decisionCourante="ACCEPTEE_COMMIS"
            actualiserDRE={mockActualiserDRE}
        />
    );
    expect(screen.getAllByRole("button")).toHaveLength(2);
    expect(screen.getByRole("button", {name: "Accepter la demande"})).toBeDisabled();
    expect(screen.getByRole("button", {name: "Rejeter la demande"})).toBeDisabled();
});

test("devrait retourner deux boutons actives pour decision acceptee commis au directeur", () => {
    render(
        <JugerDRE
            idDRE={1}
            juge="directeur"
            decisionCourante="ACCEPTEE_COMMIS"
            actualiserDRE={mockActualiserDRE}
        />
    );
    expect(screen.getAllByRole("button")).toHaveLength(2);
    expect(screen.getByRole("button", {name: "Accepter la demande"})).not.toBeDisabled();
    expect(screen.getByRole("button", {name: "Rejeter la demande"})).not.toBeDisabled();
});

test("devrait retourner deux boutons desactives pour decision null au directeur", () => {
    render(
        <JugerDRE
            idDRE={1}
            juge="directeur"
            decisionCourante={null}
            actualiserDRE={mockActualiserDRE}
        />
    );
    expect(screen.getAllByRole("button")).toHaveLength(2);
    expect(screen.getByRole("button", {name: "Accepter la demande"})).toBeDisabled();
    expect(screen.getByRole("button", {name: "Rejeter la demande"})).toBeDisabled();
});

test("devrait retourner deux boutons desactives pour decision rejetee commis au directeur", () => {
    render(
        <JugerDRE
            idDRE={1}
            juge="directeur"
            decisionCourante="REJETEE_COMMIS"
            actualiserDRE={mockActualiserDRE}
        />
    );
    expect(screen.getAllByRole("button")).toHaveLength(2);
    expect(screen.getByRole("button", {name: "Accepter la demande"})).toBeDisabled();
    expect(screen.getByRole("button", {name: "Rejeter la demande"})).toBeDisabled();
});

test("devrait retourner deux boutons actives pour decision acceptee directeur a enseignant", () => {
    render(
        <JugerDRE
            idDRE={1}
            juge="enseignant"
            decisionCourante="ACCEPTEE_DIRECTEUR"
            actualiserDRE={mockActualiserDRE}
        />
    );
    expect(screen.getAllByRole("button")).toHaveLength(2);
    expect(screen.getByRole("button", {name: "Accepter la demande"})).not.toBeDisabled();
    expect(screen.getByRole("button", {name: "Rejeter la demande"})).not.toBeDisabled();
});

test("devrait retourner une fenetre dialog details apres click sur accepter demande", () => {
    render(
        <JugerDRE
            idDRE={1}
            juge="commis"
            decisionCourante={null}
            actualiserDRE={mockActualiserDRE}
        />
    );
    const boutonAccepter = screen.getByRole("button", {name: "Accepter la demande"});
    expect(boutonAccepter).not.toBeDisabled();
    fireEvent.click(boutonAccepter);
    const dialog = screen.getByRole("dialog");
    expect(dialog).toBeInTheDocument();
    expect(within(dialog).getByText(/Veuillez saisir les détails de l'acceptation./i)).toBeInTheDocument();
    expect(within(dialog).getAllByRole("textbox")).toHaveLength(1);
    expect(within(dialog).getAllByRole("button")).toHaveLength(2);
});

test("devrait retourner une fenetre dialog details apres click sur rejeter demande", () => {
    render(
        <JugerDRE
            idDRE={1}
            juge="commis"
            decisionCourante={null}
            actualiserDRE={mockActualiserDRE}
        />
    );
    const boutonRejeter = screen.getByRole("button", {name: "Rejeter la demande"});
    expect(boutonRejeter).not.toBeDisabled();
    fireEvent.click(boutonRejeter);
    const dialog = screen.getByRole("dialog");
    expect(dialog).toBeInTheDocument();
    expect(within(dialog).getByText(/Veuillez saisir les détails du rejet./i)).toBeInTheDocument();
    expect(within(dialog).getAllByRole("textbox")).toHaveLength(1);
    expect(within(dialog).getAllByRole("button")).toHaveLength(2);
});

test("devrait retourner une fenetre dialog confirmation apres click sur accepter demande", () => {
    render(
        <JugerDRE
            idDRE={1}
            juge="commis"
            decisionCourante={null}
            actualiserDRE={mockActualiserDRE}
        />
    );
    const boutonAccepter = screen.getByRole("button", {name: "Accepter la demande"});
    expect(boutonAccepter).not.toBeDisabled();
    fireEvent.click(boutonAccepter);
    const dialogDetails = screen.getByRole("dialog");
    expect(dialogDetails).toBeInTheDocument();
    fireEvent.click(within(dialogDetails).getByRole("button", {name: "Accepter la demande"}));
    expect(dialogDetails).not.toBeVisible();
    const dialogConfirmation = screen.getByRole("dialog");
    expect(dialogConfirmation).toBeInTheDocument();
    expect(within(dialogConfirmation).getByText(/Êtes vous sûr de vouloir accepter la demande/i)).toBeInTheDocument();
    expect(within(dialogConfirmation).getAllByRole("button")).toHaveLength(2);
});