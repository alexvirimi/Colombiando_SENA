import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Badge, Button } from "../ui/UI";
import styles from "./TourExtras.module.css";

const imagenFallback = "https://commons.wikimedia.org/wiki/Special:FilePath/Valle_de_Cocora,_Colombia_04.jpg?width=1200";

/* ── TourCarousel ── */
export function TourCarousel({ tours = [] }) {
  const [idx, setIdx] = useState(0);
  const navigate = useNavigate();

  useEffect(() => {
    if (tours.length <= 1) return;
    const t = setInterval(() => setIdx((i) => (i + 1) % tours.length), 5000);
    return () => clearInterval(t);
  }, [tours.length]);

  if (!tours.length) return null;
  const tour = tours[idx];
  const instancia = tour.instancias?.[0] || tour;

  return (
    <div className={styles.carrousel}>
      <div
        className={styles.bg}
        style={
          {
            backgroundImage: `url(${tour.imagenUrl || imagenFallback})`,
          }
        }
      />
      <div className={styles.content}>
        <button
          className={styles.btn}
          style={{ left: 0 }}
          onClick={() => setIdx((i) => (i - 1 + tours.length) % tours.length)}
          aria-label="Anterior"
        >
          ‹
        </button>
        <div className={styles.infoCard}>
          <p className={styles.region}>{tour.municipality} • Colombia</p>
          <h2 className={styles.nombre}>{tour.nombre}</h2>
          <div className="tour-tags" style={{ justifyContent: "center" }}>
            <Badge color="yellow">{instancia.fechaSalida}</Badge>
            <Badge color="blue">{instancia.duracionDias} días</Badge>
          </div>
          <button
            className={styles.verBtn}
            onClick={() => navigate(`/tours/${tour.idTour}`)}
          >
            Ver tour →
          </button>
        </div>
        <button
          className={styles.btn}
          style={{ right: 0 }}
          onClick={() => setIdx((i) => (i + 1) % tours.length)}
          aria-label="Siguiente"
        >
          ›
        </button>
      </div>
      <div className={styles.dots}>
        {tours.map((_, i) => (
          <button
            key={i}
            onClick={() => setIdx(i)}
            className={`${styles.dot} ${i === idx ? styles.dotOn : ""}`}
            aria-label={`Tour ${i + 1}`}
          />
        ))}
      </div>
    </div>
  );
}

/* ── TourFilters ── */
const REGIONES = [
  "Todos",
  "Caribe",
  "Andina",
  "Orinoquía",
  "Amazonía",
  "Pacífica",
  "Insular",
];

export function TourFilters({ regionActiva, busqueda, onRegion, onBusqueda }) {
  return (
    <nav className={styles.filtersNav} aria-label="Filtros de tours">
      <div className={styles.navWrapper}>
        <ul role="list" className={styles.navList}>
          {REGIONES.map((region) => (
            <li key={region}>
              <button
                className={`${styles.navItem} ${regionActiva === region ? styles.navOn : ""}`}
                onClick={() => onRegion(region)}
              >
                {region}
              </button>
            </li>
          ))}
        </ul>
      </div>
      <label className={styles.search}>
        <svg width="18" height="18" viewBox="0 0 32 32" fill="none">
          <path
            d="M30.25 30.25L20.5 20.5M1 12.375a11.375 11.375 0 1 0 22.75 0A11.375 11.375 0 0 0 1 12.375Z"
            stroke="currentColor"
            strokeWidth="2"
            strokeLinecap="round"
            strokeLinejoin="round"
          />
        </svg>
        <input
          type="search"
          placeholder="Buscar tour…"
          value={busqueda}
          onChange={(e) => onBusqueda(e.target.value)}
          aria-label="Buscar tour"
        />
      </label>
    </nav>
  );
}

/* ── GuideCard ── */
export function GuideCard({ empleado }) {
  const ini = `${empleado.nombre?.[0] ?? ""}${empleado.apellido?.[0] ?? ""}`;
  return (
    <div className={styles.guideCard}>
      <div className={styles.avatar}>{ini}</div>
      <div>
        <p className={styles.guideName}>
          {empleado.nombre} {empleado.apellido}
        </p>
        <p className={styles.guideCargo}>{empleado.cargo}</p>
      </div>
    </div>
  );
}

export function BookingInstanceCard({ tour }) {
  const navigate = useNavigate();
  const guide = tour.empleados?.[0];
  const canReserve = tour.estado === "ACTIVE" && tour.capacidadDisponible > 0;
  const available = Math.max(0, tour.capacidadDisponible);

  return (
    <div className={styles.bookingCard}>
      <div className={styles.bookingGuide}>
        <div className={styles.avatar}>
          {guide
            ? `${guide.nombre?.[0] ?? ""}${guide.apellido?.[0] ?? ""}`
            : "?"}
        </div>
        <div>
          <p className={styles.bookingLabel}>Guía</p>
          <p className={styles.guideName}>
            {guide ? `${guide.nombre} ${guide.apellido}` : "Guía por confirmar"}
          </p>
          {guide?.bio && <p className={styles.guideBio}>{guide.bio}</p>}
          <div className={styles.languageList}>
            {tour.idiomas?.length ? (
              tour.idiomas.map((idioma) => (
                <span
                  className={styles.languageBadge}
                  key={idioma.id ?? idioma.codigo}
                >
                  {idioma.nombre}
                  {idioma.nivel && <small>{idioma.nivel}</small>}
                </span>
              ))
            ) : (
              <span className={styles.noLanguages}>
                Información no disponible
              </span>
            )}
          </div>
        </div>
      </div>

      <div className={styles.bookingDetails}>
        <div>
          <span>Salida</span>
          <strong>{tour.fechaSalida}</strong>
        </div>
        <div>
          <span>Regreso</span>
          <strong>{tour.fechaRegreso || "—"}</strong>
        </div>
        <div>
          <span>Precio por persona</span>
          <strong>{tour.precio.toLocaleString("es-CO")} COP</strong>
        </div>
        <div>
          <span>Cupos disponibles</span>
          <strong>
            {available} de {tour.capacidadMaxima}
          </strong>
        </div>
      </div>

      <div className={styles.bookingAction}>
        <Badge color={tour.estado === "ACTIVE" ? "green" : "red"}>
          {tour.estado === "ACTIVE" ? "Disponible" : "No disponible"}
        </Badge>
        {canReserve && (
          <Button
            onClick={() => navigate(`/reservas/nueva?idTour=${tour.idTour}`)}
          >
            Reservar esta instancia
          </Button>
        )}
      </div>
    </div>
  );
}

/* ── ReviewCard ── */
export function ReviewCard({ review }) {
  const ini =
    review.nombreUsuario
      ?.split(" ")
      .map((p) => p[0])
      .slice(0, 2)
      .join("") ?? "??";
  return (
    <div className={styles.reviewCard}>
      <div className={styles.reviewHeader}>
        <div className={styles.avatar}>{ini}</div>
        <p className={styles.reviewNombre}>{review.nombreUsuario}</p>
      </div>
      <p className={styles.reviewTexto}>{review.comentario}</p>
      <div className={styles.reviewFooter}>
        <span>{review.tourNombre}</span>
        <span>{review.guiaNombre}</span>
        <span>{review.fecha}</span>
      </div>
    </div>
  );
}

/* ── Itinerary ── */
export function Itinerary({ actividades = [] }) {
  if (!actividades.length)
    return (
      <p style={{ color: "var(--clr-gris-400)", fontSize: "var(--fs-200)" }}>
        El itinerario estará disponible próximamente.
      </p>
    );
  return (
    <table className={styles.itinerary}>
      <tbody>
        {actividades.map((a, i) => (
          <tr key={i} className={styles.itRow}>
            <td className={styles.itNo}>{a.numeroActividad}</td>
            <td className={styles.itAct}>{a.descripcion}</td>
            <td className={styles.itTime}>{a.hora}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
