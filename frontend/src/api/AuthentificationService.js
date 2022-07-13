export const connectionUtilisateur = async (authReq)=> {
    return await fetch(`/api/login`,
        {
            method: 'post',
            body: JSON.stringify(authReq),
            headers: {'Content-Type': 'application/json'}
        })
        .then((response) => {
            if (response.ok) {
                return response.json();
            }
            throw new Error("Un problème est survenu lors de l'authentification.");
        })
}
