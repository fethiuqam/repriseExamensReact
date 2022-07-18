import {render, screen, fireEvent, within} from "@testing-library/react";
import MessagesDRE from "./MessagesDRE";
import {format} from "date-fns";
import locale from "date-fns/locale/fr-CA";

const mockActualiserDRE = jest.fn();

const FORMAT_DATE_HEURE = 'dd MMMM yyyy hh:mm';

const messagesTestVide = [];

const messagesTestL3 = [
    {
        id: 3,
        typeMessage: "DEMANDE_COMMIS",
        contenu: "Veuillez préciser la période de votre convocation",
        dateHeure: "2022-02-15T10:45:09"
    }
    , {
        id: 4,
        typeMessage: "REPONSE_ETUDIANT",
        contenu: "De lundi à vendredi passé",
        dateHeure: "2022-02-15T12:54:08"
    }
    , {
        id: 5,
        typeMessage: "DEMANDE_COMMIS",
        contenu: "Pouvez-vous fournir une demande officielle prouvant que vous avez été convoqué?",
        dateHeure: "2022-02-15T13:22:05"
    }
];


test("devrait retourner une boite de messages vide avec le bouton envoyer", () => {
    render(
        <MessagesDRE
            messages={messagesTestVide}
            typeUtilisateur="personnel"
            idDRE={1}
            actualiserDRE={mockActualiserDRE}
        />
    );
    expect(screen.getByText(/Aucun Message à afficher./i)).toBeInTheDocument();
    expect(screen.getByRole("button", {name: "Envoyer un message"})).not.toBeDisabled();
});

test("devrait retourner une boite de messages contenant 3 messages et leures dates avec le bouton envoyer", () => {
    render(
        <MessagesDRE
            messages={messagesTestL3}
            typeUtilisateur="personnel"
            idDRE={1}
            actualiserDRE={mockActualiserDRE}
        />
    );
    expect(screen.getAllByRole("listitem")).toHaveLength(3);
    messagesTestL3.forEach(m => {
        expect(screen.getByText(m.contenu)).toBeInTheDocument();
        expect(screen.getByText(format(new Date(m.dateHeure), FORMAT_DATE_HEURE, {locale}))).toBeInTheDocument();
    });
    expect(screen.getByRole("button", {name: "Envoyer un message"})).not.toBeDisabled();
});

test("devrait retourner une boite de dialogue d'envoi de message avec un textbox et 2 boutons apres click sur envoyer message", () => {
    render(
        <MessagesDRE
            messages={messagesTestL3}
            typeUtilisateur="personnel"
            idDRE={1}
            actualiserDRE={mockActualiserDRE}
        />
    );
    const boutonEnvoyer = screen.getByRole("button", {name: "Envoyer un message"});
    expect(boutonEnvoyer).not.toBeDisabled();
    fireEvent.click(boutonEnvoyer);
    const dialog = screen.getByRole("dialog");
    expect(dialog).toBeInTheDocument();
    expect(within(dialog).getByText(/Veuiller saisir votre message./i)).toBeInTheDocument();
    expect(within(dialog).getAllByRole("textbox")).toHaveLength(1);
    expect(within(dialog).getAllByRole("button")).toHaveLength(2);
});

test("le bouton envoyer de la boite de dialogue devrait rester desactive si le textbox est vide ou contient des espaces", () => {
    render(
        <MessagesDRE
            messages={messagesTestL3}
            typeUtilisateur="personnel"
            idDRE={1}
            actualiserDRE={mockActualiserDRE}
        />
    );
    const boutonEnvoyer = screen.getByRole("button", {name: "Envoyer un message"});
    expect(boutonEnvoyer).not.toBeDisabled();
    fireEvent.click(boutonEnvoyer);
    const dialog = screen.getByRole("dialog");
    expect(dialog).toBeInTheDocument();
    const textBox = within(dialog).getByRole("textbox");
    expect(textBox).toBeEmptyDOMElement();
    const boutonEnvoyerDialog = within(dialog).getByRole("button", {name: "Envoyer"});
    expect(boutonEnvoyerDialog).toBeDisabled();
    fireEvent.change(textBox, {target: {value: 'ppp'}});
    expect(textBox).not.toBeEmptyDOMElement();
    expect(boutonEnvoyerDialog).not.toBeDisabled();
    fireEvent.change(textBox, {target: {value: '   '}});
    expect(textBox).not.toBeEmptyDOMElement();
    expect(boutonEnvoyerDialog).toBeDisabled();
});

