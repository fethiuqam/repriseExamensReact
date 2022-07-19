import '@testing-library/jest-dom'
import {render} from '@testing-library/react';
import HistoriqueEtudiant, {compterValides, diffJours, estIgnoree} from "./HistoriqueEtudiant";
import {APIgetDemandesResponse} from "../../mocks/mockData.js";


describe('1. Composante --> HistoriqueEtudiant', () => {
   test('rendre sans erreur', async () => {
      render(<HistoriqueEtudiant />);
   });
});

describe('2. Fonction utilitare --> diffJours()', () => {
    test('retourner 30 jours', async () => {
        expect(diffJours(new Date(2022, 6, 0, 10, 33, 30, 0), new Date(2022, 6, 30, 10, 33, 30, 0))).toEqual(30);
    });
});

describe('3. Fonction utilitare --> diffJours()', () => {
    test('retourner 2 jours', async () => {
        expect(diffJours(new Date(2010, 7, 3, 10, 33, 30, 0), new Date(2010, 7, 1, 10, 33, 30, 0))).toEqual(2);
    });
});

describe('4. Fonction utilitare --> diffJours()', () => {
    test('devrait retourner 1096 jours (≈ 3 ans)', async () => {
        expect(diffJours(new Date(2022, 7, 1, 10, 33, 30, 0), new Date(2019, 7, 1, 10, 33, 30, 0))).toEqual(1096);
    });
});

describe('5. Fonction utilitare --> diffJours()', () => {
    test('retourner 2191 jours (≈ 6 ans)', async () => {
        expect(diffJours(new Date(2022, 7, 1, 10, 33, 30, 0), new Date(2016, 7, 1, 10, 33, 30, 0))).toEqual(2191);
    });
});

describe('6. Fonction utilitare --> estIgnoree()', () => {
    test('retourner un tableau de booleans avec 5 valeurs vraies et 2 fausses', async () => {
        expect(estIgnoree(APIgetDemandesResponse)).toEqual([true, false, true, true, true, false, true]);
    });
});

describe('7. Fonction utilitare --> compterValides()', () => {
    test('retourner 2 (demandes dans les trois dernières annnées)', async () => {
        expect(compterValides(APIgetDemandesResponse)).toEqual(2);
    });
});
