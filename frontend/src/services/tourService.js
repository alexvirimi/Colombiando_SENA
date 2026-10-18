import api from "./api";

export const colombiaRegionData = {
  colombia: {
    name: "Colombia",
    imageUrl:
      "https://commons.wikimedia.org/wiki/Special:FilePath/Valle_de_Cocora,_Colombia_04.jpg?width=1200",
    description:
      "Valle de Cocora: la palma de cera del Quindío es el árbol nacional de Colombia",
  },
  regions: [
    {
      name: "Andina",
      imageUrl:
        "https://commons.wikimedia.org/wiki/Special:FilePath/Valle_de_Cocora,_Colombia_04.jpg?width=1200",
      description: "Valle de Cocora, Salento, Quindío",
    },
    {
      name: "Caribe",
      imageUrl:
        "https://upload.wikimedia.org/wikipedia/commons/7/76/Cabo_San_Juan%2C_Colombia.jpg?utm_source=es.wikipedia.org&utm_campaign=index&utm_content=original",
      description: "Murallas de Cartagena de Indias",
    },
    {
      name: "Pacífica",
      imageUrl:
        "https://commons.wikimedia.org/wiki/Special:FilePath/BEACH_1_NUQUI_CHOCO_COLOMBIA.jpg?width=1200",
      description: "Playa de Nuquí, Chocó",
    },
    {
      name: "Orinoquía",
      imageUrl:
        "https://wp.iagua.es/wp-content/uploads/2026/04/cano-cristales-wikipedia.jpg",
      description: "Caño Cristales, La Macarena, Meta",
    },
    {
      name: "Amazonía",
      imageUrl:
        "https://colombia.co/sites/default/files/marca-pais/media/images/reyna-victoria-amazonica.webp",
      description: "Pendiente: no encontré un enlace directo verificado",
    },
    {
      name: "Insular",
      imageUrl:
        "https://commons.wikimedia.org/wiki/Special:FilePath/Isla_Providencia_Colombia_by_Luis_Barreto.png",
      description: "Isla de Providencia, San Andrés y Providencia",
    },
  ],
};

const tourService = {
  listarActivos: async () => {
    const response = await api.get("/schedule-instances", {
      params: { state: "ACTIVE" },
    });
    return { ...response, data: await groupByPlace(response.data) };
  },
  listarTodos: async () => {
    const response = await api.get("/schedule-instances");
    return { ...response, data: await groupByPlace(response.data) };
  },
  obtenerPorId: async (id) => {
    const response = await api.get("/schedule-instances", {
      params: { placeId: id, state: "ACTIVE" },
    });
    const instances = response.data.length
      ? response.data
      : [(await api.get(`/schedule-instances/${id}`)).data];
    const tour = (await groupByPlace(instances))[0];
    return {
      ...response,
      data: tour,
    };
  },
};

async function groupByPlace(instances) {
  const languageCache = new Map();
  const mappedInstances = await Promise.all(
    instances.map(async (instance) => {
      const tour = mapInstance(instance);
      const guideId = tour.empleados?.[0]?.idUsuario;
      if (guideId && !languageCache.has(guideId)) {
        languageCache.set(guideId, loadGuideLanguages(guideId));
      }
      tour.idiomas = guideId
        ? await languageCache.get(guideId)
        : [];
      return tour;
    }),
  );
  const places = new Map();

  mappedInstances.forEach((instance) => {
    const placeId = instance.idLugar || instance.idTour;
    const place = places.get(placeId) || {
      idTour: placeId,
      idLugar: placeId,
      nombre: instance.nombre,
      descripcion: instance.descripcion,
      imagenUrl: instance.imagenUrl,
      municipality: instance.municipality,
      region: instance.region,
      destinos: instance.destinos,
      instancias: [],
      guias: [],
    };
    place.instancias.push(instance);
    instance.empleados.forEach((guide) => {
      const existingGuide = place.guias.find(
        (item) => item.idUsuario === guide.idUsuario,
      );
      if (existingGuide) {
        existingGuide.instancias.push(instance);
      } else {
        place.guias.push({
          ...guide,
          idiomas: instance.idiomas,
          instancias: [instance],
        });
      }
    });
    places.set(placeId, place);
  });

  return [...places.values()];
}

function mapInstance(instance) {
  const schedule = instance.schedule;
  const place = schedule?.place;
  const placeData = place?.data || {};
  const scheduleData = schedule?.data || {};
  const municipality = place?.address?.data?.municipality || "";
  const imagenUrl =
    placeData.headerImg ||
    place?.headerImg ||
    placeData.imageUrl ||
    place?.imageUrl ||
    colombiaRegionData.regions.find(
      (region) => region.name.toLocaleLowerCase() === municipality.trim().toLocaleLowerCase(),
    )?.imageUrl ||
    colombiaRegionData.colombia.imageUrl;

  return {
    idTour: instance.id,
    idScheduleInstance: instance.id,
    idLugar: place?.id,
    nombre: placeData.name || "Experiencia Colombiando",
    descripcion: placeData.description || "",
    imagenUrl,
    municipality,
    region: municipality,
    precio: Number(schedule?.pricePerPerson || 0),
    capacidadMaxima: schedule?.maxCapacity || instance.data?.maxCapacity || 0,
    capacidadDisponible: instance.availableCapacity ?? 0,
    fechaSalida: instance.data?.date || scheduleData.startDate,
    fechaRegreso: scheduleData.endDate,
    duracionDias:
      scheduleData.startDate && scheduleData.endDate
        ? Math.max(
            1,
            Math.round(
              (new Date(scheduleData.endDate) -
                new Date(scheduleData.startDate)) /
                86400000,
            ),
          )
        : null,
    estado: instance.state,
    destinos: place ? [{ idDestino: place.id, nombre: placeData.name }] : [],
    empleados: schedule?.guide
      ? [
          {
            idUsuario: schedule.guide.id,
            nombre: schedule.guide.name,
            apellido: schedule.guide.lastName,
            bio: schedule.guide.bio,
            imagenPerfil: schedule.guide.profileImage,
            estado: schedule.guide.status,
          },
        ]
      : [],
    idiomas: [],
    actividades: [],
  };
}

async function loadGuideLanguages(guideId) {
  const response = await api.get("/guide-languages", {
    params: { guideId },
  });
  return response.data
    .map((item) => ({
      id: item.language?.id ?? item.id?.languageId,
      nombre: item.language?.data?.name ?? item.language?.data?.nativeName,
      nombreNativo: item.language?.data?.nativeName,
      codigo: item.language?.data?.code,
      nivel: item.level,
    }))
    .filter((idioma) => idioma.nombre);
}

export default tourService;
