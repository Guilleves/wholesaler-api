ALTER TABLE `Order` ADD COLUMN deletedAt TIMESTAMP DEFAULT NULL;
ALTER TABLE OrderLine ADD COLUMN deletedAt TIMESTAMP DEFAULT NULL;
ALTER TABLE Product ADD COLUMN deletedAt TIMESTAMP DEFAULT NULL;
ALTER TABLE Proposal ADD COLUMN deletedAt TIMESTAMP DEFAULT NULL;
ALTER TABLE ProposalLine ADD COLUMN deletedAt TIMESTAMP DEFAULT NULL;
ALTER TABLE User ADD COLUMN deletedAt TIMESTAMP DEFAULT NULL;