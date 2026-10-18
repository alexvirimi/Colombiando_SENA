import { useState, useEffect } from 'react';
import { useSearchParams, useNavigate } from 'react-router-dom';
import { Button, Alert } from '../components/ui/UI';
import { reviewService } from '../services/otrosServices';
import { useAuth } from '../context/AuthContext';
import styles from './ReviewPage.module.css';
import { bookingService } from '../services/otrosServices';

/**
 * ReviewPage — calificación de tour y guía post-experiencia.
 * Replica tour-review.html.
 * Lee ?idReserva=X de la URL.
 */
export default function ReviewPage() {
  const [searchParams] = useSearchParams();
  const idReserva      = searchParams.get('idReserva');
  const navigate       = useNavigate();
  const { usuario }    = useAuth();
  const [reserva, setReserva] = useState(null);

  const [form, setForm] = useState({
    calificacion: '', comentario: '',
  });
  const [enviado,  setEnviado]  = useState(false);
  const [cargando, setCargando] = useState(false);
  const [errores,  setErrores]  = useState({});

  useEffect(() => {
    if (idReserva) {
      bookingService.obtenerPorId(idReserva).then(({ data }) => setReserva(data));
    }
  }, [idReserva]);

  const set = k => e => setForm(p => ({ ...p, [k]: e.target.value }));

  function validar() {
    const err = {};
    const rating = Number(form.calificacion);
    if (!rating || rating < 1 || rating > 5) err.calificacion = 'Ingresa un valor entre 1 y 5.';
    setErrores(err);
    return !Object.keys(err).length;
  }

  async function handleSubmit(e) {
    e.preventDefault();
    if (!validar()) return;
    setCargando(true);
    try {
      await reviewService.crear({
        userId: usuario.idUsuario,
        idReserva,
        calificacion: form.calificacion,
        comentario: form.comentario,
      });
      setEnviado(true);
    } catch (err) {
      setErrores({ api: err.response?.data?.mensaje || 'No se pudo enviar la reseña.' });
    } finally {
      setCargando(false);
    }
  }

  if (enviado) return (
    <section className="padding-block-250">
      <div className={`container ${styles.exitoWrap}`}>
        <div className={styles.exitoIcon}>⭐</div>
        <h1 className="fs-primary-heading" style={{ color: 'var(--clr-verde)' }}>
          ¡Gracias por tu reseña!
        </h1>
        <p style={{ color: 'var(--clr-gris-600)' }}>
          Tu opinión ayuda a mejorar la experiencia para futuros viajeros.
        </p>
        <div className={styles.exitoBtns}>
          <Button onClick={() => navigate('/perfil')}>Ver mis tours</Button>
          <Button variante="outline" onClick={() => navigate('/tours')}>Explorar más tours</Button>
        </div>
      </div>
    </section>
  );

  return (
    <section className="padding-block-250">
      <div className="container">

        <button className={styles.volver} onClick={() => navigate(-1)}>
          ← Volver
        </button>

        <div className={styles.layout}>

          {/* Formulario de reseña */}
          <div>
            <h1 className={`${styles.titulo} fs-primary-heading`}>
              Califica tu experiencia
            </h1>
            <p className={styles.subtitulo}>
              Tu opinión nos ayuda a seguir mejorando.
            </p>

            <form
              className={styles.form}
              onSubmit={handleSubmit}
              noValidate
            >
              {errores.api && <Alert tipo="error">{errores.api}</Alert>}
              {/* Calificación del tour */}
              <div className={styles.bloque}>
                <h3 className="fs-body-title" style={{ marginBottom: '1rem' }}>
                  Calificación del tour
                </h3>
                <div className={styles.stars}>
                  {[1,2,3,4,5].map(n => (
                    <button
                      key={n}
                      type="button"
                      className={`${styles.star} ${Number(form.calificacion) >= n ? styles.starOn : ''}`}
                      onClick={() => setForm(p => ({ ...p, calificacion: String(n) }))}
                    >
                      {n}
                    </button>
                  ))}
                </div>
                {errores.calificacion && (
                  <p className={styles.err}>{errores.calificacion}</p>
                )}
                <div className={styles.field}>
                  <label className={styles.label}>Describe tu experiencia</label>
                  <textarea
                    className={styles.textarea}
                    rows={4}
                    placeholder="¿Qué fue lo que más te gustó del tour?"
                    value={form.comentario}
                    onChange={set('comentario')}
                  />
                </div>
              </div>

              <Button tipo="submit" ancho disabled={cargando}>
                {cargando ? 'Enviando reseña…' : 'Enviar Reseña'}
              </Button>
            </form>
          </div>

          {/* Info de la reserva */}
          <aside className={styles.infoPanel}>
            <h3 className={styles.infoPanelTitle}>
              Reserva del tour {reserva?.lugarNombre || 'sin nombre'}
            </h3>
            <p className={styles.infoItem}>
              Gracias por completar este tour con Colombiando.
              Tu reseña será visible para otros viajeros.
            </p>
            <div className={styles.infoTip}>
              <span className={styles.infoTipIcon}>💡</span>
              <p>Las reseñas honestas ayudan a mejorar la calidad de nuestros tours.</p>
            </div>
          </aside>

        </div>
      </div>
    </section>
  );
}
