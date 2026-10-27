import axios from 'axios';

const api = axios.create({
  baseURL: '/api',
  headers: { 'Content-Type': 'application/json' },
  timeout: 15000,
});

// Inyectar token JWT en cada request
api.interceptors.request.use(config => {
  const token = sessionStorage.getItem('col_token');
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});

// Redirigir al login si el backend devuelve 401.
// Se excluye /auth/login: allí un 401 solo significa "credenciales incorrectas"
// (login, y también la verificación de contraseña al eliminar la cuenta).
api.interceptors.response.use(
  res => res,
  err => {
    const esLogin = err.config?.url?.includes('/auth/login');
    if (err.response?.status === 401 && !esLogin) {
      sessionStorage.removeItem('col_user');
      sessionStorage.removeItem('col_token');
      window.location.href = '/login';
    }
    return Promise.reject(err);
  }
);

export default api;
