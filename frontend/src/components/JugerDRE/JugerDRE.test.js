import {fireEvent, render, screen, within} from "@testing-library/react";
import JugerDRE from "./JugerDRE";
import AuthContext from "../../context/AuthProvider";
import {BrowserRouter} from "react-router-dom";
import {DecisionId, Permission, TypeId} from "../../utils/const";

const mockActualiserDRE = jest.fn();

test("devrait retourner trois boutons actives pour decision aucune au commis", () => {
    render(
        <AuthContext.Provider value={{type: TypeId.Personnel,
            permissions: [Permission.JugerCommis, Permission.RetournerDemande]}}>
            <BrowserRouter>
                <JugerDRE
                    idDRE={1}
                    decisionCourante="AUCUNE"
                    actualiserDRE={mockActualiserDRE}
                />
            </BrowserRouter>
        </AuthContext.Provider>
    );
    expect(screen.getAllByRole("button")).toHaveLength(3);
    expect(screen.getByRole("button", {name: "Recommander l'acceptation"})).not.toBeDisabled();
    expect(screen.getByRole("button", {name: "Recommander le rejet"})).not.toBeDisabled();
    expect(screen.getByRole("button", {name: "Retourner la demande"})).not.toBeDisabled();
});

test("devrait retourner trois boutons desactives pour decision acceptation recommandee au commis", () => {
    render(
        <AuthContext.Provider value={{type: TypeId.Personnel,
            permissions: [Permission.JugerCommis, Permission.RetournerDemande]}}>
            <BrowserRouter>
                <JugerDRE
                    idDRE={1}
                    decisionCourante={DecisionId.AcceptationRecommandee}
                    actualiserDRE={mockActualiserDRE}
                />
            </BrowserRouter>
        </AuthContext.Provider>
    );
    expect(screen.getAllByRole("button")).toHaveLength(3);
    expect(screen.getByRole("button", {name: "Recommander l'acceptation"})).toBeDisabled();
    expect(screen.getByRole("button", {name: "Recommander le rejet"})).toBeDisabled();
    expect(screen.getByRole("button", {name: "Retourner la demande"})).toBeDisabled();
});

test("devrait retourner deux boutons actives pour decision aucune au directeur", () => {
    render(
        <AuthContext.Provider value={{type: TypeId.Personnel, permissions: [Permission.JugerDirecteur]}}>
            <BrowserRouter>
                <JugerDRE
                    idDRE={1}
                    decisionCourante={DecisionId.Aucune}
                    actualiserDRE={mockActualiserDRE}
                />
            </BrowserRouter>
        </AuthContext.Provider>
    );
    expect(screen.getAllByRole("button")).toHaveLength(2);
    expect(screen.getByRole("button", {name: "Accepter la demande"})).not.toBeDisabled();
    expect(screen.getByRole("button", {name: "Rejeter la demande"})).not.toBeDisabled();
});

test("devrait retourner deux boutons actives pour decision acceptation recommandee au directeur", () => {
    render(
        <AuthContext.Provider value={{type: TypeId.Personnel, permissions: [Permission.JugerDirecteur]}}>
            <BrowserRouter>
                <JugerDRE
                    idDRE={1}
                    decisionCourante={DecisionId.AcceptationRecommandee}
                    actualiserDRE={mockActualiserDRE}
                />
            </BrowserRouter>
        </AuthContext.Provider>
    );
    expect(screen.getAllByRole("button")).toHaveLength(2);
    expect(screen.getByRole("button", {name: "Accepter la demande"})).not.toBeDisabled();
    expect(screen.getByRole("button", {name: "Rejeter la demande"})).not.toBeDisabled();
});

test("devrait retourner deux boutons actives pour decision rejet recommande au directeur", () => {
    render(
        <AuthContext.Provider value={{type: TypeId.Personnel, permissions: [Permission.JugerDirecteur]}}>
            <BrowserRouter>
                <JugerDRE
                    idDRE={1}
                    decisionCourante={DecisionId.RejetRecommande}
                    actualiserDRE={mockActualiserDRE}
                />
            </BrowserRouter>
        </AuthContext.Provider>
    );
    expect(screen.getAllByRole("button")).toHaveLength(2);
    expect(screen.getByRole("button", {name: "Accepter la demande"})).not.toBeDisabled();
    expect(screen.getByRole("button", {name: "Rejeter la demande"})).not.toBeDisabled();
});

test("devrait retourner deux boutons actives pour decision acceptee directeur a enseignant", () => {
    render(
        <AuthContext.Provider value={{type: TypeId.Enseignant, permissions:[]}}>
            <BrowserRouter>
                <JugerDRE
                    idDRE={1}
                    decisionCourante={DecisionId.AccepteeDirecteur}
                    actualiserDRE={mockActualiserDRE}
                />
            </BrowserRouter>
        </AuthContext.Provider>
    );
    expect(screen.getAllByRole("button")).toHaveLength(2);
    expect(screen.getByRole("button", {name: "Accepter la demande"})).not.toBeDisabled();
    expect(screen.getByRole("button", {name: "Rejeter la demande"})).not.toBeDisabled();
});

test("devrait retourner une fenetre dialog details apres click sur accepter demande", () => {
    render(
        <AuthContext.Provider value={{type: TypeId.Personnel,
            permissions: [Permission.JugerCommis, Permission.RetournerDemande]}}>
            <BrowserRouter>
                <JugerDRE
                    idDRE={1}
                    decisionCourante={DecisionId.Aucune}
                    actualiserDRE={mockActualiserDRE}
                />
            </BrowserRouter>
        </AuthContext.Provider>
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
        <AuthContext.Provider value={{type: TypeId.Personnel,
            permissions: [Permission.JugerCommis, Permission.RetournerDemande]}}>
            <BrowserRouter>
                <JugerDRE
                    idDRE={1}
                    decisionCourante={DecisionId.Aucune}
                    actualiserDRE={mockActualiserDRE}
                />
            </BrowserRouter>
        </AuthContext.Provider>
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
        <AuthContext.Provider value={{type: TypeId.Personnel,
            permissions: [Permission.JugerCommis, Permission.RetournerDemande]}}>
            <BrowserRouter>
                <JugerDRE
                    idDRE={1}
                    decisionCourante={DecisionId.Aucune}
                    actualiserDRE={mockActualiserDRE}
                />
            </BrowserRouter>
        </AuthContext.Provider>
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