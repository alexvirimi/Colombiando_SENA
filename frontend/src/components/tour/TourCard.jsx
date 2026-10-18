import { useNavigate } from 'react-router-dom';
import { Badge } from '../ui/UI';
import styles from './TourCard.module.css';

const pesos = n => new Intl.NumberFormat('es-CO', { style: 'currency', currency: 'COP', maximumFractionDigits: 0 }).format(n);
const imagenFallback = 'https://commons.wikimedia.org/wiki/Special:FilePath/Valle_de_Cocora,_Colombia_04.jpg?width=1200';

export default function TourCard({ tour }) {
  const navigate = useNavigate();
  const instancia = tour.instancias?.[0];
  return (
    <article className={styles.card}
      onClick={() => navigate(`/tours/${tour.idTour}`)}
      role="button" tabIndex={0}
      onKeyDown={e => e.key === 'Enter' && navigate(`/tours/${tour.idTour}`)}
      aria-label={`Ver detalles del tour ${tour.nombre}`}>

      <div className={styles.imagen}>
        {tour.imagenUrl
          ? (
            <img
              src={tour.imagenUrl}
              alt={tour.nombre}
              loading="lazy"
              onError={(event) => {
                event.currentTarget.onerror = null;
                event.currentTarget.src = imagenFallback;
              }}
            />
          )
          : <span className={styles.placeholder}>{tour.nombre}</span>}
      </div>

      <div className="tour-tags">
        <Badge color="blue">{tour.instancias?.length || 0} salidas</Badge>
        <Badge color="green">{tour.guias?.length || 0} guías</Badge>
      </div>

      <div className={styles.info}>
        <p className={styles.nombre}>{tour.nombre}</p>
        <p className={styles.precio}>{pesos(instancia?.precio || 0)}<span className={styles.pax}> / persona</span></p>
        {tour.descripcion && (
          <p className={styles.resumen}>
            {tour.descripcion.length > 90 ? tour.descripcion.slice(0, 90) + '…' : tour.descripcion}
          </p>
        )}
      </div>
    </article>
  );
}
