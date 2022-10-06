alter table Canteen_CanteenType DROP FOREIGN KEY Canteen_CanteenType_askjh298_fk_canteen_id;
alter table Canteen_CanteenType ADD CONSTRAINT `Canteen_CanteenType_askjh298_fk_canteen_id` FOREIGN KEY (`canteen_id`) REFERENCES `Canteen` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

alter table Canteen_CanteenType DROP FOREIGN KEY Canteen_CanteenType_kjas2asd_fk_canteen_type_id;
alter table Canteen_CanteenType ADD CONSTRAINT `Canteen_CanteenType_kjas2asd_fk_canteen_type_id` FOREIGN KEY (`canteen_type_id`) REFERENCES `CanteenType` (`id`) ON DELETE CASCADE ON UPDATE CASCADE

ALTER TABLE `project1`.`User`
CHANGE COLUMN `name` `name` VARCHAR(32) CHARACTER SET 'utf8' NULL ;