import { RoleDTO } from "./DTOs/RoleDTO";

export interface LoginCitizenResponse {
    id: number;
    email: string;
    token: string;
    roles: RoleDTO[];
  }