import * as React from 'react';
import Entete from '../Entete/Entete';
import PiedPage from '../PiedPage/PiedPage';
import Titre from '../Titre/Titre';
import '../../styles/StyleEtudiant.css'

const MiseEnPage = (props) => {
    return (
        <div className="miseEnPage">
            <Entete />
            <div className='corpsPage'>
                <Titre titre={props.titre}/>
                {props.children}
            </div>
            <PiedPage />
        </div>
    );
}

export default MiseEnPage;