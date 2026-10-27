import api from "./api";

export const authService = {
  login: async (correo, contrasena) => {
    const response = await api.post("/auth/login", {
      email: correo,
      password: contrasena,
    });
    const user = response.data;
    return {
      ...response,
      data: {
        usuario: {
          idUsuario: user.id,
          nombre: user.name,
          apellido: user.lastName,
          correo: user.email,
          telefono: user.phone,
        },
        token: null,
      },
    };
  },
  registrar: (datos) =>
    api.post("/auth/register", {
      name: datos.nombre,
      lastName: datos.apellido,
      email: datos.correo,
      phone: datos.telefono,
      password: datos.contrasena,
    }),
  recuperarPassword: () =>
    Promise.reject(
      new Error("La recuperación de contraseña aún no está disponible."),
    ),
};

/* NUEVO — configuración de cuenta (UserService: update / changePassword / delete) */
export const userService = {
  actualizar: (id, datos) =>
    api.patch(`/users/${id}`, {
      data: {
        name: datos.nombre,
        lastName: datos.apellido,
        phone: datos.telefono,
        email: datos.correo,
      },
    }),
  cambiarPassword: (id, datos) =>
    api.patch(`/users/change-password/${id}`, {
      currentPassword: datos.currentPassword,
      newPassword: datos.newPassword,
    }),
  eliminar: (id) => api.delete(`/users/${id}`),
};

export const bookingService = {
  crear: async (datos) => {
    const response = await api.post("/bookings", {
      userId: datos.userId,
      scheduleInstanceId: datos.idTour,
      numPeople: Number(datos.numeroPasajeros),
    });
    return { ...response, data: mapBooking(response.data) };
  },
  listarPorCliente: async (id) => {
    const response = await api.get(`/bookings?userId=${id}`);
    return { ...response, data: response.data.map(mapBooking) };
  },
  obtenerPorId: async (id) => {
    const response = await api.get(`/bookings/${id}`);
    return { ...response, data: mapBooking(response.data) };
  },
  actualizar: (id, datos) => api.patch(`/bookings/${id}`, datos),
  cancelar: (id) => api.delete(`/bookings/${id}`),
};

function mapBooking(booking) {
  return {
    idReserva: booking.id,
    idTour: booking.scheduleInstance?.id,
    numeroPasajeros: booking.numPeople,
    totalReserva: Number(booking.totalPrice || 0),
    estado: booking.status,
    fechaReserva: booking.scheduleInstance?.data?.date,
    guia: booking.scheduleInstance?.schedule?.guide
      ? {
          nombre: booking.scheduleInstance.schedule.guide.name,
          apellido: booking.scheduleInstance.schedule.guide.lastName,
        }
      : null,
    lugarNombre: booking.scheduleInstance?.schedule?.place?.data?.name
      || booking.scheduleInstance?.schedule?.place?.name
      || null,
    cliente: booking.user,
  };
}

export const bookingEscortService = {
  crear: (datos) =>
    api.post("/booking-escorts", {
      bookingId: datos.bookingId,
      birthDate: datos.fechaNacimiento,
      data: {
        name: datos.nombre,
        lastName: datos.apellido,
        documentType: datos.tipoDocumento,
        idNumber: datos.numeroDocumento,
      },
    }),
  /* NUEVO — acompañantes de una reserva (BookingEscortService.getAllBookingsByBookingId) */
  listarPorReserva: async (id) => {
    const response = await api.get(`/booking-escorts/by-booking/${id}`);
    return { ...response, data: response.data.map(mapEscort) };
  },
};

function mapEscort(escort) {
  const d = escort.data ?? escort;
  return {
    idAcompanante: escort.id,
    nombre: d.name,
    apellido: d.lastName,
    tipoDocumento: d.documentType,
    numeroDocumento: d.idNumber,
    fechaNacimiento: escort.birthDate,
  };
}

export const paymentService = {
  registrar: (datos) =>
    api.post("/payments", {
      bookingId: datos.idReserva,
      amountToPay: Number(datos.monto),
      paymentMethod: datos.metodoPago,
      currency: "COP",
    }),
  listarPorReserva: async (id) => {
    const response = await api.get(`/payments?bookingId=${id}`);
    return { ...response, data: response.data.map(mapPayment) };
  },
  totalPorReserva: async (id) => {
    const response = await api.get(`/payments?bookingId=${id}`);
    const total = response.data.reduce(
      (sum, payment) => sum + Number(payment.amountToPay || 0),
      0,
    );
    return { ...response, data: { total } };
  },
};

function mapPayment(payment) {
  return {
    idPago: payment.id,
    monto: Number(payment.amountToPay || 0),
    metodoPago: payment.paymentMethod,
    estado: payment.status,
  };
}

export const destinoService = {
  listarTodos: () => api.get("/places"),
  obtenerPorId: (id) => api.get(`/places/${id}`),
  crear: (datos) => api.post("/places", datos),
  actualizar: (id, datos) => api.patch(`/places/${id}`, datos),
  eliminar: (id) => api.delete(`/places/${id}`),
};

export const reviewService = {
  crear: (datos) =>
    api.post("/reviews", {
      userId: datos.userId,
      bookingId: datos.idReserva,
      rating: Number(datos.calificacion),
      review: datos.comentario,
    }),
};
