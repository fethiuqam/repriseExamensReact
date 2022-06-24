import axios from 'axios';

const ROLE_API_BASE_URL = "http://localhost:8080/api/roles";

class RoleService {

    obtenirRoles(){
        return axios.get("http://localhost:8080/roles");
    }

    creerRole(role){
        return axios.post(ROLE_API_BASE_URL, role);
    }

    obtenirRoleParId(roleId){
        return axios.get(ROLE_API_BASE_URL + '/' + roleId);
    }

    modifierRole(roleId,role){
        return axios.put(ROLE_API_BASE_URL + '/' + roleId, role);
    }

    supprimerRole(roleId){
        return axios.delete(ROLE_API_BASE_URL + '/' + roleId);
    }
}

export default new RoleService()
