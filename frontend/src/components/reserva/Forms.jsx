import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Button, Input, Alert, EstadoBadge } from "../ui/UI";
import { paymentService } from "../../services/otrosServices";
import styles from "./Forms.module.css";

const pesos = (n) =>
  new Intl.NumberFormat("es-CO", {
    style: "currency",
    currency: "COP",
    maximumFractionDigits: 0,
  }).format(n);

/* ═══════════════════════════════════════════════════════════
   BookingForm — formulario de nueva reserva
   ═══════════════════════════════════════════════════════════ */
export function BookingForm({ tour, usuario, onSubmit, onChange, cargando, error }) {
  const [form, setForm] = useState({
    idTour: tour?.idTour ?? "",
    numeroPasajeros: 1,
    acompanantes: [],
  });
  const [errores, setErrores] = useState({});

  function cambiarPasajeros(value) {
    const numeroPasajeros = Math.max(1, value);
    setForm((previous) => {
      const nextForm = {
        ...previous,
        numeroPasajeros,
        acompanantes: Array.from(
          { length: Math.max(0, numeroPasajeros - 1) },
          (_, index) =>
            previous.acompanantes[index] || {
              nombre: "",
              apellido: "",
              tipoDocumento: "CC",
              numeroDocumento: "",
              fechaNacimiento: "",
            },
        ),
      };
      onChange?.(nextForm);
      return nextForm;
    });
  }

  function cambiarAcompanante(index, field, value) {
    setForm((previous) => {
      const nextForm = {
        ...previous,
        acompanantes: previous.acompanantes.map((acompanante, currentIndex) =>
          currentIndex === index
            ? { ...acompanante, [field]: value }
            : acompanante,
        ),
      };
      onChange?.(nextForm);
      return nextForm;
    });
  }

  function validar() {
    const err = {};
    if (form.numeroPasajeros < 1) err.numeroPasajeros = "Mínimo 1 pasajero.";
    if (tour && form.numeroPasajeros > tour.capacidadMaxima)
      err.numeroPasajeros = `Máximo ${tour.capacidadMaxima} pasajeros.`;
    form.acompanantes.forEach((acompanante, index) => {
      if (!acompanante.nombre)
        err[`acompanante-${index}-nombre`] = "Escribe los nombres.";
      if (!acompanante.apellido)
        err[`acompanante-${index}-apellido`] = "Escribe los apellidos.";
      if (!acompanante.numeroDocumento)
        err[`acompanante-${index}-numeroDocumento`] = "Escribe el documento.";
      if (!acompanante.fechaNacimiento)
        err[`acompanante-${index}-fechaNacimiento`] =
          "Selecciona la fecha de nacimiento.";
    });
    setErrores(err);
    return !Object.keys(err).length;
  }

  function handleSubmit(e) {
    e.preventDefault();
    if (validar()) onSubmit(form);
  }

  const total = tour ? tour.precio * form.numeroPasajeros : 0;

  return (
    <form className={styles.form} onSubmit={handleSubmit} noValidate>
      {error && <Alert tipo="error">{error}</Alert>}

      {tour && (
        <div className={styles.tourPreview}>
          <div className={styles.tourPreviewColor} />
          <div>
            <p className={styles.tourPreviewNombre}>{tour.nombre}</p>
            <p className={styles.tourPreviewSub}>
              {tour.duracionDias} días · Máx. {tour.capacidadMaxima} personas
            </p>
          </div>
          <p className={styles.tourPreviewPrecio}>
            {pesos(tour.precio)}
            <span>/pax</span>
          </p>
        </div>
      )}

      <div className={styles.field}>
        <label className={styles.label}>Cliente</label>
        <input
          className={styles.input}
          value={
            usuario
              ? `${usuario.nombre} ${usuario.apellido} (${usuario.correo})`
              : ""
          }
          readOnly
        />
      </div>

      <div className={styles.row}>
        <div className={styles.field}>
          <label className={styles.label}>
            Pasajeros <span className={styles.req}>*</span>
          </label>
          <input
            type="number"
            className={`${styles.input} ${errores.numeroPasajeros ? styles.inputErr : ""}`}
            min={1}
            max={tour?.capacidadMaxima ?? 50}
            value={form.numeroPasajeros}
            onChange={(e) => cambiarPasajeros(+e.target.value)}
          />
          {errores.numeroPasajeros && (
            <p className={styles.err}>{errores.numeroPasajeros}</p>
          )}
        </div>
        <div className={styles.field}>
          <label className={styles.label}>Fecha del tour</label>
          <input
            type="date"
            className={styles.input}
            value={tour?.fechaSalida ?? ""}
            readOnly
          />
        </div>
      </div>

      {form.acompanantes.length > 0 && (
        <div className={styles.field}>
          <h3>Datos de acompañantes</h3>
          {form.acompanantes.map((acompanante, index) => (
            <div className={styles.form} key={index}>
              <strong>Acompañante {index + 1}</strong>
              <div className={styles.row}>
                <Input
                  label="Nombres"
                  requerido
                  value={acompanante.nombre}
                  onChange={(e) =>
                    cambiarAcompanante(index, "nombre", e.target.value)
                  }
                  error={errores[`acompanante-${index}-nombre`]}
                />
                <Input
                  label="Apellidos"
                  requerido
                  value={acompanante.apellido}
                  onChange={(e) =>
                    cambiarAcompanante(index, "apellido", e.target.value)
                  }
                  error={errores[`acompanante-${index}-apellido`]}
                />
              </div>
              <div className={styles.row}>
                <div className={styles.field}>
                  <label className={styles.label}>Tipo de documento</label>
                  <select
                    className={styles.select}
                    value={acompanante.tipoDocumento}
                    onChange={(e) =>
                      cambiarAcompanante(index, "tipoDocumento", e.target.value)
                    }
                  >
                    <option value="CC">Cédula de ciudadanía</option>
                    <option value="CE">Cédula de extranjería</option>
                    <option value="TI">Tarjeta de identidad</option>
                    <option value="PASSPORT">Pasaporte</option>
                  </select>
                </div>
                <Input
                  label="Número de documento"
                  requerido
                  value={acompanante.numeroDocumento}
                  onChange={(e) =>
                    cambiarAcompanante(index, "numeroDocumento", e.target.value)
                  }
                  error={errores[`acompanante-${index}-numeroDocumento`]}
                />
              </div>
              <Input
                label="Fecha de nacimiento"
                tipo="date"
                requerido
                value={acompanante.fechaNacimiento}
                onChange={(e) =>
                  cambiarAcompanante(index, "fechaNacimiento", e.target.value)
                }
                error={errores[`acompanante-${index}-fechaNacimiento`]}
              />
            </div>
          ))}
        </div>
      )}

      {total > 0 && (
        <div className={styles.costoPreview}>
          <span>Costo estimado</span>
          <strong style={{ color: "var(--clr-verde)" }}>{pesos(total)}</strong>
        </div>
      )}

      <Button tipo="submit" ancho disabled={cargando}>
        {cargando ? "Creando reserva…" : "Crear reserva"}
      </Button>
    </form>
  );
}

/* ═══════════════════════════════════════════════════════════
   BookingSummary — panel lateral de resumen (solo presentación)
   ═══════════════════════════════════════════════════════════ */
export function BookingSummary({ tour, pax = 1 }) {
  if (!tour) return null;
  const total = tour.precio * pax;
  return (
    <aside className={styles.summary}>
      <h3 className={styles.summaryTitle}>Resumen</h3>
      <div className={styles.summaryTourBanner}>
        <p className={styles.summaryTourNombre}>{tour.nombre}</p>
        <p className={styles.summaryTourSub}>
          {tour.duracionDias} días · {tour.estado}
        </p>
      </div>
      {[
        ["Precio por persona", pesos(tour.precio)],
        ["Pasajeros", pax],
        ["Fecha del tour", tour.fechaSalida],
        ["Capacidad máxima", `${tour.capacidadMaxima} pax`],
      ].map(([k, v]) => (
        <div key={k} className={styles.summaryRow}>
          <span>{k}</span>
          <span>{v}</span>
        </div>
      ))}
      <div className={styles.summaryTotal}>
        <span>Total estimado</span>
        <strong style={{ color: "var(--clr-verde)" }}>{pesos(total)}</strong>
      </div>
      <p className={styles.summaryNote}>
        La reserva se confirma automáticamente al recibir el pago completo.
      </p>
    </aside>
  );
}

/* ═══════════════════════════════════════════════════════════
   PaymentForm — formulario de registro de pago
   ═══════════════════════════════════════════════════════════ */
export function PaymentForm({ idReserva, totalPendiente, onExito }) {
  const [form, setForm] = useState({
    monto: totalPendiente ?? "",
    metodoPago: "",
  });
  const [cargando, setCargando] = useState(false);
  const [error, setError] = useState("");
  const set = (k) => (e) => setForm((p) => ({ ...p, [k]: e.target.value }));

  const METODOS = [
    { value: "CASH", label: "💵  Efectivo" },
    { value: "CARD", label: "💳  Tarjeta débito / crédito" },
    { value: "TRANSFER", label: "🏦  Transferencia bancaria" },
    { value: "PSE", label: "🌐  PSE" },
  ];

  async function handleSubmit(e) {
    e.preventDefault();
    if (!form.metodoPago || !form.monto) {
      setError("Monto y método de pago son obligatorios.");
      return;
    }
    setCargando(true);
    setError("");
    try {
      await paymentService.registrar({
        idReserva,
        monto: form.monto,
        metodoPago: form.metodoPago,
      });
      onExito?.();
    } catch (err) {
      setError(err.response?.data?.mensaje || "Error al registrar el pago.");
    } finally {
      setCargando(false);
    }
  }

  return (
    <form className={styles.form} onSubmit={handleSubmit} noValidate>
      {error && <Alert tipo="error">{error}</Alert>}

      <Input
        label="Monto (COP)"
        id="monto"
        tipo="number"
        requerido
        value={form.monto}
        onChange={set("monto")}
        placeholder="1800000"
      />

      <div className={styles.field}>
        <label className={styles.label}>
          Método de pago <span className={styles.req}>*</span>
        </label>
        <select
          className={styles.select}
          value={form.metodoPago}
          onChange={set("metodoPago")}
          required
        >
          <option value="">Seleccionar…</option>
          {METODOS.map((m) => (
            <option key={m.value} value={m.value}>
              {m.label}
            </option>
          ))}
        </select>
      </div>

      <Button tipo="submit" ancho disabled={cargando}>
        {cargando ? "Validando…" : "Validar pago"}
      </Button>
    </form>
  );
}

/* ═══════════════════════════════════════════════════════════
   ConfirmationBanner — reserva creada con éxito
   ═══════════════════════════════════════════════════════════ */
export function ConfirmationBanner({ reserva, tour, cliente }) {
  const navigate = useNavigate();
  if (!reserva) return null;
  const total = tour ? tour.precio * reserva.numeroPasajeros : 0;

  return (
    <div className={styles.confirm}>
      <div className={styles.confirmIcon}>🇨🇴</div>
      <h1 className={styles.confirmTitle}>¡Reserva creada exitosamente!</h1>
      <p className={styles.confirmSub}>
        Tu reserva está pendiente de pago. Confírmala registrando el pago.
      </p>

      <div className={styles.confirmCard}>
        {[
          [
            "Reserva",
            `Reserva del tour ${tour?.nombre || reserva.lugarNombre || "sin nombre"}`,
          ],
          [
            "Cliente",
            cliente
              ? `${cliente.nombre} ${cliente.apellido}`
              : `#${reserva.idCliente}`,
          ],
          ["Guía", reserva.guia
            ? `${reserva.guia.nombre} ${reserva.guia.apellido}`
            : "Guía por confirmar"],
          ["Pasajeros", reserva.numeroPasajeros],
          ["Fecha del tour", reserva.fechaReserva],
          ["Total estimado", total > 0 ? pesos(total) : "—"],
        ].map(([k, v]) => (
          <div key={k} className={styles.confirmRow}>
            <span className={styles.confirmKey}>{k}</span>
            <strong>{v}</strong>
          </div>
        ))}
        <div className={styles.confirmEstado}>
          <EstadoBadge estado={reserva.estado} />
        </div>
      </div>

      <div className={styles.confirmActions}>
        <Button
          onClick={() =>
            navigate(`/pagos/nueva?idReserva=${reserva.idReserva}`)
          }
        >
          Registrar pago ahora
        </Button>
        <Button variante="outline" onClick={() => navigate("/perfil")}>
          Ver mis tours activos
        </Button>
        <Button variante="outline" onClick={() => navigate("/tours")}>
          Explorar más tours
        </Button>
      </div>
    </div>
  );
}

/* ═══════════════════════════════════════════════════════════
   ContactForm — formulario de contacto reutilizable
   Aparece en: HomePage, ToursPage, AboutPage
   ═══════════════════════════════════════════════════════════ */
export function ContactForm() {
  const [form, setForm] = useState({ nombre: "", correo: "", mensaje: "" });
  const [sent, setSent] = useState(false);
  const [error, setError] = useState("");
  const set = (k) => (e) => setForm((p) => ({ ...p, [k]: e.target.value }));

  function handleSubmit(e) {
    e.preventDefault();
    if (!form.nombre || !form.correo || !form.mensaje) {
      setError("Completa todos los campos obligatorios.");
      return;
    }
    setSent(true);
  }

  if (sent)
    return (
      <div className={styles.contactWrap}>
        <Alert tipo="success">
          ✓ Mensaje enviado. Te responderemos pronto.
        </Alert>
        <Button
          variante="outline"
          onClick={() => {
            setSent(false);
            setForm({ nombre: "", correo: "", mensaje: "" });
          }}
        >
          Enviar otro mensaje
        </Button>
      </div>
    );

  return (
    <form className={styles.contactWrap} onSubmit={handleSubmit} noValidate>
      <div className={styles.contactHeader}>
        <p style={{ color: "var(--clr-gris-600)", fontSize: "var(--fs-200)" }}>
          ¿Quieres saber más?
        </p>
        <h2 className="fs-secondary-heading">Contáctanos</h2>
      </div>
      {error && <Alert tipo="error">{error}</Alert>}
      <div className={styles.row}>
        <Input
          label="Nombre Completo"
          id="c-nombre"
          requerido
          value={form.nombre}
          onChange={set("nombre")}
          placeholder="Jane Doe"
        />
        <Input
          label="Correo"
          id="c-correo"
          tipo="email"
          requerido
          value={form.correo}
          onChange={set("correo")}
          placeholder="example@email.com"
        />
      </div>
      <div className={styles.field}>
        <label className={styles.label}>Mensaje</label>
        <textarea
          className={styles.textarea}
          rows={4}
          placeholder="Mensaje"
          value={form.mensaje}
          onChange={set("mensaje")}
          required
        />
      </div>
      <div className={styles.checkRow}>
        <input type="checkbox" id="politica" required />
        <label htmlFor="politica" style={{ fontSize: "var(--fs-200)" }}>
          Acepto la política de tratamiento de datos personales
        </label>
      </div>
      <Button tipo="submit" ancho>
        Enviar Mensaje
      </Button>
    </form>
  );
}
