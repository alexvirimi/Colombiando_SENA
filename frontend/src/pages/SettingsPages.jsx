import { useState } from 'react';
import { NavLink, Outlet, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { authService, userService } from '../services/otrosServices';
import { Button, Input, Alert } from '../components/ui/UI';
import styles from './SettingsPages.module.css';

/* El backend responde errores como { error: "..." } */
const mensajeApi = (err, porDefecto) =>
  err.response?.data?.error || err.response?.data?.mensaje || porDefecto;

/* Separa "Nombre Completo" en nombre / apellido (el backend los guarda por separado) */
function separarNombre(completo) {
  const partes = completo.trim().split(/\s+/);
  const corte = partes.length >= 4 ? 2 : 1;
  return {
    nombre: partes.slice(0, corte).join(' '),
    apellido: partes.slice(corte).join(' '),
  };
}

/* ═══════════════════════════════════════════════════════════
   SettingsLayout — menú lateral + contenido (Outlet)
   Rutas: /perfil/cuenta | /perfil/contacto | /perfil/eliminar
   ═══════════════════════════════════════════════════════════ */
export function SettingsLayout() {
  const { logout } = useAuth();
  const navigate = useNavigate();

  const claseItem = ({ isActive }) =>
    `${styles.item} ${isActive ? styles.itemOn : ''}`;

  function salir() {
    logout();
    navigate('/');
  }

  return (
    <section className="padding-block-250">
      <div className={`container ${styles.layout}`}>
        <aside className={styles.sidebar}>
          <nav className={styles.sidebarNav} aria-label="Configuración de la cuenta">
            <NavLink to="/perfil/cuenta" className={claseItem}>Cuenta</NavLink>
            <NavLink to="/perfil/contacto" className={claseItem}>Contáctanos</NavLink>
            <hr className={styles.sep} />
            <NavLink to="/perfil/eliminar" className={claseItem}>Eliminar Cuenta</NavLink>
            <button type="button" className={styles.item} onClick={salir}>
              Log Out
            </button>
          </nav>
        </aside>

        <div className={styles.panel}>
          <Outlet />
        </div>
      </div>
    </section>
  );
}

/* ═══════════════════════════════════════════════════════════
   AccountPage — datos de la cuenta + cambio de contraseña
   ═══════════════════════════════════════════════════════════ */
export function AccountPage() {
  const { usuario, token, login } = useAuth();

  const [form, setForm] = useState({
    nombreCompleto: `${usuario?.nombre ?? ''} ${usuario?.apellido ?? ''}`.trim(),
    correo: usuario?.correo ?? '',
    codigo: '+57',
    telefono: usuario?.telefono ?? '',
    contrasenaActual: '',
    contrasenaNueva: '',
  });
  const [errores, setErrores] = useState({});
  const [cargando, setCargando] = useState(false);
  const [apiError, setApiError] = useState('');
  const [exito, setExito] = useState('');

  const set = k => e => setForm(p => ({ ...p, [k]: e.target.value }));

  function validar() {
    const err = {};
    if (form.nombreCompleto.trim().split(/\s+/).length < 2)
      err.nombreCompleto = 'Escribe tu nombre y apellido.';
    if (!/\S+@\S+\.\S+/.test(form.correo)) err.correo = 'Correo inválido.';
    if (!/^[0-9]{10}$/.test(form.telefono))
      err.telefono = 'El teléfono debe tener 10 dígitos.';

    const quiereCambiarPwd = form.contrasenaActual || form.contrasenaNueva;
    if (quiereCambiarPwd) {
      if (!form.contrasenaActual) err.contrasenaActual = 'Escribe tu contraseña actual.';
      if (form.contrasenaNueva.length < 6)
        err.contrasenaNueva = 'Mínimo 6 caracteres.';
    }
    setErrores(err);
    return !Object.keys(err).length;
  }

  async function handleSubmit(e) {
    e.preventDefault();
    setApiError('');
    setExito('');
    if (!validar()) return;

    const { nombre, apellido } = separarNombre(form.nombreCompleto);
    setCargando(true);
    try {
      /* Primero la contraseña: si la actual es incorrecta no se toca el resto */
      if (form.contrasenaActual) {
        await userService.cambiarPassword(usuario.idUsuario, {
          currentPassword: form.contrasenaActual,
          newPassword: form.contrasenaNueva,
        });
      }
      await userService.actualizar(usuario.idUsuario, {
        nombre,
        apellido,
        telefono: form.telefono,
        correo: form.correo,
      });

      login(
        { ...usuario, nombre, apellido, correo: form.correo, telefono: form.telefono },
        token,
      );
      setForm(p => ({ ...p, contrasenaActual: '', contrasenaNueva: '' }));
      setExito('Cambios guardados correctamente.');
    } catch (err) {
      setApiError(mensajeApi(err, 'No se pudieron guardar los cambios.'));
    } finally {
      setCargando(false);
    }
  }

  return (
    <form onSubmit={handleSubmit} noValidate>
      {apiError && <Alert tipo="error">{apiError}</Alert>}
      {exito && <Alert tipo="success">{exito}</Alert>}

      <Input
        label="Nombre Completo"
        id="nombreCompleto"
        placeholder="Nombre del Usuario"
        value={form.nombreCompleto}
        onChange={set('nombreCompleto')}
        error={errores.nombreCompleto}
      />
      {/* El backend aún no guarda sexo ni fecha de nacimiento del usuario */}
      <Input
        label="Sexo"
        id="sexo"
        placeholder="No disponible por ahora"
        value={usuario?.sexo ?? ''}
        disabled
      />
      <Input
        label="Fecha de Nacimiento"
        id="fechaNacimiento"
        placeholder="No disponible por ahora"
        value={usuario?.fechaNacimiento ?? ''}
        disabled
      />

      <hr className={styles.sep} />

      <Input
        label="Correo"
        id="correo"
        tipo="email"
        placeholder="correo_del_usuario@email.com"
        value={form.correo}
        onChange={set('correo')}
        error={errores.correo}
      />

      <div className={styles.field}>
        <label className={styles.label} htmlFor="telefono">Celular</label>
        <div className={styles.phoneWrap}>
          <select
            className={`${styles.select} ${styles.phoneCode}`}
            value={form.codigo}
            onChange={set('codigo')}
            aria-label="Indicativo"
          >
            <option>+57</option><option>+1</option><option>+34</option>
          </select>
          <input
            id="telefono"
            type="tel"
            className={`${styles.input} ${errores.telefono ? styles.inputErr : ''}`}
            placeholder="3001234567"
            value={form.telefono}
            onChange={set('telefono')}
            maxLength={10}
          />
        </div>
        {errores.telefono && <p className={styles.err}>{errores.telefono}</p>}
      </div>

      <hr className={styles.sep} />

      <Input
        label="Contraseña Actual"
        id="pwdActual"
        tipo="password"
        placeholder="Contraseña actual"
        value={form.contrasenaActual}
        onChange={set('contrasenaActual')}
        error={errores.contrasenaActual}
      />
      <Input
        label="Nueva Contraseña"
        id="pwdNueva"
        tipo="password"
        placeholder="Mínimo 6 caracteres"
        value={form.contrasenaNueva}
        onChange={set('contrasenaNueva')}
        error={errores.contrasenaNueva}
      />

      <Button tipo="submit" ancho disabled={cargando}>
        {cargando ? 'Guardando…' : 'Guardar Cambios'}
      </Button>
    </form>
  );
}

/* ═══════════════════════════════════════════════════════════
   ContactPage — formulario de contacto + líneas de atención
   (igual que ContactForm, aún no existe endpoint para mensajes)
   ═══════════════════════════════════════════════════════════ */
export function ContactPage() {
  const { usuario } = useAuth();
  const inicial = {
    nombre: `${usuario?.nombre ?? ''} ${usuario?.apellido ?? ''}`.trim(),
    correo: usuario?.correo ?? '',
    codigo: '+57',
    telefono: usuario?.telefono ?? '',
    mensaje: '',
  };
  const [form, setForm] = useState(inicial);
  const [enviado, setEnviado] = useState(false);
  const [error, setError] = useState('');

  const set = k => e => setForm(p => ({ ...p, [k]: e.target.value }));

  function handleSubmit(e) {
    e.preventDefault();
    if (!form.nombre || !form.correo || !form.mensaje) {
      setError('Completa nombre, correo y mensaje.');
      return;
    }
    setError('');
    setEnviado(true);
  }

  return (
    <div className={styles.contactWrap}>
      <h1 className={`${styles.title} ${styles.titleCenter}`}>Contáctanos</h1>

      {enviado ? (
        <>
          <Alert tipo="success">✓ Mensaje enviado. Te responderemos pronto.</Alert>
          <Button
            variante="outline"
            onClick={() => { setEnviado(false); setForm({ ...inicial }); }}
          >
            Enviar otro mensaje
          </Button>
        </>
      ) : (
        <form onSubmit={handleSubmit} noValidate>
          {error && <Alert tipo="error">{error}</Alert>}
          <Input
            label="Nombre Completo"
            id="c-nombre"
            placeholder="Jane Doe"
            value={form.nombre}
            onChange={set('nombre')}
          />
          <Input
            label="Correo"
            id="c-correo"
            tipo="email"
            placeholder="example@email.com"
            value={form.correo}
            onChange={set('correo')}
          />
          <div className={styles.field}>
            <label className={styles.label} htmlFor="c-telefono">Celular</label>
            <div className={styles.phoneWrap}>
              <select
                className={`${styles.select} ${styles.phoneCode}`}
                value={form.codigo}
                onChange={set('codigo')}
                aria-label="Indicativo"
              >
                <option>+57</option><option>+1</option><option>+34</option>
              </select>
              <input
                id="c-telefono"
                type="tel"
                className={styles.input}
                placeholder="3001234567"
                value={form.telefono}
                onChange={set('telefono')}
                maxLength={10}
              />
            </div>
          </div>
          <div className={styles.field}>
            <label className={styles.label} htmlFor="c-mensaje">Mensaje</label>
            <textarea
              id="c-mensaje"
              className={styles.textarea}
              rows={4}
              placeholder="Mensaje"
              value={form.mensaje}
              onChange={set('mensaje')}
            />
          </div>
          <Button tipo="submit" ancho>Enviar Mensaje</Button>
        </form>
      )}

      <p className={styles.contactInfo}>
        Líneas de Atención Nacionales: +57 000 0000000, +57 000 0000000, +57 000 0000000
        {' | '}Líneas de Atención Internacionales: 01 8000 000000
        {' | '}servicio@colombiando.com
      </p>
    </div>
  );
}

/* ═══════════════════════════════════════════════════════════
   DeleteAccountPage — eliminación (borrado lógico en backend)
   Verifica correo + contraseña reutilizando authService.login
   ═══════════════════════════════════════════════════════════ */
export function DeleteAccountPage() {
  const { usuario, logout } = useAuth();
  const navigate = useNavigate();

  const [form, setForm] = useState({ correo: '', contrasena: '', acepta: false });
  const [cargando, setCargando] = useState(false);
  const [error, setError] = useState('');

  const set = k => e => setForm(p => ({ ...p, [k]: e.target.value }));

  async function handleSubmit(e) {
    e.preventDefault();
    setError('');

    if (!form.correo || !form.contrasena) {
      setError('Escribe tu correo y tu contraseña actual.');
      return;
    }
    if (form.correo.trim().toLowerCase() !== usuario.correo.toLowerCase()) {
      setError('El correo no coincide con el de tu cuenta.');
      return;
    }
    if (!form.acepta) {
      setError('Debes aceptar la política para eliminar tu cuenta.');
      return;
    }
    if (!window.confirm('¿Seguro que quieres eliminar tu cuenta? Esta acción no se puede deshacer.')) {
      return;
    }

    setCargando(true);
    try {
      await authService.login(form.correo, form.contrasena); // valida credenciales
      await userService.eliminar(usuario.idUsuario);
      logout();
      navigate('/', { replace: true });
    } catch (err) {
      setError(mensajeApi(err, 'No se pudo eliminar la cuenta. Verifica tu contraseña.'));
    } finally {
      setCargando(false);
    }
  }

  return (
    <form onSubmit={handleSubmit} noValidate>
      <h1 className={styles.title}>Eliminar Cuenta</h1>
      <p className={styles.lead}>Nos entristece verte ir.</p>

      {error && <Alert tipo="error">{error}</Alert>}

      <Input
        label="Correo"
        id="del-correo"
        tipo="email"
        placeholder="correo_del_usuario@email.com"
        value={form.correo}
        onChange={set('correo')}
      />
      <Input
        label="Contraseña Actual"
        id="del-pwd"
        tipo="password"
        placeholder="Contraseña actual"
        value={form.contrasena}
        onChange={set('contrasena')}
      />

      <div className={styles.checkRow}>
        <input
          type="checkbox"
          id="del-politica"
          checked={form.acepta}
          onChange={e => setForm(p => ({ ...p, acepta: e.target.checked }))}
        />
        <label htmlFor="del-politica" style={{ fontSize: 'var(--fs-200)' }}>
          Acepto la política de eliminación de cuenta y tratamiento de datos personales
        </label>
      </div>

      <Button tipo="submit" ancho disabled={cargando}>
        {cargando ? 'Eliminando…' : 'Eliminar Cuenta'}
      </Button>
    </form>
  );
}
