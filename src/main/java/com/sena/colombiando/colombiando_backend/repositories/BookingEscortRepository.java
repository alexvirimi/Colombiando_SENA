package com.sena.colombiando.colombiando_backend.repositories;

import com.sena.colombiando.colombiando_backend.entities.BookingEscortEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookingEscortRepository extends JpaRepository<BookingEscortEntity, UUID> {
/** Consulta find by booking id.
 * @param bookingId parametro de entrada.
 * @return resultado de la operacion.
 */
    public List<BookingEscortEntity> findByBookingId(UUID bookingId);
/** Cuenta by booking id.
 * @param bookingId parametro de entrada.
 * @return resultado de la operacion.
 */
    public Integer countByBookingId(UUID bookingId);

/** Elimina by booking id.
 * @param bookingId parametro de entrada.
 */
    @Transactional
    void deleteByBookingId(UUID bookingId);
}
