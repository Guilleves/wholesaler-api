INSERT INTO `wholesaler`.`organization` (`id`, `name`, `legalName`, `cuit`, `role`) VALUES ('1', 'Sony', 'Sony Argentina SA', '123', 'supplier');
INSERT INTO `wholesaler`.`organization` (`id`, `name`, `legalName`, `cuit`, `role`) VALUES ('2', 'LG', 'LG Electronics', '456', 'supplier');
INSERT INTO `wholesaler`.`organization` (`id`, `name`, `legalName`, `cuit`, `role`) VALUES ('3', 'Calatayud', 'Calatayud SRL', '789', 'retailer');

INSERT INTO `wholesaler`.`user` (`id`, `firstName`, `lastName`, `username`, `email`, `password`, `organizationId`) VALUES ('1', 'Guille', 'Véscovo', 'guillevescovo', 'guille@mail.com', 'holis', '1');
INSERT INTO `wholesaler`.`user` (`id`, `firstName`, `lastName`, `username`, `email`, `password`, `organizationId`) VALUES ('2', 'Juan', 'Grasso', 'juangrasso', 'juan@mail.com', 'holis', '3');
INSERT INTO `wholesaler`.`user` (`id`, `firstName`, `lastName`, `username`, `email`, `password`, `organizationId`) VALUES ('3', 'Diego', 'Debia', 'diegodebia', 'diego@mail.com', 'holis', '2');
INSERT INTO `wholesaler`.`user` (`id`, `firstName`, `lastName`, `username`, `email`, `password`, `organizationId`) VALUES ('4', 'Juan', 'Véscovo', 'juanvescovo', 'juan@mail.com', 'holis', '1');

INSERT INTO `wholesaler`.`brand` (`id`, `name` ) VALUES ('1', 'TP-Link');
INSERT INTO `wholesaler`.`brand` (`id`, `name` ) VALUES ('2', 'LG');
INSERT INTO `wholesaler`.`brand` (`id`, `name` ) VALUES ('3', 'BGH');
INSERT INTO `wholesaler`.`category` (`id`, `name` ) VALUES ('1', 'PC Components');
INSERT INTO `wholesaler`.`category` (`id`, `name` ) VALUES ('2', 'Small home appliances');
INSERT INTO `wholesaler`.`category` (`id`, `name` ) VALUES ('3', 'Refrigerators');

UPDATE `wholesaler`.`product` SET `gtin` = '2342344' WHERE (`id` = '3');
INSERT INTO `wholesaler`.`product` (`id`, `name`, `gtin`, `brandId`, `categoryId`) VALUES ('4', 'comida para gatos', '3434', '2', '2');
INSERT INTO `wholesaler`.`product` (`id`, `name`, `gtin`, `brandId`, `categoryId`) VALUES ('5', 'teclado', '5689', '2', '3');
INSERT INTO `wholesaler`.`product` (`id`, `name`, `gtin`, `brandId`, `categoryId`) VALUES ('6', 'mesa', '33656456', '1', '2');
