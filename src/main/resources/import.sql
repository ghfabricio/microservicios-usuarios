INSERT INTO usuarios (username, password, enabled, nombre, apellido, dependencia, email) VALUES ('jose','$2a$10$ykhXmCAam5FUEF9GN.4Z8OwwWJidvMii6VFYe77cmS2X6oF6p4W86', true, 'Jose Antonio', 'Sanchez Rojas', 'Subdirección de Investigacion y Desarrollo', 'jsanchez@gmail.com');
INSERT INTO usuarios (username, password, enabled, nombre, apellido, dependencia, email) VALUES ('admin','$2a$10$fezbYJX1bZtMNWPCsMGwKOt1Btr7GHghCP/yIiMqRKcE8xQ0bmxJ2',true, 'John', 'Doee', 'Dependencia Jhon','Jhon.doe@bolsadeideas.com');
INSERT INTO usuarios (username, password, enabled, nombre, apellido, dependencia, email) VALUES ('fabricio','$2a$10$ykhXmCAam5FUEF9GN.4Z8OwwWJidvMii6VFYe77cmS2X6oF6p4W86',true, 'Fabricio', 'Juarez', 'Dependencia Fabricio', 'fabricio@gmail.com');
INSERT INTO usuarios (username, password, enabled, nombre, apellido, dependencia, email) VALUES ('martin','$2a$10$qGyDfZLBB.SgLv7GCP3uZe3oM38fVtr58T1iZ1LNOvO61loNUAAaK',true, 'Martin Eduardo', 'Altamira Fonolla', 'Subdirección de Investigacion y Desarrollo', 'maltamira@gmail.com');
INSERT INTO usuarios (username, password, enabled, nombre, apellido, dependencia, email) VALUES ('guillermo','$2a$10$ykhXmCAam5FUEF9GN.4Z8OwwWJidvMii6VFYe77cmS2X6oF6p4W86',true, 'Guillermo Mario', 'Arrollo Mazzotti', 'Subdirección de Investigacion y Desarrollo','garrollo@gmail.com');
INSERT INTO usuarios (username, password, enabled, nombre, apellido, dependencia, email) VALUES ('carolina','$2a$10$qGyDfZLBB.SgLv7GCP3uZe3oM38fVtr58T1iZ1LNOvO61loNUAAaK',true, 'Maria Carolina', 'Muñoz Fonseca', 'Subdirección de Investigacion y Desarrollo', 'cmunioz@gmail.com');
INSERT INTO usuarios (username, password, enabled, nombre, apellido, dependencia, email) VALUES ('cristian','$2a$10$qGyDfZLBB.SgLv7GCP3uZe3oM38fVtr58T1iZ1LNOvO61loNUAAaK',true, 'Cristian Mauricio', 'Arias Martignoni', 'Subdirección de Investigacion y Desarrollo', 'carias@gmail.com');
INSERT INTO usuarios (username, password, enabled, nombre, apellido, dependencia, email) VALUES ('damian','$2a$10$qGyDfZLBB.SgLv7GCP3uZe3oM38fVtr58T1iZ1LNOvO61loNUAAaK',true, 'Cristian Damian', 'Dutrus Castro', 'Subdirección de Investigacion y Desarrollo', 'cdutrus@gmail.com');


INSERT INTO roles (nombre, descripcion) VALUES ('ROLE_USER', 'Solo puede ver el listado de usuarios');
INSERT INTO roles (nombre, descripcion) VALUES ('ROLE_ADMIN','Tiene todos los permisos');

INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2, 2);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2, 1);
