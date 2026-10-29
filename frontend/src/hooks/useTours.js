import { useState, useEffect } from 'react';
import tourService from '../services/tourService';

export function useTours() {
  const [tours,    setTours]    = useState([]);
  const [cargando, setCargando] = useState(true);
  const [error,    setError]    = useState(null);

  async function cargar() {
    setCargando(true);
    setError(null);
    try {
      const { data } = await tourService.listarActivos();
      setTours(Array.isArray(data) ? data : []);
    } catch (e) {
      setError(e.response?.data?.mensaje || 'No se pudieron cargar los tours.');
    } finally {
      setCargando(false);
    }
  }

  useEffect(() => {
    Promise.resolve().then(cargar);
  }, []);
  return { tours, cargando, error, recargar: cargar };
}

export function useTourDetalle(id) {
  const [tour,     setTour]     = useState(null);
  const [cargando, setCargando] = useState(true);
  const [error,    setError]    = useState(null);

  useEffect(() => {
    if (!id) return;
    Promise.resolve().then(() => setCargando(true));
    tourService.obtenerPorId(id)
      .then(({ data }) => setTour(data))
      .catch(e => setError(e.response?.data?.mensaje || 'Tour no encontrado.'))
      .finally(() => setCargando(false));
  }, [id]);

  return { tour, cargando, error };
}
