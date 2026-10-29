import { createContext, useContext, useState } from "react";

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [usuario, setUsuario] = useState(() =>
    JSON.parse(sessionStorage.getItem("col_user") || "null"),
  );
  const [token, setToken] = useState(
    () => sessionStorage.getItem("col_token") || null,
  );

  function login(datosUsuario, jwt) {
    setUsuario(datosUsuario);
    setToken(jwt);
    sessionStorage.setItem("col_user", JSON.stringify(datosUsuario));
    if (jwt) sessionStorage.setItem("col_token", jwt);
    else sessionStorage.removeItem("col_token");
  }

  function logout() {
    setUsuario(null);
    setToken(null);
    sessionStorage.removeItem("col_user");
    sessionStorage.removeItem("col_token");
  }

  return (
    <AuthContext.Provider
      value={{ usuario, token, login, logout, estaAutenticado: !!usuario }}
    >
      {children}
    </AuthContext.Provider>
  );
}

// eslint-disable-next-line react-refresh/only-export-components
export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error("useAuth debe usarse dentro de <AuthProvider>");
  return ctx;
}
