import {React, useEffect} from "react";
import '../../styles/StyleEtudiant.css';

const Banniere = (props) => {

    const id = props.id;

    useEffect(() => {
        setTimeout(() => {
            const banniere = document.getElementById(id);
            banniere.style.display = 'none';
        }, 2000);
    });

    return (  
        <div 
        id={id} 
        className={props.className}
        data-testid="banniereErreurTestId"> 
            <div>
                {props.texte}
            </div>
        </div>
    );
}


export default Banniere;