CREATE SCHEMA IF NOT EXISTS wholesaler;

DROP TABLE IF EXISTS Organization;
CREATE TABLE Organization (
    id INT AUTO_INCREMENT,
    name VARCHAR(),
    legalName VARCHAR(),
    cuit VARCHAR(),
    role VARCHAR(),
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS User;
CREATE TABLE User (
    id INT AUTO_INCREMENT,
    firstName VARCHAR(),
    lastName VARCHAR(),
    username VARCHAR(),
    email VARCHAR(),
    password VARCHAR(),
    organizationId INT,
    PRIMARY KEY (id),
    CONSTRAINT fk_user_organizaion FOREIGN KEY (organizationId) REFERENCES Organization (id)
);

DROP TABLE IF EXISTS Brand;
CREATE TABLE Brand (
    id INT AUTO_INCREMENT,
    name VARCHAR(),
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS Category;
CREATE TABLE Category (
    id INT AUTO_INCREMENT,
    name VARCHAR(),
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS Product;
CREATE TABLE Product (
    id INT AUTO_INCREMENT,
    name VARCHAR(),
    gtin VARCHAR(),
    brandId INT,
    categoryId INT,
    PRIMARY KEY (id),
    CONSTRAINT fk_product_brand FOREIGN KEY (brandId) REFERENCES Brand (id),
    CONSTRAINT fk_product_category FOREIGN KEY (categoryId) REFERENCES Category (id)
);

DROP TABLE IF EXISTS Catalog;
CREATE TABLE Catalog (
    id INT AUTO_INCREMENT,
    organizationId INT,
    productId INT,
    CONSTRAINT fk_catalog_organization FOREIGN KEY (organizationId) REFERENCES Organization (id),
    CONSTRAINT fk_catalog_product FOREIGN KEY (productId) REFERENCES Product (id)
);

DROP TABLE IF EXISTS Proposal;
CREATE TABLE Proposal (
    id INT AUTO_INCREMENT,
    beginDate TIMESTAMP,
    endDate TIMESTAMP,
    description VARCHAR(),
    supplierId INT,
    PRIMARY KEY (id),
    CONSTRAINT fk_proposal_organization FOREIGN KEY (supplierId) REFERENCES Organization (id)
);

DROP TABLE IF EXISTS ProposalLine;
CREATE TABLE ProposalLine (
    id INT AUTO_INCREMENT,
    price FLOAT,
    proposalId INT,
    productId INT,
    PRIMARY KEY (id),
    CONSTRAINT fk_proposalLine_product FOREIGN KEY (productId) REFERENCES Product (id)
    CONSTRAINT fk_proposalLine_proposal FOREIGN KEY (proposalId) REFERENCES Proposal (id)
);

DROP TABLE IF EXISTS Order;
CREATE TABLE Order (
    id INT AUTO_INCREMENT,
    dateOrdered TIMESTAMP,
    retailId INT,
    PRIMARY KEY (id),
    CONSTRAINT fk_order_organization FOREIGN KEY (retailId) REFERENCES Organization (id)
);

DROP TABLE IF EXISTS OrderLine;
CREATE TABLE OrderLine (
    id INT AUTO_INCREMENT,
    quantity INT,
    orderId INT,
    proposalLineId INT,
    PRIMARY KEY (id),
    CONSTRAINT fk_orderLine_order FOREIGN KEY (orderId) REFERENCES Order (id),
    CONSTRAINT fk_orderLine_proposalLine FOREIGN KEY (proposalLineId) REFERENCES ProposalLine (id)    
);
