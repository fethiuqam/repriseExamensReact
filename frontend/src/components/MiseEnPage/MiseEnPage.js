import * as React from 'react';
import Titre from '../Titre/Titre';
import '../../styles/StyleEtudiant.css'

const MiseEnPage = (props) => {
    return (
        <div>
            <div className='corpsPage'>
                <Titre titre={props.titre}/>
                {props.children}
            </div>
        </div>
    );
}

export default MiseEnPage;