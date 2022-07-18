import {format} from "date-fns";
import locale from "date-fns/locale/fr-CA";


const FORMAT_DATE = 'dd MMMM yyyy';
const FORMAT_DATE_HEURE = 'dd MMMM yyyy hh:mm';

export const afficherDate = (date) => {
    return format(new Date(date), FORMAT_DATE, {locale})
}

export const afficherDateHeure = (dateHeure) => {
    return format(new Date(dateHeure), FORMAT_DATE_HEURE, {locale})
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