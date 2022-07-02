import {render, screen} from '@testing-library/react';
import TestRenderer from 'react-test-renderer';
import Formulaire from "./Formulaire";

test("contient tous les champs requis", () => {
    
    render(<Formulaire />);

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

it('renders correctly', () => {
    const tree = TestRenderer
      .create(<Formulaire/>, {
        createNodeMock: () => document.createElement('textarea')
      })
      .toJSON();
    expect(tree).toMatchSnapshot();
  });
