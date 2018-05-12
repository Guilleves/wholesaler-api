INSERT INTO `wholesaler`.`Organization` (`id`, `name`, `legalName`, `cuit`, `role`) VALUES ('1', 'Sony', 'Sony Argentina SA', '123', 'supplier');
INSERT INTO `wholesaler`.`Organization` (`id`, `name`, `legalName`, `cuit`, `role`) VALUES ('2', 'LG', 'LG Electronics', '456', 'supplier');
INSERT INTO `wholesaler`.`Organization` (`id`, `name`, `legalName`, `cuit`, `role`) VALUES ('3', 'Calatayud', 'Calatayud SRL', '789', 'retailer');

INSERT INTO `wholesaler`.`User` (`id`, `firstName`, `lastName`, `Username`, `email`, `password`, `OrganizationId`) VALUES ('1', 'Guille', 'Véscovo', 'guillevescovo', 'guille@mail.com', 'holis', '1');
INSERT INTO `wholesaler`.`User` (`id`, `firstName`, `lastName`, `Username`, `email`, `password`, `OrganizationId`) VALUES ('2', 'Juan', 'Grasso', 'juangrasso', 'juan@mail.com', 'holis', '3');
INSERT INTO `wholesaler`.`User` (`id`, `firstName`, `lastName`, `Username`, `email`, `password`, `OrganizationId`) VALUES ('3', 'Diego', 'Debia', 'diegodebia', 'diego@mail.com', 'holis', '2');
INSERT INTO `wholesaler`.`User` (`id`, `firstName`, `lastName`, `Username`, `email`, `password`, `OrganizationId`) VALUES ('4', 'Juan', 'Véscovo', 'juanvescovo', 'juan@mail.com', 'holis', '1');

INSERT INTO `wholesaler`.`Brand` (`id`, `name` ) VALUES ('1', 'TP-Link');
INSERT INTO `wholesaler`.`Brand` (`id`, `name` ) VALUES ('2', 'LG');
INSERT INTO `wholesaler`.`Brand` (`id`, `name` ) VALUES ('3', 'BGH');
INSERT INTO `wholesaler`.`Category` (`id`, `name` ) VALUES ('1', 'PC Components');
INSERT INTO `wholesaler`.`Category` (`id`, `name` ) VALUES ('2', 'Small home appliances');
INSERT INTO `wholesaler`.`Category` (`id`, `name` ) VALUES ('3', 'Refrigerators');
