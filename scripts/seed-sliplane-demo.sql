USE mibarrio_db;

SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;

SET @password_admin123 = '$2a$10$Hrvy3CUebXCOskmyaQt59.0RwIwTAEMhdRo1D9uErvIyLjCctGnde';

INSERT INTO roles (nombre)
SELECT 'ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE nombre = 'ADMIN');

INSERT INTO roles (nombre)
SELECT 'COMERCIO'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE nombre = 'COMERCIO');

SET @rol_admin = (SELECT id FROM roles WHERE nombre = 'ADMIN' LIMIT 1);
SET @rol_comercio = (SELECT id FROM roles WHERE nombre = 'COMERCIO' LIMIT 1);

INSERT INTO categorias (nombre, activo)
SELECT 'Hostelería', 1
WHERE NOT EXISTS (SELECT 1 FROM categorias WHERE nombre = 'Hostelería');

INSERT INTO categorias (nombre, activo)
SELECT 'Belleza', 1
WHERE NOT EXISTS (SELECT 1 FROM categorias WHERE nombre = 'Belleza');

INSERT INTO categorias (nombre, activo)
SELECT 'Alimentación', 1
WHERE NOT EXISTS (SELECT 1 FROM categorias WHERE nombre = 'Alimentación');

INSERT INTO categorias (nombre, activo)
SELECT 'Salud', 1
WHERE NOT EXISTS (SELECT 1 FROM categorias WHERE nombre = 'Salud');

INSERT INTO categorias (nombre, activo)
SELECT 'Floristería', 1
WHERE NOT EXISTS (SELECT 1 FROM categorias WHERE nombre = 'Floristería');

INSERT INTO categorias (nombre, activo)
SELECT 'Mercería', 1
WHERE NOT EXISTS (SELECT 1 FROM categorias WHERE nombre = 'Mercería');

INSERT INTO categorias (nombre, activo)
SELECT 'Comercio', 1
WHERE NOT EXISTS (SELECT 1 FROM categorias WHERE nombre = 'Comercio');

SET @cat_hosteleria = (SELECT id FROM categorias WHERE nombre = 'Hostelería' LIMIT 1);
SET @cat_belleza = (SELECT id FROM categorias WHERE nombre = 'Belleza' LIMIT 1);
SET @cat_alimentacion = (SELECT id FROM categorias WHERE nombre = 'Alimentación' LIMIT 1);
SET @cat_salud = (SELECT id FROM categorias WHERE nombre = 'Salud' LIMIT 1);
SET @cat_floristeria = (SELECT id FROM categorias WHERE nombre = 'Floristería' LIMIT 1);
SET @cat_merceria = (SELECT id FROM categorias WHERE nombre = 'Mercería' LIMIT 1);
SET @cat_comercio = (SELECT id FROM categorias WHERE nombre = 'Comercio' LIMIT 1);

INSERT INTO usuarios (nombre, email, password, activo, estado, rol_id, created_at, updated_at)
SELECT 'Administradora miBarrio', 'admin@admin.com', @password_admin123, 1, 'ACTIVO', @rol_admin, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE email = 'admin@admin.com');

INSERT INTO usuarios (nombre, email, password, activo, estado, rol_id, created_at, updated_at)
SELECT 'Bar Chaflán', 'a@a.com', @password_admin123, 1, 'ACTIVO', @rol_comercio, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE email = 'a@a.com');

INSERT INTO usuarios (nombre, email, password, activo, estado, rol_id, created_at, updated_at)
SELECT 'Peluquería Emma Vázquez', 'b@b.com', @password_admin123, 1, 'ACTIVO', @rol_comercio, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE email = 'b@b.com');

INSERT INTO usuarios (nombre, email, password, activo, estado, rol_id, created_at, updated_at)
SELECT 'Panadería Élida Mesejo', 'c@c.com', @password_admin123, 1, 'ACTIVO', @rol_comercio, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE email = 'c@c.com');

INSERT INTO usuarios (nombre, email, password, activo, estado, rol_id, created_at, updated_at)
SELECT 'Farmacia Adúriz', 'd@d.com', @password_admin123, 1, 'ACTIVO', @rol_comercio, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE email = 'd@d.com');

INSERT INTO usuarios (nombre, email, password, activo, estado, rol_id, created_at, updated_at)
SELECT 'Floristería Abelleira', 'e@e.com', @password_admin123, 1, 'ACTIVO', @rol_comercio, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE email = 'e@e.com');

INSERT INTO usuarios (nombre, email, password, activo, estado, rol_id, created_at, updated_at)
SELECT 'Mercería MIC', 'f@f.com', @password_admin123, 1, 'ACTIVO', @rol_comercio, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE email = 'f@f.com');

INSERT INTO usuarios (nombre, email, password, activo, estado, rol_id, created_at, updated_at)
SELECT 'A Casa da Collona', 'g@g.com', @password_admin123, 1, 'ACTIVO', @rol_comercio, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE email = 'g@g.com');

INSERT INTO usuarios (nombre, email, password, activo, estado, rol_id, created_at, updated_at)
SELECT 'Pulpeira A Nova Lanchiña', 'h@h.com', @password_admin123, 1, 'ACTIVO', @rol_comercio, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE email = 'h@h.com');

INSERT INTO usuarios (nombre, email, password, activo, estado, rol_id, created_at, updated_at)
SELECT 'Jumbo H-1', 'i@i.com', @password_admin123, 1, 'ACTIVO', @rol_comercio, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE email = 'i@i.com');

INSERT INTO usuarios (nombre, email, password, activo, estado, rol_id, created_at, updated_at)
SELECT 'Fruta & Co', 'j@j.com', @password_admin123, 1, 'ACTIVO', @rol_comercio, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE email = 'j@j.com');

INSERT INTO usuarios (nombre, email, password, activo, estado, rol_id, created_at, updated_at)
SELECT 'Liege Alves Beauty Concept', 'k@k.com', @password_admin123, 1, 'ACTIVO', @rol_comercio, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE email = 'k@k.com');

INSERT INTO comercios (nombre, direccion, telefono, activo, estado, categoria_id, usuario_id, imagen_url, created_at, updated_at)
SELECT 'Bar Chaflán', 'Rúa da Merced, 12', '981 11 20 31', 1, 'ACTIVO', @cat_hosteleria, (SELECT id FROM usuarios WHERE email = 'a@a.com'), 'https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?auto=format&fit=crop&w=1200&q=80', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM comercios WHERE nombre = 'Bar Chaflán');

INSERT INTO comercios (nombre, direccion, telefono, activo, estado, categoria_id, usuario_id, imagen_url, created_at, updated_at)
SELECT 'Peluquería Emma Vázquez', 'Rúa Eugenio Carré Aldao, 5', '981 22 10 45', 1, 'ACTIVO', @cat_belleza, (SELECT id FROM usuarios WHERE email = 'b@b.com'), 'https://images.unsplash.com/photo-1560066984-138dadb4c035?auto=format&fit=crop&w=1200&q=80', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM comercios WHERE nombre = 'Peluquería Emma Vázquez');

INSERT INTO comercios (nombre, direccion, telefono, activo, estado, categoria_id, usuario_id, imagen_url, created_at, updated_at)
SELECT 'Panadería Élida Mesejo', 'Avenida dos Mallos, 42', '981 33 44 55', 1, 'ACTIVO', @cat_alimentacion, (SELECT id FROM usuarios WHERE email = 'c@c.com'), 'https://images.unsplash.com/photo-1509440159596-0249088772ff?auto=format&fit=crop&w=1200&q=80', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM comercios WHERE nombre = 'Panadería Élida Mesejo');

INSERT INTO comercios (nombre, direccion, telefono, activo, estado, categoria_id, usuario_id, imagen_url, created_at, updated_at)
SELECT 'Farmacia Adúriz', 'Ronda de Outeiro, 188', '981 44 22 10', 1, 'ACTIVO', @cat_salud, (SELECT id FROM usuarios WHERE email = 'd@d.com'), 'https://images.unsplash.com/photo-1587854692152-cbe660dbde88?auto=format&fit=crop&w=1200&q=80', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM comercios WHERE nombre = 'Farmacia Adúriz');

INSERT INTO comercios (nombre, direccion, telefono, activo, estado, categoria_id, usuario_id, imagen_url, created_at, updated_at)
SELECT 'Floristería Abelleira', 'Rúa Vizcaya, 9', '981 18 67 20', 1, 'ACTIVO', @cat_floristeria, (SELECT id FROM usuarios WHERE email = 'e@e.com'), 'https://images.unsplash.com/photo-1490750967868-88aa4486c946?auto=format&fit=crop&w=1200&q=80', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM comercios WHERE nombre = 'Floristería Abelleira');

INSERT INTO comercios (nombre, direccion, telefono, activo, estado, categoria_id, usuario_id, imagen_url, created_at, updated_at)
SELECT 'Mercería MIC', 'Rúa Barcelona, 31', '981 77 40 12', 1, 'ACTIVO', @cat_merceria, (SELECT id FROM usuarios WHERE email = 'f@f.com'), 'https://images.unsplash.com/photo-1452860606245-08befc0ff44b?auto=format&fit=crop&w=1200&q=80', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM comercios WHERE nombre = 'Mercería MIC');

INSERT INTO comercios (nombre, direccion, telefono, activo, estado, categoria_id, usuario_id, imagen_url, created_at, updated_at)
SELECT 'A Casa da Collona', 'Rúa Ángel Senra, 24', '981 50 12 67', 1, 'ACTIVO', @cat_hosteleria, (SELECT id FROM usuarios WHERE email = 'g@g.com'), 'https://images.unsplash.com/photo-1555396273-367ea4eb4db5?auto=format&fit=crop&w=1200&q=80', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM comercios WHERE nombre = 'A Casa da Collona');

INSERT INTO comercios (nombre, direccion, telefono, activo, estado, categoria_id, usuario_id, imagen_url, created_at, updated_at)
SELECT 'Pulpeira A Nova Lanchiña', 'Rúa dos Mallos, 58', '981 80 14 39', 1, 'ACTIVO', @cat_hosteleria, (SELECT id FROM usuarios WHERE email = 'h@h.com'), 'https://images.unsplash.com/photo-1514933651103-005eec06c04b?auto=format&fit=crop&w=1200&q=80', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM comercios WHERE nombre = 'Pulpeira A Nova Lanchiña');

INSERT INTO comercios (nombre, direccion, telefono, activo, estado, categoria_id, usuario_id, imagen_url, created_at, updated_at)
SELECT 'Jumbo H-1', 'Avenida Arteixo, 83', '981 12 90 88', 1, 'ACTIVO', @cat_comercio, (SELECT id FROM usuarios WHERE email = 'i@i.com'), 'https://images.unsplash.com/photo-1604719312566-8912e9227c6a?auto=format&fit=crop&w=1200&q=80', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM comercios WHERE nombre = 'Jumbo H-1');

INSERT INTO comercios (nombre, direccion, telefono, activo, estado, categoria_id, usuario_id, imagen_url, created_at, updated_at)
SELECT 'Fruta & Co', 'Rúa San Luis, 17', '981 64 20 18', 1, 'ACTIVO', @cat_alimentacion, (SELECT id FROM usuarios WHERE email = 'j@j.com'), 'https://images.unsplash.com/photo-1542838132-92c53300491e?auto=format&fit=crop&w=1200&q=80', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM comercios WHERE nombre = 'Fruta & Co');

INSERT INTO comercios (nombre, direccion, telefono, activo, estado, categoria_id, usuario_id, imagen_url, created_at, updated_at)
SELECT 'Liege Alves Beauty Concept', 'Rúa Ramón Cabanillas, 6', '981 91 73 54', 1, 'ACTIVO', @cat_belleza, (SELECT id FROM usuarios WHERE email = 'k@k.com'), 'https://images.unsplash.com/photo-1521590832167-7bcbfaa6381f?auto=format&fit=crop&w=1200&q=80', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM comercios WHERE nombre = 'Liege Alves Beauty Concept');

INSERT INTO horarios (dia_semana, hora_apertura, hora_cierre, activo, comercio_id)
SELECT d.dia, '09:00:00', '14:00:00', 1, c.id
FROM comercios c
JOIN (
    SELECT 1 AS dia UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5
) d
WHERE c.nombre IN (
    'Peluquería Emma Vázquez',
    'Panadería Élida Mesejo',
    'Farmacia Adúriz',
    'Floristería Abelleira',
    'Mercería MIC',
    'Jumbo H-1',
    'Fruta & Co',
    'Liege Alves Beauty Concept'
)
AND NOT EXISTS (
    SELECT 1 FROM horarios h
    WHERE h.comercio_id = c.id AND h.dia_semana = d.dia AND h.hora_apertura = '09:00:00'
);

INSERT INTO horarios (dia_semana, hora_apertura, hora_cierre, activo, comercio_id)
SELECT d.dia, '18:00:00', '21:00:00', 1, c.id
FROM comercios c
JOIN (
    SELECT 1 AS dia UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5
) d
WHERE c.nombre IN (
    'Peluquería Emma Vázquez',
    'Floristería Abelleira',
    'Mercería MIC',
    'Jumbo H-1',
    'Fruta & Co',
    'Liege Alves Beauty Concept'
)
AND NOT EXISTS (
    SELECT 1 FROM horarios h
    WHERE h.comercio_id = c.id AND h.dia_semana = d.dia AND h.hora_apertura = '18:00:00'
);

INSERT INTO horarios (dia_semana, hora_apertura, hora_cierre, activo, comercio_id)
SELECT d.dia, '08:30:00', '23:30:00', 1, c.id
FROM comercios c
JOIN (
    SELECT 1 AS dia UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6
) d
WHERE c.nombre IN ('Bar Chaflán', 'A Casa da Collona', 'Pulpeira A Nova Lanchiña')
AND NOT EXISTS (
    SELECT 1 FROM horarios h
    WHERE h.comercio_id = c.id AND h.dia_semana = d.dia AND h.hora_apertura = '08:30:00'
);

INSERT INTO ofertas (titulo, descripcion, activo, fecha_fin, comercio_id, created_at, updated_at)
SELECT 'Tapa de cortesía con tu consumición', 'De lunes a jueves por la tarde, pide tu bebida y prueba una tapa de la casa.', 1, '2026-06-20', id, NOW(), NOW()
FROM comercios
WHERE nombre = 'Bar Chaflán'
AND NOT EXISTS (SELECT 1 FROM ofertas WHERE titulo = 'Tapa de cortesía con tu consumición');

INSERT INTO ofertas (titulo, descripcion, activo, fecha_fin, comercio_id, created_at, updated_at)
SELECT 'Peinado express -20%', 'Descuento especial para citas de peinado y acabado entre semana.', 1, '2026-06-18', id, NOW(), NOW()
FROM comercios
WHERE nombre = 'Peluquería Emma Vázquez'
AND NOT EXISTS (SELECT 1 FROM ofertas WHERE titulo = 'Peinado express -20%');

INSERT INTO ofertas (titulo, descripcion, activo, fecha_fin, comercio_id, created_at, updated_at)
SELECT '2x1 en napolitanas', 'Promoción de mañana en napolitanas artesanas hasta fin de existencias.', 1, '2026-06-11', id, NOW(), NOW()
FROM comercios
WHERE nombre = 'Panadería Élida Mesejo'
AND NOT EXISTS (SELECT 1 FROM ofertas WHERE titulo = '2x1 en napolitanas');

INSERT INTO ofertas (titulo, descripcion, activo, fecha_fin, comercio_id, created_at, updated_at)
SELECT 'Revisión gratuita del botiquín', 'Lleva tu botiquín doméstico y te ayudamos a revisar caducidades y básicos.', 1, '2026-06-30', id, NOW(), NOW()
FROM comercios
WHERE nombre = 'Farmacia Adúriz'
AND NOT EXISTS (SELECT 1 FROM ofertas WHERE titulo = 'Revisión gratuita del botiquín');

INSERT INTO ofertas (titulo, descripcion, activo, fecha_fin, comercio_id, created_at, updated_at)
SELECT 'Ramo de temporada -15%', 'Ramos frescos de temporada con descuento para encargos realizados online o por teléfono.', 1, '2026-06-16', id, NOW(), NOW()
FROM comercios
WHERE nombre = 'Floristería Abelleira'
AND NOT EXISTS (SELECT 1 FROM ofertas WHERE titulo = 'Ramo de temporada -15%');

INSERT INTO ofertas (titulo, descripcion, activo, fecha_fin, comercio_id, created_at, updated_at)
SELECT 'Kit costura básico', 'Pack de hilos, agujas y cinta métrica con precio especial para el barrio.', 1, '2026-06-25', id, NOW(), NOW()
FROM comercios
WHERE nombre = 'Mercería MIC'
AND NOT EXISTS (SELECT 1 FROM ofertas WHERE titulo = 'Kit costura básico');

INSERT INTO ofertas (titulo, descripcion, activo, fecha_fin, comercio_id, created_at, updated_at)
SELECT 'Menú del día con postre incluido', 'Menú casero con postre incluido durante esta semana.', 1, '2026-06-14', id, NOW(), NOW()
FROM comercios
WHERE nombre = 'A Casa da Collona'
AND NOT EXISTS (SELECT 1 FROM ofertas WHERE titulo = 'Menú del día con postre incluido');

INSERT INTO ofertas (titulo, descripcion, activo, fecha_fin, comercio_id, created_at, updated_at)
SELECT 'Ración de pulpo para compartir', 'Precio especial en ración de pulpo los miércoles y jueves.', 1, '2026-06-22', id, NOW(), NOW()
FROM comercios
WHERE nombre = 'Pulpeira A Nova Lanchiña'
AND NOT EXISTS (SELECT 1 FROM ofertas WHERE titulo = 'Ración de pulpo para compartir');

INSERT INTO ofertas (titulo, descripcion, activo, fecha_fin, comercio_id, created_at, updated_at)
SELECT 'Descuento barrio en compra mínima', '5% de descuento en compras superiores a 25 euros.', 1, '2026-06-28', id, NOW(), NOW()
FROM comercios
WHERE nombre = 'Jumbo H-1'
AND NOT EXISTS (SELECT 1 FROM ofertas WHERE titulo = 'Descuento barrio en compra mínima');

INSERT INTO ofertas (titulo, descripcion, activo, fecha_fin, comercio_id, created_at, updated_at)
SELECT 'Caja de fruta de temporada', 'Caja variada de fruta de temporada con precio cerrado.', 1, '2026-06-13', id, NOW(), NOW()
FROM comercios
WHERE nombre = 'Fruta & Co'
AND NOT EXISTS (SELECT 1 FROM ofertas WHERE titulo = 'Caja de fruta de temporada');

INSERT INTO ofertas (titulo, descripcion, activo, fecha_fin, comercio_id, created_at, updated_at)
SELECT 'Tratamiento facial inicial -20%', 'Primera sesión de diagnóstico y tratamiento facial con descuento.', 1, '2026-06-19', id, NOW(), NOW()
FROM comercios
WHERE nombre = 'Liege Alves Beauty Concept'
AND NOT EXISTS (SELECT 1 FROM ofertas WHERE titulo = 'Tratamiento facial inicial -20%');
