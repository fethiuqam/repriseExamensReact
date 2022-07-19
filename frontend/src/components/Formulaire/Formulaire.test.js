import '@testing-library/jest-dom'
import {render, screen } from '@testing-library/react';
import TestRenderer from 'react-test-renderer';
import Formulaire from "./Formulaire";
import { BrowserRouter } from 'react-router-dom'; 

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

