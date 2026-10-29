import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { bookingService, bookingEscortService, paymentService } from '../services/otrosServices';
import { Spinner, Alert, EstadoBadge, Button, Modal } from '../components/ui/UI';
import styles from './ProfilePage.module.css';

const pesos = n =>
  new Intl.NumberFormat('es-CO', {
    style: 'currency', currency: 'COP', maximumFractionDigits: 0,
  }).format(n);

/**
 * ProfilePage — perfil del cliente logueado.
 * Replica mi-perfil.html: saludo → tours activos → tours pasados.
 * Al hacer clic en un tour activo se abre un pop up con el detalle.
 * Ruta protegida por PrivateRoute.
 */
export default function ProfilePage() {
  const { usuario }  = useAuth();
  const navigate     = useNavigate();
  const [reservas,   setReservas]   = useState([]);
  const [cargando,   setCargando]   = useState(true);
  const [error,      setError]      = useState('');
  const [cancelando, setCancelando] = useState(null);
  const [mensaje,    setMensaje]    = useState('');
  const [detalle,    setDetalle]    = useState(null);

  useEffect(() => {
    if (!usuario?.idUsuario) return;
    bookingService.listarPorCliente(usuario.idUsuario)
      .then(({ data }) => setReservas(Array.isArray(data) ? data : []))
      .catch(() => setError('No se pudieron cargar tus reservas.'))
      .finally(() => setCargando(false));
  }, [usuario]);

  const activas = reservas.filter(r =>
    ['PENDING', 'CONFIRMED'].includes(r.estado)
  );
  const pasadas = reservas.filter(r =>
    ['COMPLETED', 'CANCELLED'].includes(r.estado)
  );

  async function cancelarReserva(reserva) {
    const reembolso = reserva.estado === 'CONFIRMED';
    const confirmacion = reembolso
      ? '¿Quieres cancelar este tour? La reserva está pagada y pronto se realizará el reembolso.'
      : '¿Quieres cancelar este tour?';

    if (!window.confirm(confirmacion)) return;

    setCancelando(reserva.idReserva);
    setMensaje('');
    try {
      await bookingService.cancelar(reserva.idReserva);
      setReservas(previous =>
        previous.map(item =>
          item.idReserva === reserva.idReserva
            ? { ...item, estado: 'CANCELLED' }
            : item,
        ),
      );
      setMensaje(
        reembolso
          ? 'Reserva cancelada. Pronto se realizará el reembolso del pago.'
          : 'Reserva cancelada correctamente.',
      );
    } catch (cancelError) {
      setError(
        cancelError.response?.data?.mensaje ||
          'No se pudo cancelar la reserva. Inténtalo de nuevo.',
      );
    } finally {
      setCancelando(null);
    }
  }

  return (
    <>
      {/* ── SALUDO ────────────────────────────────────── */}
      <section className="padding-block-250">
        <div className="container">
          <h1 className="fs-title">
            Hola, {usuario?.nombre ?? 'Viajero'}
          </h1>
          <p className={styles.sub}>
            {usuario?.correo}
            {usuario?.telefono && ` • ${usuario.telefono}`}
          </p>
          <div className={styles.configBtn}>
            <Button variante="outline" onClick={() => navigate('/perfil/cuenta')}>
              Configurar mi cuenta
            </Button>
          </div>
        </div>
      </section>

      {/* ── TOURS ACTIVOS ─────────────────────────────── */}
      <section className="padding-block-250">
        <div className="container">
          <h2 className="fs-secondary-heading" style={{ marginBottom: '1.25rem' }}>
            Tours activos
          </h2>

          {cargando && <Spinner />}
          {error    && <Alert tipo="error">{error}</Alert>}
          {mensaje  && <Alert tipo="success">{mensaje}</Alert>}

          {!cargando && !error && (
            activas.length === 0 ? (
              <div className={styles.empty}>
                <p>No tienes tours activos en este momento.</p>
                <Button onClick={() => navigate('/tours')}>
                  ¡Explora nuestros tours!
                </Button>
              </div>
            ) : (
              <div className="three-col-grid">
                {activas.map(r => (
                  <ReservaCard
                    key={r.idReserva}
                    reserva={r}
                    mostrarGuia
                    onDetalle={() => setDetalle(r)}
                    onCancelar={() => cancelarReserva(r)}
                    cancelando={cancelando === r.idReserva}
                    onPagar={() => navigate(`/pagos/nueva?idReserva=${r.idReserva}`)}
                  />
                ))}
              </div>
            )
          )}
        </div>
      </section>

      {/* ── TOURS PASADOS ─────────────────────────────── */}
      {!cargando && pasadas.length > 0 && (
        <section className="padding-block-250" style={{ background: 'var(--clr-gris-200)' }}>
          <div className="container">
            <h2 className="fs-secondary-heading" style={{ marginBottom: '1.25rem' }}>
              Tours pasados
            </h2>
            <div className="three-col-grid">
              {pasadas.map(r => (
                <ReservaCard
                  key={r.idReserva}
                  reserva={r}
                  onCalificar={
                    r.estado === 'COMPLETED'
                      ? () => navigate(`/calificar?idReserva=${r.idReserva}`)
                      : null
                  }
                />
              ))}
            </div>
          </div>
        </section>
      )}

      {/* ── POP UP DETALLE DE LA RESERVA ───────────────── */}
      <Modal
        abierto={!!detalle}
        onCerrar={() => setDetalle(null)}
        titulo={`Tour ${detalle?.lugarNombre || 'sin nombre'}`}
      >
        {detalle && (
          <ReservaDetalle
            reserva={detalle}
            onPagar={() => navigate(`/pagos/nueva?idReserva=${detalle.idReserva}`)}
          />
        )}
      </Modal>
    </>
  );
}

/* ── ReservaDetalle — contenido del pop up ── */
function ReservaDetalle({ reserva, onPagar }) {
  const [acompanantes, setAcompanantes] = useState([]);
  const [pagos,        setPagos]        = useState([]);
  const [cargando,     setCargando]     = useState(true);
  const [aviso,        setAviso]        = useState('');

  useEffect(() => {
    let vigente = true;
    Promise.resolve().then(() => {
      if (!vigente) return;
      setCargando(true);
      setAviso('');
    });
    Promise.allSettled([
      bookingEscortService.listarPorReserva(reserva.idReserva),
      paymentService.listarPorReserva(reserva.idReserva),
    ]).then(([esc, pag]) => {
      if (!vigente) return;
      if (esc.status === 'fulfilled') setAcompanantes(esc.value.data ?? []);
      if (pag.status === 'fulfilled') setPagos(pag.value.data ?? []);
      if (esc.status === 'rejected' || pag.status === 'rejected') {
        setAviso('No se pudo cargar toda la información de la reserva.');
      }
      setCargando(false);
    });
    return () => { vigente = false; };
  }, [reserva.idReserva]);

  const pagado = pagos
    .filter(p => p.estado === 'COMPLETED')
    .reduce((suma, p) => suma + p.monto, 0);
  const pendiente = Math.max(0, reserva.totalReserva - pagado);

  const filas = [
    ['Fecha del tour', reserva.fechaReserva ?? '—'],
    ['Guía', reserva.guia
      ? `${reserva.guia.nombre} ${reserva.guia.apellido}`
      : 'Guía por confirmar'],
    ['Pasajeros', reserva.numeroPasajeros],
    ['Total de la reserva', pesos(reserva.totalReserva)],
    ['Total pagado', pesos(pagado)],
    ['Saldo pendiente', pesos(pendiente)],
  ];

  return (
    <div className={styles.detalle}>
      <div className={styles.detalleRow}>
        <span className={styles.detalleKey}>Estado</span>
        <EstadoBadge estado={reserva.estado} />
      </div>
      {filas.map(([k, v]) => (
        <div key={k} className={styles.detalleRow}>
          <span className={styles.detalleKey}>{k}</span>
          <strong>{v}</strong>
        </div>
      ))}

      {cargando && <Spinner />}
      {aviso && <Alert tipo="error">{aviso}</Alert>}

      {!cargando && (
        <>
          <h3 className={styles.detalleTitulo}>Acompañantes</h3>
          {acompanantes.length === 0 ? (
            <p className={styles.detalleVacio}>Esta reserva no tiene acompañantes registrados.</p>
          ) : (
            <ul className={styles.detalleLista} role="list">
              {acompanantes.map(a => (
                <li key={a.idAcompanante} className={styles.detalleItem}>
                  <strong>{a.nombre} {a.apellido}</strong>
                  <span>
                    {a.tipoDocumento} {a.numeroDocumento}
                    {a.fechaNacimiento && ` · Nac. ${a.fechaNacimiento}`}
                  </span>
                </li>
              ))}
            </ul>
          )}

          <h3 className={styles.detalleTitulo}>Pagos</h3>
          {pagos.length === 0 ? (
            <p className={styles.detalleVacio}>Aún no has registrado pagos.</p>
          ) : (
            <ul className={styles.detalleLista} role="list">
              {pagos.map(p => (
                <li key={p.idPago} className={styles.detalleItem}>
                  <strong>{pesos(p.monto)}</strong>
                  <span>{p.metodoPago}</span>
                  <EstadoBadge estado={p.estado} />
                </li>
              ))}
            </ul>
          )}
        </>
      )}

      {pendiente > 0 && reserva.estado === 'PENDING' && (
        <Button ancho onClick={onPagar}>Registrar pago</Button>
      )}
    </div>
  );
}

/* ── ReservaCard — tarjeta individual dentro del perfil ── */
function ReservaCard({
  reserva,
  mostrarGuia = false,
  onDetalle,
  onPagar,
  onCancelar,
  cancelando,
  onCalificar,
}) {
  function teclado(e) {
    if (e.target !== e.currentTarget) return;
    if (e.key === 'Enter' || e.key === ' ') {
      e.preventDefault();
      onDetalle();
    }
  }

  return (
    <div
      className={`${styles.card} ${onDetalle ? styles.cardClickable : ''}`}
      onClick={onDetalle}
      onKeyDown={onDetalle ? teclado : undefined}
      role={onDetalle ? 'button' : undefined}
      tabIndex={onDetalle ? 0 : undefined}
      aria-label={onDetalle ? `Ver detalle del tour ${reserva.lugarNombre || 'sin nombre'}` : undefined}
    >
      {onCancelar && (
        <button
          type="button"
          className={styles.cancelButton}
          onClick={e => { e.stopPropagation(); onCancelar(); }}
          disabled={cancelando}
          aria-label={`Cancelar reserva ${reserva.idReserva}`}
          title="Cancelar reserva"
        >
          {cancelando ? '…' : '×'}
        </button>
      )}
      {/* Cabecera */}
      <div className={styles.cardHeader}>
        <figure className={styles.cardFig}>
          Tour {reserva.lugarNombre || 'sin nombre'}
        </figure>
      </div>

      {/* Cuerpo */}
      <div className={styles.cardBody}>
        <div className={styles.cardTop}>
          <p className={styles.cardTour}>
            Tour {reserva.lugarNombre || 'sin nombre'}
          </p>
          <EstadoBadge estado={reserva.estado} />
        </div>

        <p className={styles.cardMeta}>
          {reserva.fechaReserva}
          {mostrarGuia && reserva.guia && (
            <> · Guía: {reserva.guia.nombre} {reserva.guia.apellido}</>
          )}
          {' · '}{reserva.numeroPasajeros} pax
        </p>

        {reserva.totalReserva && (
          <p className={styles.cardTotal}>{pesos(reserva.totalReserva)}</p>
        )}

        {/* Acciones (no deben abrir el pop up) */}
        <div className={styles.cardActions} onClick={e => e.stopPropagation()}>
          {onPagar && (
            <Button variante="primary" onClick={onPagar} className={styles.btnSm}>
              Registrar pago
            </Button>
          )}
          {reserva.estado === 'CONFIRMED' && onCancelar && (
            <p className={styles.refundNote}>
              Al cancelar, pronto se realizará el reembolso.
            </p>
          )}
          {onCalificar && (
            <Button variante="outline" onClick={onCalificar} className={styles.btnSm}>
              ✦ Calificar
            </Button>
          )}
        </div>
      </div>
    </div>
  );
}
