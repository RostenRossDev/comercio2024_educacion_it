/*Creamos usuarios*/
INSERT INTO users (username, password, enabled, email, creado) VALUES ('AnaGarcía','$2a$10$p7LHk/KItqUEAyK2VksvzeOMeZWi7TXbA7uta3bMOz89uzlNeSe3q',1, 'ana.garcia@example.com', '2024-10-14');
INSERT INTO users (username, password, enabled, email, creado) VALUES ('admin','$2a$10$aT7985FBPnPc2WGcMYwiSeoHHjJKvid214iYR.NQRGaLcKWfa9YK.',1, 'admin@admin.com', '2024-10-14');


 /*asignamos roles*/

INSERT INTO authorities (user_id, authority) VALUES (1,'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (2,'ROLE_ADMIN');
INSERT INTO authorities (user_id, authority) VALUES (2,'ROLE_USER');

/*productos*/
INSERT INTO products (name, description, price, image_url) VALUES ('Laptop', 'Laptop de alta gama con procesador i7', 1200.00, 'https://via.placeholder.com/300.png?text=Laptop');
INSERT INTO products (name, description, price, image_url) VALUES ('Smartphone', 'Smartphone con cámara de 48 MP', 700.00, 'https://via.placeholder.com/300.png?text=Smartphone');
INSERT INTO products (name, description, price, image_url) VALUES ('Tablet', 'Tablet con pantalla de 10 pulgadas', 350.00, 'https://via.placeholder.com/300.png?text=Tablet');
INSERT INTO products (name, description, price, image_url) VALUES ('Auriculares', 'Auriculares inalámbricos con cancelación de ruido', 150.00, 'https://via.placeholder.com/300.png?text=Auriculares');
INSERT INTO products (name, description, price, image_url) VALUES ('Smartwatch', 'Reloj inteligente con monitoreo de salud', 200.00, 'https://via.placeholder.com/300.png?text=Smartwatch');
INSERT INTO products (name, description, price, image_url) VALUES ('Impresora', 'Impresora a color de inyección de tinta', 250.00, 'https://via.placeholder.com/300.png?text=Impresora');
INSERT INTO products (name, description, price, image_url) VALUES ('Monitor', 'Monitor 24 pulgadas Full HD', 300.00, 'https://via.placeholder.com/300.png?text=Monitor');
INSERT INTO products (name, description, price, image_url) VALUES ('Teclado mecánico', 'Teclado mecánico RGB para gamers', 100.00, 'https://via.placeholder.com/300.png?text=Teclado');
INSERT INTO products (name, description, price, image_url) VALUES ('Ratón', 'Ratón óptico ergonómico', 50.00, 'https://via.placeholder.com/300.png?text=Raton');
INSERT INTO products (name, description, price, image_url) VALUES ('Cámara', 'Cámara DSLR de 24 MP', 800.00, 'https://via.placeholder.com/300.png?text=Camara');
INSERT INTO products (name, description, price, image_url) VALUES ('Altavoces', 'Altavoces Bluetooth con sonido envolvente', 120.00, 'https://via.placeholder.com/300.png?text=Altavoces');
INSERT INTO products (name, description, price, image_url) VALUES ('Disco Duro', 'Disco duro externo de 1TB', 90.00, 'https://via.placeholder.com/300.png?text=Disco+Duro');
INSERT INTO products (name, description, price, image_url) VALUES ('SSD', 'SSD de 500GB para mejorar el rendimiento', 120.00, 'https://via.placeholder.com/300.png?text=SSD');
INSERT INTO products (name, description, price, image_url) VALUES ('Router', 'Router Wi-Fi de doble banda', 80.00, 'https://via.placeholder.com/300.png?text=Router');
INSERT INTO products (name, description, price, image_url) VALUES ('Cable HDMI', 'Cable HDMI de 2 metros', 15.00, 'https://via.placeholder.com/300.png?text=Cable+HDMI');
INSERT INTO products (name, description, price, image_url) VALUES ('Webcam', 'Webcam 1080p para videollamadas', 60.00, 'https://via.placeholder.com/300.png?text=Webcam');
INSERT INTO products (name, description, price, image_url) VALUES ('Microfono', 'Micrófono USB de condensador', 75.00, 'https://via.placeholder.com/300.png?text=Microfono');
INSERT INTO products (name, description, price, image_url) VALUES ('Soporte para portátil', 'Soporte ajustable para laptop', 30.00, 'https://via.placeholder.com/300.png?text=Soporte');
INSERT INTO products (name, description, price, image_url) VALUES ('Mochila para portátil', 'Mochila resistente para laptops de 15 pulgadas', 40.00, 'https://via.placeholder.com/300.png?text=Mochila');
INSERT INTO products (name, description, price, image_url) VALUES ('Batería externa', 'Batería externa de 10000mAh', 25.00, 'https://via.placeholder.com/300.png?text=Bateria+externa');