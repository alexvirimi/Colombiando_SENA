import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { bookingService } from '../services/otrosServices';
import { Spinner, Alert, EstadoBadge, Button } from '../components/ui/UI';
import styles from './ProfilePage.module.css';

/**
 * ProfilePage — perfil del cliente logueado.
 * Replica mi-perfil.html: saludo → tours activos → tours pasados.
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
    </>
  );
}

/* ── ReservaCard — tarjeta individual dentro del perfil ── */
function ReservaCard({
  reserva,
  mostrarGuia = false,
  onPagar,
  onCancelar,
  cancelando,
  onCalificar,
}) {
  const pesos = n =>
    new Intl.NumberFormat('es-CO', {
      style: 'currency', currency: 'COP', maximumFractionDigits: 0,
    }).format(n);

  return (
    <div className={styles.card}>
      {onCancelar && (
        <button
          type="button"
          className={styles.cancelButton}
          onClick={onCancelar}
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

        {/* Acciones */}
        <div className={styles.cardActions}>
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
