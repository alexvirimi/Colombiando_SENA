import { useState, useMemo } from 'react';
import { useTours } from '../hooks/useTours';
import TourCard from '../components/tour/TourCard';
import { TourCarousel, TourFilters } from '../components/tour/TourExtras';
import { ContactForm } from '../components/reserva/Forms';
import { Spinner, Alert } from '../components/ui/UI';
import { colombiaRegionData } from '../services/tourService';
import styles from './ToursPage.module.css';

/**
 * ToursPage — catálogo público de tours.
 * Replica servicios.html: carousel + filtros de región + búsqueda + grid de cards.
 */
export default function ToursPage() {
  const { tours, cargando, error } = useTours();
  const [region,   setRegion]   = useState('Todos');
  const [busqueda, setBusqueda] = useState('');
  const regionData = region === 'Todos'
    ? colombiaRegionData.colombia
    : colombiaRegionData.regions.find(item => item.name === region)
      || colombiaRegionData.colombia;

  /* Filtrado memoizado para evitar recálculos en cada render */
  const toursFiltrados = useMemo(() => {
    const q = busqueda.toLowerCase();
    return tours.filter(t => {
      const municipio = t.municipality?.trim().toLocaleLowerCase();
      const pasaRegion = region === 'Todos'
        || municipio === region.toLocaleLowerCase();
      const pasaBusqueda = !q
        || t.nombre.toLowerCase().includes(q)
        || t.descripcion?.toLowerCase().includes(q)
        || t.municipality?.toLowerCase().includes(q);
      return pasaRegion && pasaBusqueda;
    });
  }, [tours, region, busqueda]);

  return (
    <>
      {/* ── HERO carousel ──────────────────────────────── */}
      {!cargando && !error && tours.length > 0 && (
        <section style={{ marginBottom: 0 }}>
          <TourCarousel tours={tours.slice(0, 4)} />
        </section>
      )}

      {/* ── CATÁLOGO ───────────────────────────────────── */}
      <section className="padding-block-250">
        <div className="container">

          {/* Filtros */}
          <TourFilters
            regionActiva={region}
            busqueda={busqueda}
            onRegion={setRegion}
            onBusqueda={setBusqueda}
          />

          {/* Imagen representativa de región */}
          <div
            className={styles.regionImg}
            style={regionData?.imageUrl
              ? { backgroundImage: `url(${regionData.imageUrl})` }
              : undefined}
            role="img"
            aria-label={regionData?.description || `Región ${region}`}
          >
            <p className={styles.regionLabel}>
              {region === 'Todos' ? 'Colombia' : `Municipio ${region}`}
            </p>
          </div>

          {/* Estados */}
          {cargando && <Spinner texto="Cargando tours…" />}
          {error    && <Alert tipo="error">{error}</Alert>}

          {!cargando && !error && (
            <>
              {toursFiltrados.length === 0 ? (
                <p className={styles.sinResultados}>
                  No se encontraron tours con ese criterio.
                  {busqueda && (
                    <button
                      className={styles.limpiarBtn}
                      onClick={() => { setBusqueda(''); setRegion('Todos'); }}
                    >
                      Limpiar filtros
                    </button>
                  )}
                </p>
              ) : (
                <div className={`three-col-grid ${styles.grid}`}>
                  {toursFiltrados.map(t => (
                    <TourCard key={t.idTour} tour={t} />
                  ))}
                </div>
              )}
            </>
          )}

        </div>
      </section>

      {/* ── CONTACTO ───────────────────────────────────── */}
      <section className="padding-block-250">
        <div className="container">
          <ContactForm />
        </div>
      </section>
    </>
  );
}
