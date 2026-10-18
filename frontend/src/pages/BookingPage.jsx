import { useMemo, useState } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { useTourDetalle } from '../hooks/useTours';
import { useBooking } from '../hooks/useBooking';
import { useAuth } from '../context/AuthContext';
import { BookingForm, BookingSummary, ConfirmationBanner } from '../components/reserva/Forms';
import { Spinner, Alert } from '../components/ui/UI';
import styles from './BookingPage.module.css';

/**
 * BookingPage — flujo de nueva reserva.
 * Lee ?idTour=X de la URL.
 * Layout: formulario (izquierda) + resumen sticky (derecha).
 * Tras el éxito muestra ConfirmationBanner.
 */
export default function BookingPage() {
  const [searchParams]      = useSearchParams();
  const idTour              = searchParams.get('idTour');
  const navigate            = useNavigate();
  const { usuario }         = useAuth();
  const { tour, cargando: cargandoTour, error: errorTour } = useTourDetalle(idTour);
  const { crearReserva, reserva, cargando, error }         = useBooking();
  const tourInstance = useMemo(
    () => tour?.instancias?.find(instance => instance.idTour === idTour) || tour,
    [tour, idTour],
  );

  /* Estado local del formulario para sincronizar con BookingSummary */
  const [paxActual,   setPaxActual]   = useState(1);
  async function handleSubmit(datos) {
    setPaxActual(datos.numeroPasajeros);
    await crearReserva({
      userId: usuario.idUsuario,
      idTour,
      numeroPasajeros: datos.numeroPasajeros,
      acompanantes: datos.acompanantes,
    });
  }

  /* Confirmación */
  if (reserva) {
    return (
      <section className="padding-block-250">
        <div className="container">
          <ConfirmationBanner
            reserva={reserva}
            tour={tourInstance}
            cliente={usuario}
          />
        </div>
      </section>
    );
  }

  if (cargandoTour) return <div className="padding-block-250 container"><Spinner /></div>;
  if (errorTour)    return <div className="padding-block-250 container"><Alert tipo="error">{errorTour}</Alert></div>;

  return (
    <section className="padding-block-250">
      <div className="container">

        {/* Migas */}
        <button className={styles.volver} onClick={() => navigate(-1)}>
          ← Volver al tour
        </button>

        <h1 className={`${styles.titulo} fs-primary-heading`}>Nueva reserva</h1>

        <div className={styles.layout}>
          {/* Formulario */}
          <div>
            <BookingForm
              tour={tourInstance}
              usuario={usuario}
              onChange={(datos) => setPaxActual(datos.numeroPasajeros)}
              onSubmit={handleSubmit}
              cargando={cargando}
              error={error}
            />
          </div>

          {/* Resumen sticky */}
          <BookingSummary
            tour={tourInstance}
            pax={paxActual}
          />
        </div>

      </div>
    </section>
  );
}
