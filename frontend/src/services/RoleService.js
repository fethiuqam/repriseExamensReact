import axios from 'axios'

const ROLES_REST_API_URL = 'http://localhost:8080/api/roles';

class RoleService {

    // lISTER LES ROLES
    getRoles(){
        return axios.get(ROLES_REST_API_URL);
    }

    // SUPPRIMER LES ROLES
    deleteRoLE(roleId) {
        return axios.delete(ROLES_REST_API_URL + '/' + roleId);
    }

    // CREER UN ROLES
    addRole(role) {
        return axios.post(""+ROLES_REST_API_URL, role);
    }

    // MODIFIER ROLE
    editRole(role) {
        return axios.put(ROLES_REST_API_URL + '/' + role.id, role);
    }
}

export default new RoleService();

