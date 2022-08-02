import {format} from "date-fns";
import locale from "date-fns/locale/fr-CA";
import {STATUTS_ENSEIGNANT, STATUTS_ETUDIANT, STATUTS_PERSONNEL} from "./const";


const FORMAT_DATE = 'dd MMMM yyyy';
const FORMAT_DATE_HEURE = 'dd MMMM yyyy hh:mm';
const FORMAT_DATE_HEURE_ISO = "yyyy-MM-dd'T'HH:mm";

export const afficherDate = (date) => {
    return format(new Date(date), FORMAT_DATE, {locale})
}

export const afficherDateHeure = (dateHeure) => {
    return format(new Date(dateHeure), FORMAT_DATE_HEURE, {locale})
}

export const afficherDateHeureIso = (dateHeure) => {
    return format(dateHeure, FORMAT_DATE_HEURE_ISO, {locale})
}

export const comparator = (prop, desc = true) => (a, b) => {
    const order = desc ? -1 : 1;
    if (a[prop] < b[prop]) {
        return -1 * order;
    }
    if (a[prop] > b[prop]) {
        return 1 * order;
    }
    return 0;
};

export const estArchivable = (dre) => {
    return ["ANNULEE", "REJETEE", "ABSENCE", "COMPLETEE", "RETOURNEE"].includes(dre.statutCourant);
}

export const statutsUtilisteurs = (type) => {
    switch (type) {
        case "personnel":
            return STATUTS_PERSONNEL;
        case "enseignant":
            return STATUTS_ENSEIGNANT;
        case "etudiant":
            return STATUTS_ETUDIANT;
        default:
            return [];
    }
}

export const afficherSession = (coursGroupe) => {
    return coursGroupe.session.substring(0, 3) + "-" + coursGroupe.annee.substring(2);
}

export const afficherCoursGroupe = (coursGroupe) => {
    return coursGroupe.cours.sigle + "-" + coursGroupe.groupe;
}