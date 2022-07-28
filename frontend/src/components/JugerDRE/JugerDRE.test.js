import {render, screen, fireEvent, within} from "@testing-library/react";
import JugerDRE from "./JugerDRE";

const mockActualiserDRE = jest.fn();

test("devrait retourner deux boutons actives pour decision aucune au commis", () => {
    render(
        <JugerDRE
            idDRE={1}
            juge="commis"
            decisionCourante="AUCUNE"
            actualiserDRE={mockActualiserDRE}
        />
    );
    expect(screen.getAllByRole("button")).toHaveLength(2);
    expect(screen.getByRole("button", {name: "Recommander l'acceptation"})).not.toBeDisabled();
    expect(screen.getByRole("button", {name: "Recommander le rejet"})).not.toBeDisabled();
});

test("devrait retourner deux boutons dont un desactive pour decision rejetee commis au commis", () => {
    render(
        <JugerDRE
            idDRE={1}
            juge="commis"
            decisionCourante="REJET_RECOMMANDE"
            actualiserDRE={mockActualiserDRE}
        />
    );
    expect(screen.getAllByRole("button")).toHaveLength(2);
    expect(screen.getByRole("button", {name: "Recommander l'acceptation"})).toBeDisabled();
    expect(screen.getByRole("button", {name: "Annuler le rejet"})).not.toBeDisabled();
});

test("devrait retourner deux boutons desactives pour decision acceptation recommandee au commis", () => {
    render(
        <JugerDRE
            idDRE={1}
            juge="commis"
            decisionCourante="ACCEPTATION_RECOMMANDEE"
            actualiserDRE={mockActualiserDRE}
        />
    );
    expect(screen.getAllByRole("button")).toHaveLength(2);
    expect(screen.getByRole("button", {name: "Recommander l'acceptation"})).toBeDisabled();
    expect(screen.getByRole("button", {name: "Recommander le rejet"})).toBeDisabled();
});

test("devrait retourner deux boutons actives pour decision aucune au directeur", () => {
    render(
        <JugerDRE
            idDRE={1}
            juge="directeur"
            decisionCourante="AUCUNE"
            actualiserDRE={mockActualiserDRE}
        />
    );
    expect(screen.getAllByRole("button")).toHaveLength(2);
    expect(screen.getByRole("button", {name: "Accepter la demande"})).not.toBeDisabled();
    expect(screen.getByRole("button", {name: "Rejeter la demande"})).not.toBeDisabled();
});

test("devrait retourner deux boutons actives pour decision acceptation recommandee au directeur", () => {
    render(
        <JugerDRE
            idDRE={1}
            juge="directeur"
            decisionCourante="ACCEPTATION_RECOMMANDEE"
            actualiserDRE={mockActualiserDRE}
        />
    );
    expect(screen.getAllByRole("button")).toHaveLength(2);
    expect(screen.getByRole("button", {name: "Accepter la demande"})).not.toBeDisabled();
    expect(screen.getByRole("button", {name: "Rejeter la demande"})).not.toBeDisabled();
});

test("devrait retourner deux boutons actives pour decision rejet recommande au directeur", () => {
    render(
        <JugerDRE
            idDRE={1}
            juge="directeur"
            decisionCourante="REJET_RECOMMANDE"
            actualiserDRE={mockActualiserDRE}
        />
    );
    expect(screen.getAllByRole("button")).toHaveLength(2);
    expect(screen.getByRole("button", {name: "Accepter la demande"})).not.toBeDisabled();
    expect(screen.getByRole("button", {name: "Rejeter la demande"})).not.toBeDisabled();
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
            decisionCourante="AUCUNE"
            actualiserDRE={mockActualiserDRE}
        />
    );
    const boutonAccepter = screen.getByRole("button", {name: "Recommander l'acceptation"});
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
            decisionCourante="AUCUNE"
            actualiserDRE={mockActualiserDRE}
        />
    );
    const boutonRejeter = screen.getByRole("button", {name: "Recommander le rejet"});
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
            decisionCourante="AUCUNE"
            actualiserDRE={mockActualiserDRE}
        />
    );
    const boutonAccepter = screen.getByRole("button", {name: "Recommander l'acceptation"});
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