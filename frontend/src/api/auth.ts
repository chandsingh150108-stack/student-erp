import client from './client';

export interface LoginRequest {
  username: string;
  password: string;
}

export interface RegisterRequest {
  username: string;
  email: string;
  password: string;
  role: string;
}

export interface AuthResponse {
  token: string;
  username: string;
  role: string;
}

export const authApi = {
  login: (data: LoginRequest) => client.post<AuthResponse>('/api/auth/login', data),
  register: (data: RegisterRequest) => client.post<AuthResponse>('/api/auth/register', data),
};
