import '@testing-library/jest-dom'
import {render, screen, waitFor, fireEvent} from '@testing-library/react';
import userEvent from '@testing-library/user-event'
import TestRenderer from 'react-test-renderer';
import Formulaire from "./Formulaire";
import { BrowserRouter } from 'react-router-dom'; 
import { act } from 'react-dom/test-utils';

// test pour vérifier si le formulaire comprend toute l'information requise

test("contient tous les champs requis", () => {
    
    render(
      <BrowserRouter>
        <Formulaire />
      </BrowserRouter>
    );

    const listeChamps = [
        "Prénom", 
        "Nom", 
        "Code permanent",
        "Sigle du cours",
        "Groupe",
        "Enseignant",
        "Début de l'absence", 
        "Fin de l'absence",
        "Motif",
        "Explications détaillées",
        "Justificatifs"
    ];

    listeChamps.forEach( (elem) => {
        expect(screen.getAllByText(elem)).toBeTruthy();
    })

});


/*
Test pour vérifier qu'un échec du post affiche bien la bannière d'erreur
*/
const unmockedFetch = global.fetch

beforeAll(() => {
  global.fetch = jest.fn().mockRejectedValue(new Error("Erreur de post"));
})

afterAll(() => {
  global.fetch = unmockedFetch
})

test('Bannière affichée quand le post échoue', async () => {

    render(
      <BrowserRouter>
        <Formulaire />
      </BrowserRouter>
    );
  
    const sigle = screen.getByTestId("sigleCoursTestId");
    const groupe = screen.getByRole("textbox", {name: /Groupe/i});
    const enseignant = screen.getByRole("textbox", {name: /Enseignant/i});
    const dateDebut = screen.getByLabelText("Début de l'absence");
    const dateFin = screen.getByLabelText("Fin de l'absence");
    const motif = screen.getByTestId("motifTestId");
    const explications = screen.getByRole("textbox", {name: /Explications détaillées/i});

    const boutonSoumettre = screen.getByRole("button", {name: /Soumettre/i});

    userEvent.click(sigle);
    await waitFor(() => screen.findByText(/INM5151/i));
    fireEvent.change(screen.getByText(/INM5151/i), { target: { value: 1 } })
    expect(screen.getByRole('option', { name: 'INM5151' }).selected).toBe(true)

    userEvent.type(groupe, '20');
    expect(groupe.value).toBe('20');
    
    userEvent.type(enseignant, 'Camille Coti');
    userEvent.type(dateDebut, '2022-07-07');
    userEvent.type(dateFin, '2022-07-07');
    expect(dateFin.value).toBe('2022-07-07');

    userEvent.click(motif);
    await waitFor(() => screen.findByText(/Hospitalisation/i));
    fireEvent.change(screen.getByText(/Hospitalisation/i), { target: { value: 1 } })
    expect(screen.getByRole('option', { name: 'Hospitalisation' }).selected).toBe(true)

    userEvent.type(explications, 'Voilà.');
  
    // eslint-disable-next-line testing-library/no-unnecessary-act
    await act(async () => {
      userEvent.click(boutonSoumettre)
    })

    await screen.findByText("ERREUR : Votre demande n'a pas pu être envoyée.")
   
}, 150000);



/* test pour vérifier que le UI de création de DRE n'a pas changé
 Pour mettre à jour le snapshot, ajouter "-u" au script "test:ci", directement dans package.json. 
 ATTENTION : N'oubliez pas d'enlever le "-u" une fois le snapshot mis à jour, sinon il va juste se 
 mettre à jour chaque fois que les tests roulent, sans jamais comparer l'état actuel au snapshot enregistré. 
 */

test('Formulaire affiché correctement', () => {

  jest.useFakeTimers().setSystemTime(new Date('2020-01-01'));

  const FormulaireAvecRoute = () => {
    return(
      <BrowserRouter>
        <Formulaire />
      </BrowserRouter>
    );
  }

  const tree = TestRenderer
      .create(<FormulaireAvecRoute/>, {
        createNodeMock: () => document.createElement('textarea')
      })
      .toJSON();
    expect(tree).toMatchSnapshot();
});

