CREATE Procedure spInsertEquipment
@EquipmentName nvarchar(50),
@EquipmentDescription nvarchar(80),
@EquipmentID int output
AS
BEGIN
INSERT INTO tblEquipment VALUES (@EquipmentName, @EquipmentDescription,0)
SET @EquipmentID = @@IDENTITY
END

CREATE Procedure spMofifyEquipment
@EquipmentID int,
@EquipmentName nvarchar(50),
@EquipmentDescription nvarchar(80)
AS
BEGIN
UPDATE tblEquipment 
SET EquipmentName = @EquipmentName, EquipmentDescription = @EquipmentDescription
WHERE EquipmentID = @EquipmentID
END

CREATE Procedure spRemoveEquipment
@EquipmentID int
AS
BEGIN
DELETE FROM tblEquipment WHERE EquipmentID = @EquipmentID
END

CREATE Procedure spIncreaseEquipmentUsage
@EquipmentID int
AS
BEGIN
UPDATE tblEquipment 
SET EquipmentUsageStatistics +=1
WHERE EquipmentID = @EquipmentID
END

CREATE Procedure spDecreaseEquipmentUsage
@EquipmentID int
AS
BEGIN
UPDATE tblEquipment 
SET EquipmentUsageStatistics -=1
WHERE EquipmentID = @EquipmentID
END