import { RoleDTO } from "./RoleDTO";

export interface UserDTO{
    id: number,
    email: string,
    roles: RoleDTO[]
}