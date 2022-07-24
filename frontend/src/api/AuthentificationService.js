export const connectionUtilisateur = async (authReq)=> {
    return await fetch(`/api/login`,
        {
            method: 'post',
            body: JSON.stringify(authReq),
            headers: {'Content-Type': 'application/json'},
            credentials: 'include'
        })
        .then((response) => {
            if (response.ok) {
                return response.json();
            }
            throw new Error("Un problème est survenu lors de l'authentification.");
        })
}

export const deconnecterUtilisateur = async ()=> {
    return await fetch(`/api/logout`,
        {
            method: 'post',
            credentials: 'include'
        })
        .then((response) => {
            if (!response.ok) throw new Error("Une erreur s'est produite lors de la déconnexion");
        })
}
